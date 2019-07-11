/*
 * Tencent is pleased to support the open source community by making UI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ui.nestedScroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class UIContinuousNestedBottomRecyclerView extends RecyclerView implements IUIContinuousNestedBottomView {
    public UIContinuousNestedBottomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public UIContinuousNestedBottomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UIContinuousNestedBottomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void consumeScroll(int yUnconsumed) {
        scrollBy(0, yUnconsumed);
    }

    @Override
    public int getContentHeight() {
        Adapter adapter = getAdapter();
        if(adapter == null){
            return 0;
        }
        LayoutManager layoutManager = getLayoutManager();
        if(layoutManager == null){
            return 0;
        }
        final int scrollRange = computeVerticalScrollRange();
        final int offsetRange = scrollRange - computeVerticalScrollExtent();
        if(offsetRange > 0){
            return HEIGHT_IS_ENOUGH_TO_SCROLL;
        }
        return scrollRange;
    }
}
