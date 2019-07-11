package com.batman.baselibrary.camera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Toast;

import com.batman.baselibrary.R;
import com.batman.baselibrary.base.BaseActivity;
import com.batman.baselibrary.camera.camera.CameraManager;
import com.batman.baselibrary.camera.decode.CaptureActivityHandler;
import com.batman.baselibrary.camera.decode.DecodeManager;
import com.batman.baselibrary.camera.decode.InactivityTimer;
import com.batman.baselibrary.camera.view.QrCodeFinderView;
import com.google.zxing.Result;

import java.io.IOException;


/**
 * Created by xingli on 12/26/15.
 * <p/>
 * 二维码扫描类。
 */
public class QrCodeActivity extends BaseActivity implements Callback
{

	public static final String RESULT_CODE_KEY = "result_code_key";
	public static final int RESULT_CODE_SUCCESS = 1;
	public static final int RESULT_CODE_FAIL = 0;
	public static final String RESULT_CODE_RESULT_key = "result_key";

	public static final String INTENT_OUT_STRING_SCAN_RESULT = "scan_result";
	private static final String INTENT_IN_INT_SUPPORT_TYPE = "support_type";
	private static final int REQUEST_PERMISSIONS = 1;
	private CaptureActivityHandler mCaptureActivityHandler;
	private boolean mHasSurface;
	private InactivityTimer mInactivityTimer;
	private QrCodeFinderView mQrCodeFinderView;
	private SurfaceView mSurfaceView;
	private ViewStub mSurfaceViewStub;
	private DecodeManager mDecodeManager = new DecodeManager();


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
		{
			initCamera();
		} else
		{
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
		}
	}

	private void initCamera()
	{
		if (null == mSurfaceView)
		{
			mSurfaceViewStub.setLayoutResource(R.layout.layout_surface_view);
			mSurfaceView = (SurfaceView) mSurfaceViewStub.inflate();
		}
		SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
		if (mHasSurface)
		{
			initCamera(surfaceHolder);
		} else
		{
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		if (mCaptureActivityHandler != null)
		{
			try
			{
				mCaptureActivityHandler.quitSynchronously();
				mCaptureActivityHandler = null;
				mHasSurface = false;
				if (null != mSurfaceView)
				{
					mSurfaceView.getHolder().removeCallback(this);
				}
				CameraManager.get().closeDriver();
			} catch (Exception e)
			{
				// 关闭摄像头失败的情况下,最好退出该Activity,否则下次初始化的时候会显示摄像头已占用.
				finish();
			}
		}
	}

	private void showPermissionDeniedDialog()
	{
		findViewById(R.id.qr_code_view_background).setVisibility(View.VISIBLE);
		mQrCodeFinderView.setVisibility(View.GONE);
		mDecodeManager.showPermissionDeniedDialog(this);
	}

	@Override
	protected void onDestroy()
	{
		if (null != mInactivityTimer)
		{
			mInactivityTimer.shutdown();
		}
		super.onDestroy();
	}

	/**
	 * Handler scan result
	 *
	 * @param result
	 */
	public void handleDecode(Result result)
	{
		mInactivityTimer.onActivity();
		if (null == result)
		{
			mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener()
			{
				@Override
				public void refresh()
				{
					restartPreview();
				}
			});
		} else
		{
			String resultString = result.getText();
			Log.d(TAG, "handleDecode: "+resultString);
			handleResult(resultString);
		}
	}

	private void initCamera(SurfaceHolder surfaceHolder)
	{
		try
		{
			if (!CameraManager.get().openDriver(surfaceHolder))
			{
				showPermissionDeniedDialog();
				return;
			}
		} catch (IOException e)
		{
			// 基本不会出现相机不存在的情况
			Toast.makeText(this, getString(R.string.qr_code_camera_not_found), Toast.LENGTH_SHORT).show();
			finish();
			return;
		} catch (RuntimeException re)
		{
			re.printStackTrace();
			showPermissionDeniedDialog();
			return;
		}
		mQrCodeFinderView.setVisibility(View.VISIBLE);
		findViewById(R.id.qr_code_view_background).setVisibility(View.GONE);
		if (mCaptureActivityHandler == null)
		{
			mCaptureActivityHandler = new CaptureActivityHandler(this);
		}
	}

	private void restartPreview()
	{
		if (null != mCaptureActivityHandler)
		{
			try
			{
				mCaptureActivityHandler.restartPreviewAndDecode();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		if (!mHasSurface)
		{
			mHasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		mHasSurface = false;
	}

	public Handler getCaptureActivityHandler()
	{
		return mCaptureActivityHandler;
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
	                                       @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length != 0)
		{
			int cameraPermission = grantResults[0];
			if (cameraPermission == PackageManager.PERMISSION_GRANTED)
			{
				initCamera();
			} else
			{
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
						REQUEST_PERMISSIONS);
			}
		}
	}

	private void handleResult(String resultString)
	{
		if (TextUtils.isEmpty(resultString))
		{
			mDecodeManager.showCouldNotReadQrCodeFromScanner(this, new DecodeManager.OnRefreshCameraListener()
			{
				@Override
				public void refresh()
				{
					restartPreview();
				}
			});
		} else
		{
			getIntent().putExtra(INTENT_OUT_STRING_SCAN_RESULT,resultString);
			setResult(RESULT_OK,getIntent());
			finish();

		//	verificationCode(resultString);
//            if (mCaptureActivityHandler != null) {
//                try {
//                    mCaptureActivityHandler.quitSynchronously();
//                    mCaptureActivityHandler = null;
//                    mHasSurface = false;
//                    if (null != mSurfaceView) {
//                        mSurfaceView.getHolder().removeCallback(this);
//                    }
//                    CameraManager.get().closeDriver();
//                } catch (Exception e) {
//                    // 关闭摄像头失败的情况下,最好退出该Activity,否则下次初始化的时候会显示摄像头已占用.
//                    finish();
//                }
//            }
		}
	}


	@Override
	public int getLayoutId() {
		return R.layout.activity_qr_code;
	}

	@Override
	public void initViews() {
		mQrCodeFinderView = (QrCodeFinderView) findViewById(R.id.qr_code_view_finder);
		mSurfaceViewStub = (ViewStub) findViewById(R.id.qr_code_view_stub);
		mHasSurface = false;
	}

	@Override
	public void loadData(Bundle savedInstanceState) {
		CameraManager.init();
		mInactivityTimer = new InactivityTimer(this);
	}


	/**
	 * 扫码
	 *
	 * @param code
	 */
	private void verificationCode(final String code)
	{
	}
}