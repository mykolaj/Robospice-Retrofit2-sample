package ua.techguardians.robospice.sample.retrofit2.services;

import com.octo.android.robospice.persistence.retrofit2.converter.RetrofitGsonResponseConverter;
import com.octo.android.robospice.retrofit2.RetrofitGsonSpiceService;

import ua.techguardians.robospice.sample.retrofit2.webapi.SomeWebApi;

public class ApiService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(SomeWebApi.class);
    }

    @Override
    protected String getServerUrl() {
        // Thanks to guys from 'jsonplaceholder.typicode.com'
        return "http://jsonplaceholder.typicode.com";
    }

    @Override
    protected RetrofitGsonResponseConverter createRetrofitToCacheConverter() {
        // Typically you don't need to override this method. Override it only if you need
        // to change any fields inside JSON responses before saving them into cache, or when reading
        // saved data.
        // Here a custom converter is created which sets a "timestamp" field inside a received
        // server's response before saving it.
        return new CustomResponseConverter();
    }

}
