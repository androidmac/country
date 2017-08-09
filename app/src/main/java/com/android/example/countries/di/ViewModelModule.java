package com.android.example.countries.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.android.example.countries.ui.country.CountryViewModel;
import com.android.example.countries.viewmodel.CountryViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel.class)
    abstract ViewModel bindCountryViewModel(CountryViewModel countryViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(CountryViewModelFactory factory);
}
