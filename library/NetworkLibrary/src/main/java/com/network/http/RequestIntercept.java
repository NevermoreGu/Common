package com.network.http;


import com.network.utils.JsonUtils;
import com.network.utils.LogUtils;
import com.network.utils.ZipHelper;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author batman
 */
public class RequestIntercept implements Interceptor {

    public static final String TAG = "HttpIntercept";
    private HttpHandler mHttpHandler;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public RequestIntercept(HttpHandler httpHandler) {
        this.mHttpHandler = httpHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();

        //在请求服务器之前可以拿到request,做一些操作比如给request添加header,如果不做操作则返回参数中的request
        if (mHttpHandler != null) {
            request = mHttpHandler.onHttpRequestBefore(chain, request);
        }
        //打印url信息
        if (request.method().equalsIgnoreCase("get")) {
            LogUtils.d(TAG, "Sending Request on------ " + request.url() + " Headers ---> " + request.headers());
        } else if (request.method().equalsIgnoreCase("post")) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();

            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (isPlaintext(buffer)) {
                LogUtils.d(TAG, "Sending Request on------ " + request.url() + " Headers ---> " + request.headers());
                LogUtils.d(TAG, "Body " + buffer.readString(charset));
            } else {
                LogUtils.d(TAG, "Sending Request on------ " + request.url() + " Headers ---> " + request.headers());
            }

        }
        long t1 = System.nanoTime();
        Response originalResponse = chain.proceed(request);
        long t2 = System.nanoTime();
        //打印响应时间
        LogUtils.d(TAG, "Received response " + (t2 - t1) / 1e6d + originalResponse.headers());

        //读取服务器返回的结果
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        //获取content的压缩类型
        String encoding = originalResponse
                .headers()
                .get("Content-Encoding");

        Buffer clone = buffer.clone();
        String bodyString;

        //解析response content
        if (encoding != null && "gzip".equalsIgnoreCase(encoding)) {
            //content使用gzip压缩
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            clone.writeTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            bodyString = ZipHelper.decompressForGzip(bytes);
            outputStream.close();
        } else if (encoding != null && "zlib".equalsIgnoreCase(encoding)) {
            //content使用zlib压缩
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            clone.writeTo(outputStream);
            byte[] bytes = outputStream.toByteArray();
            bodyString = ZipHelper.decompressToStringForZlib(bytes);
            outputStream.close();
        } else {//content没有被压缩
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            bodyString = clone.readString(charset);
        }

        LogUtils.d(TAG, "Result " + JsonUtils.jsonFormat(bodyString));
        //这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
        if (mHttpHandler != null) {
            return mHttpHandler.onHttpResultResponse(bodyString, chain, originalResponse);
        }
        return originalResponse;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

}
