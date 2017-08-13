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

package com.android.example.countries.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;

import com.android.example.countries.db.CountryTypeConverters;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(primaryKeys = "alpha3Code")
@TypeConverters(CountryTypeConverters.class)
public class Country {
    @SerializedName("alpha3Code")
    public final String alpha3Code;
    @SerializedName("nativeName")
    public final String nativeName;
    @SerializedName("name")
    public final String name;
    @SerializedName("flag")
    public final String flag;
    @SerializedName("borders")
    public final List<String> borders;

    public Country(String alpha3Code, String nativeName, String name, String flag,
                   List<String> borders) {
        this.alpha3Code = alpha3Code;
        this.nativeName = nativeName;
        this.name = name;
        this.flag = flag;
        this.borders = borders;
    }
}
