/*
package com.android.example.countries.repository;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

*/
/**
 * Created by corly_h on 02/07/2017.
 *//*


@RunWith(JUnit4.class)
public class CountryRepositoryTest {
*/
/*    private CountryDao dao;
    private CountryService service;
    private CountryRepository repository;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();*//*


*/
/*    @Before
    public void init() {
        dao = mock(CountryDao.class);
        service = mock(CountryService.class);
        CountryDb db = mock(CountryDb.class);
        when(db.countryDao()).thenReturn(dao);
        repository = new CountryRepository(new InstantAppExecutors(), dao, service);
    }*//*


   */
/* @Test
    public void loadAllCountries() {
        repository.loadCountry();
        //verify(countryDao).getAll()
    }

    @Test
    public void goToNetwork() {
        MutableLiveData<Country> dbData = new MutableLiveData<>();
        dao.getAll();
        *//*
*/
/* MutableLiveData<Repo> dbData = new MutableLiveData<>();
        when(dao.load("foo", "bar")).thenReturn(dbData);

        Repo repo = TestUtil.createRepo("foo", "bar", "desc");
        LiveData<ApiResponse<Repo>> call = successCall(repo);
        when(service.getRepo("foo", "bar")).thenReturn(call);

        LiveData<Resource<Repo>> data = repository.loadRepo("foo", "bar");
        verify(dao).load("foo", "bar");
        verifyNoMoreInteractions(service);

        Observer observer = mock(Observer.class);
        data.observeForever(observer);
        verifyNoMoreInteractions(service);
        verify(observer).onChanged(Resource.loading(null));
        MutableLiveData<Repo> updatedDbData = new MutableLiveData<>();
        when(dao.load("foo", "bar")).thenReturn(updatedDbData);

        dbData.postValue(null);
        verify(service).getRepo("foo", "bar");
        verify(dao).insert(repo);

        updatedDbData.postValue(repo);
        verify(observer).onChanged(Resource.success(repo));*//*
*/
/*
    }

    @Test
    public void dontGoToNetwork() {
        MutableLiveData<Country> dbData = new MutableLiveData<>();
        repository.filter("");
        *//*
*/
/*User user = TestUtil.createUser("foo");
        dbData.setValue(user);
        when(userDao.findByLogin("foo")).thenReturn(dbData);
        Observer<Resource<User>> observer = mock(Observer.class);
        repo.loadUser("foo").observeForever(observer);
        verify(githubService, never()).getUser("foo");
        verify(observer).onChanged(Resource.success(user));*//*
*/
/*
    }*//*

}
*/
