package com.android.example.countries.ui.country;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.countries.R;
import com.android.example.countries.binding.FragmentDataBindingComponent;
import com.android.example.countries.databinding.CountryFragmentBinding;
import com.android.example.countries.di.Injectable;
import com.android.example.countries.ui.common.CountryListAdapter;
import com.android.example.countries.ui.common.NavigationController;
import com.android.example.countries.util.AutoClearedValue;
import com.android.example.countries.util.SvgDecoder;
import com.android.example.countries.util.SvgDrawableTranscoder;
import com.android.example.countries.util.SvgSoftwareLayerSetter;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;

import javax.inject.Inject;

/**
 * Created by corly_h on 18/06/2017.
 */

public class CountryFragment extends LifecycleFragment implements Injectable {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;

    android.databinding.DataBindingComponent dataBindingComponent =
            new FragmentDataBindingComponent(this);
    private CountryViewModel countryViewModel;
    private AutoClearedValue<CountryFragmentBinding> binding;
    private AutoClearedValue<CountryListAdapter> adapter;

    public static CountryFragment create() {
        CountryFragment countryFragment = new CountryFragment();
        return countryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CountryFragmentBinding dataBinding =
                DataBindingUtil.inflate(inflater, R.layout.country_fragment,
                        container, false, dataBindingComponent);
        dataBinding.setRetryCallback(() -> countryViewModel.retry());
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    public GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> getSvgRequistBuilder() {
        return requestBuilder;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        createSVGRequestBuilder();

        countryViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(CountryViewModel.class);

        initRecyclerView();
        CountryListAdapter rvAdapter = new CountryListAdapter(dataBindingComponent,
                true,
                country -> countryViewModel.setQuery(TextUtils.join(",", country.borders))/*navigationController.navigateToBordering(country.alpha3Code)*/);
        binding.get().countryList.setAdapter(rvAdapter);
        adapter = new AutoClearedValue<>(this, rvAdapter);

        binding.get().setRetryCallback(() -> countryViewModel.refresh());
    }

    private void createSVGRequestBuilder() {
        requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    private void initRecyclerView() {
        countryViewModel.getCountries().observe(this, result -> {
            binding.get().setCountryResource(result);
            // Todo
            /*binding.get().setResultCount((result == null || result.data == null)
                    ? 0 : result.data.size());*/
            adapter.get().replace(result == null ? null : result.data);
            binding.get().executePendingBindings();
        });
        countryViewModel.initQuery();
    }
}
