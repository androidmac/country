package com.android.example.countries.ui.country;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.android.example.countries.repository.CountryRepository;
import com.android.example.countries.vo.Country;
import com.android.example.countries.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by corly_h on 25/06/2017.
 */

@RunWith(JUnit4.class)
public class CountryViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutor = new InstantTaskExecutorRule();
    private CountryViewModel viewModel;
    private CountryRepository repository;

    @Before
    public void init() {
        repository = mock(CountryRepository.class);
        viewModel = new CountryViewModel(repository);
    }

    @Test
    public void testNull() {
        assertThat(viewModel.getCountries(), notNullValue());
    }

    @Test
    public void testLoadAll() {
        Observer<Resource<List<Country>>> result = mock(Observer.class);
        viewModel.getCountries().observeForever(result);
        viewModel.setQuery("");
        verify(repository).loadCountry();
    }

    @Test
    public void testFilter() {
        Observer<Resource<List<Country>>> result = mock(Observer.class);
        viewModel.getCountries().observeForever(result);
        viewModel.setQuery("ILS");
        verify(repository).filter("ILS");
    }
}