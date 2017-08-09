package com.android.example.countries.db;

/**
 * Created by corly_h on 27/06/2017.
 */

import android.support.test.runner.AndroidJUnit4;

import com.android.example.countries.vo.Country;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.android.example.countries.util.LiveDataTestUtil.getValue;
import static com.android.example.countries.util.TestUtil.createCountryIsrael;
import static com.android.example.countries.util.TestUtil.createIsraelWithItsBorderCountries;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class CountryDaoTest extends DbTest {

    @Test
    public void insertAndLoad() throws InterruptedException {
        final List<Country> israel = new ArrayList<>();
        israel.add(createCountryIsrael());
        db.countryDao().insert(israel);

        final List<Country> loaded = getValue(db.countryDao().getAll());

        assertThat(loaded, notNullValue());
        assertThat(loaded.size(), is(1));
        assertThat(loaded.get(0).name, is(israel.get(0).name));
        assertThat(loaded.get(0).alpha3Code, is(israel.get(0).alpha3Code));
        assertThat(loaded.get(0).borders, is(israel.get(0).borders));
        assertThat(loaded.get(0).nativeName, is(israel.get(0).nativeName));
        assertThat(loaded.get(0).flag, is(israel.get(0).flag));
    }

    @Test
    public void insertAndFilter() throws InterruptedException {
        final List<Country> list = createIsraelWithItsBorderCountries();
        db.countryDao().insert(list);

        final List<Country> loaded = getValue(db.countryDao().getAll());

        assertThat(loaded, notNullValue());
        assertThat(loaded.size(), is(5));

        List<String> israelBorders = loaded.get(0).borders;
        String[] bordersAsArray = israelBorders.toArray(new String[israelBorders.size()]);
        final List<Country> filtered = getValue(db.countryDao().filterByCode(bordersAsArray));

        assertThat(filtered, notNullValue());
        assertThat(filtered.size(), is(4));
    }
}
