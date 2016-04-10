package ua.techguardians.robospice.sample.retrofit2.services;

import com.octo.android.robospice.persistence.retrofit2.transformers.RetrofitGsonConvertAware;
import com.octo.android.robospice.request.retrofit2.RetrofitSpiceRequest2;
import com.octo.android.robospice.retrofit2.RetrofitGsonSpiceService2;

import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import ua.techguardians.robospice.sample.retrofit2.datamodels.SingleMessageJson;
import ua.techguardians.robospice.sample.retrofit2.webapi.SomeWebApi;

public class ApiService extends RetrofitGsonSpiceService2 {

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
    protected RetrofitGsonConvertAware createRetrofitToCacheConverter() {
        // Here we set a custom converter which will set a "timestamp" field to a received server's
        // response
        return new CustomCacheSaver();
    }

    private static class CustomCacheSaver extends RetrofitGsonConvertAware {
        @Override
        public String convertToString(Object object, Class<?> clzz) throws Exception {
            if (clzz.equals(SingleMessageJson.class)) {
                // Here you could modify any field you want inside a received object before saving
                // it to cache.
                // For example we set a "timestamp" field
                ((SingleMessageJson) object).setTimestamp(new Date().toString());
            }
            return super.convertToString(object, clzz);
        }
    }

}
