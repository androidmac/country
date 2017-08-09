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

package com.android.example.countries.ui.country;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.android.example.countries.repository.CountryRepository;
import com.android.example.countries.vo.Country;
import com.android.example.countries.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class CountryViewModel extends ViewModel {

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<Resource<List<Country>>> countries;
    @SuppressWarnings("unchecked")
    @Inject
    public CountryViewModel(CountryRepository repository) {

        countries = Transformations.switchMap(query, search -> {
            if (search == null || search.trim().length() == 0) {
                return repository.loadCountry();
            } else {
                return repository.filter(search);
            }
        });
    }

    LiveData<Resource<List<Country>>> getCountries() {
        return countries;
    }

    public void setQuery(String code){
        query.setValue(code);
    }

    public String getQuery(){
        return query.getValue();
    }

    public void initQuery(){
        if (query.getValue() == null)
            query.setValue("");
    }

    void retry() {
     /*   if (this.login.getValue() != null) {
            this.login.setValue(this.login.getValue());
        }*/
    }

    void refresh() {

    }
}
