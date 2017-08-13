package com.android.example.countries.ui.country;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.android.example.countries.R;
import com.android.example.countries.binding.FragmentBindingAdapters;
import com.android.example.countries.testing.SingleFragmentActivity;
import com.android.example.countries.ui.common.NavigationController;
import com.android.example.countries.util.RecyclerViewMatcher;
import com.android.example.countries.util.TaskExecutorWithIdlingResourceRule;
import com.android.example.countries.util.TestUtil;
import com.android.example.countries.util.ViewModelUtil;
import com.android.example.countries.vo.Country;
import com.android.example.countries.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.android.example.countries.util.TestUtil.createCountryIsrael;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by corly_h on 26/06/2017.
 */
@RunWith(AndroidJUnit4.class)
public class CountryFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule =
            new ActivityTestRule<>(SingleFragmentActivity.class, true, true);
    @Rule
    public TaskExecutorWithIdlingResourceRule executorRule =
            new TaskExecutorWithIdlingResourceRule();

    private FragmentBindingAdapters fragmentBindingAdapters;
    private NavigationController navigationController;

    private CountryViewModel viewModel;

    private MutableLiveData<Resource<List<Country>>> results = new MutableLiveData<>();

    @Before
    public void init() {
        CountryFragment countryFragment = new CountryFragment();
        viewModel = mock(CountryViewModel.class);
        when(viewModel.getCountries()).thenReturn(results);

        fragmentBindingAdapters = mock(FragmentBindingAdapters.class);
        navigationController = mock(NavigationController.class);
        countryFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        countryFragment.dataBindingComponent = () -> fragmentBindingAdapters;
        countryFragment.navigationController = navigationController;
        activityRule.getActivity().setFragment(countryFragment);
    }

    @Test
    public void givenOneCountry_verifyCountryDisplayed() {
        Country country = createCountryIsrael();
        results.postValue(Resource.success(Arrays.asList(country)));
        onView(listMatcher().atPosition(0)).check(matches(hasDescendant(withText(country.name))));
    }

    @Test
    public void whenClickOnCountry_verifyQueryUpdatedWithBorders() {
        Country country = createCountryIsrael();
        results.postValue(Resource.success(Arrays.asList(country)));
        onView(withText(country.name)).perform(click());
        String bordersOfIsrael = TextUtils.join(",", country.borders);
        verify(viewModel).setQuery(bordersOfIsrael);
    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.country_list);
    }
}
