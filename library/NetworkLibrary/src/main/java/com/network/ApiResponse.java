/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.network;


import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Common class used by API responses.
 *
 * @param <T>
 * @author batman
 */
public class ApiResponse<T> {

    @Nullable
    public String code;
    @Nullable
    public String msg;
    @Nullable
    @SerializedName("resultData")
    public T body;

    public boolean isSuccessful() {
        return code.equals("200") || code.equals("0");
    }
}
