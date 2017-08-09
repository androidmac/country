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

package com.android.example.countries.ui.common;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.example.countries.R;
import com.android.example.countries.databinding.CountryItemBinding;
import com.android.example.countries.util.Objects;
import com.android.example.countries.vo.Country;

/**
 * A RecyclerView adapter for {@link Repo} class.
 */
public class CountryListAdapter extends DataBoundListAdapter<Country, CountryItemBinding> {
    private final DataBindingComponent dataBindingComponent;
    private final CountryClickCallback countryClickCallback;
    private final boolean showFullName;

    public CountryListAdapter(DataBindingComponent dataBindingComponent, boolean showFullName,
                              CountryClickCallback countryClickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.countryClickCallback = countryClickCallback;
        this.showFullName = showFullName;
    }

    @Override
    protected CountryItemBinding createBinding(ViewGroup parent) {
        CountryItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.country_item,
                        parent, false, dataBindingComponent);
        binding.setShowFullName(showFullName);
        binding.getRoot().setOnClickListener(v -> {
            Country country = binding.getCountry();
            if (country != null && countryClickCallback != null) {
                countryClickCallback.onClick(country);
            }
        });
        return binding;
    }

    @Override
    protected void bind(CountryItemBinding binding, Country item) {
        binding.setCountry(item);
    }

    @Override
    protected boolean areItemsTheSame(Country oldItem, Country newItem) {
        return Objects.equals(oldItem.alpha3Code, newItem.alpha3Code);
    }

    @Override
    protected boolean areContentsTheSame(Country oldItem, Country newItem) {
        return Objects.equals(oldItem.alpha3Code, newItem.alpha3Code);
    }

    public interface CountryClickCallback {
        void onClick(Country country);
    }
}
