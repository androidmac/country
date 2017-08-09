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

package com.android.example.countries.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.android.example.countries.api.CountryService;
import com.android.example.countries.db.CountryDao;
import com.android.example.countries.db.CountryDb;
import com.android.example.countries.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    CountryService provideCountryService() {
        return new Retrofit.Builder()
                .baseUrl("https://restcountries.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(CountryService.class);
    }

    @Singleton
    @Provides
    CountryDb provideDb(Application app) {
        return Room.databaseBuilder(app, CountryDb.class, "countries.db").build();
    }

    @Singleton
    @Provides
    CountryDao provideCountryDao(CountryDb db) {
        return db.countryDao();
    }
}
