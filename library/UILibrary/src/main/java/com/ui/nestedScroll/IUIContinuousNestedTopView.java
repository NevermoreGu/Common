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

public interface IUIContinuousNestedTopView {
    /**
     * consume scroll
     *
     * @param dyUnconsumed the delta value to consume
     * @return the remain unconsumed value
     */
    int consumeScroll(int dyUnconsumed);

    int getCurrentScroll();

    int getScrollRange();

    void injectScrollNotifier(OnScrollNotifier notifier);

    interface OnScrollNotifier {
        void notify(int innerOffset, int innerRange);
    }
}