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

public interface IUIContinuousNestedBottomView {
    int HEIGHT_IS_ENOUGH_TO_SCROLL = -1;
    /**
     * consume scroll
     * @param dyUnconsumed the delta value to consume
     */
    void consumeScroll(int dyUnconsumed);

    /**
     * sometimes the content of BottomView is not enough to scroll,
     * so BottomView should tell the this info to  {@link UIContinuousNestedScrollLayout}
     * @return  {@link #HEIGHT_IS_ENOUGH_TO_SCROLL} if can scroll, or content height.
     */
    int getContentHeight();

}