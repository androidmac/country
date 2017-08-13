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

package com.android.example.countries.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.example.countries.AppExecutors;
import com.android.example.countries.api.ApiResponse;
import com.android.example.countries.api.CountryService;
import com.android.example.countries.db.CountryDao;
import com.android.example.countries.vo.Country;
import com.android.example.countries.vo.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles User objects.
 */
@Singleton
public class CountryRepository {
    private final CountryDao countryDao;
    private final CountryService countryService;
    private final AppExecutors appExecutors;

    @Inject
    CountryRepository(AppExecutors appExecutors, CountryDao countryDao,
                      CountryService countryService) {
        this.countryDao = countryDao;
        this.countryService = countryService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Country>>> loadCountry() {
        return new NetworkBoundResource<List<Country>, List<Country>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Country> item) {
                countryDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Country> data) {
                return data == null || data.size() == 0;
            }

            @NonNull
            @Override
            protected LiveData<List<Country>> loadFromDb() {
                return countryDao.getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Country>>> createCall() {
                return countryService.getAllCountries();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Country>>> filter(String code) {
        return new NetworkBoundResource<List<Country>, List<Country>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull List<Country> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Country> data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<Country>> loadFromDb() {
                return countryDao.filterByCode(code.split(","));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Country>>> createCall() {
                return countryService.getAllCountries();
            }
        }.asLiveData();
    }
}
