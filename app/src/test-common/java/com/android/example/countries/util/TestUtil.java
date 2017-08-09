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

package com.android.example.countries.util;

import com.android.example.countries.vo.Country;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static Country createCountryIsrael(){
        List<String> borders = new ArrayList<>();
        borders.add("EGY");
        borders.add("JOR");
        borders.add("LBN");
        borders.add("SYR");

        Country israel = TestUtil.createCountry("ISR", "יִשְׂרָאֵל", "Israel", "https://restcountries.eu/data/imn.svg", borders);

        return israel;
    }

    public static Country createCountry(String alpha3Code, String nativeName, String name, String flag, List<String> borders){
        return new Country(alpha3Code, nativeName, name, flag, borders);
    }

    public static List<Country> createIsraelWithItsBorderCountries(){
        List<Country> list = new ArrayList<>();

        List<String> borders = new ArrayList<>();
        borders.add("EGY");
        borders.add("JOR");
        borders.add("LBN");
        borders.add("SYR");

        Country israel = TestUtil.createCountry("ISR", "יִשְׂרָאֵל", "Israel", "https://restcountries.eu/data/imn.svg", borders);

        list.add(israel);
        list.add(createCountry("EGY","","","",null));
        list.add(createCountry("JOR","","","",null));
        list.add(createCountry("LBN","","","",null));
        list.add(createCountry("SYR","","","",null));

        return list;
    }
}
