package ua.techguardians.robospicetest.services;

import com.octo.android.robospice.request.retrofit2.RetrofitSpiceRequest2;
import com.octo.android.robospice.retrofit2.RetrofitJacksonSpiceService2;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import ua.techguardians.robospicetest.datamodels.SingleMessageJson;
import ua.techguardians.robospicetest.webapi.SomeWebApi;

public class ApiService extends RetrofitJacksonSpiceService2 {

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

    public static class MessageRequest extends RetrofitSpiceRequest2<SingleMessageJson, SomeWebApi> {

        private final long mId;

        public MessageRequest(long id) {
            super(SingleMessageJson.class, SomeWebApi.class);
            mId = id;
        }

        @Override
        public SingleMessageJson loadDataFromNetwork() throws Exception {
            final Call<SingleMessageJson> call = getService().getSingleMessage(mId);
            final Response<SingleMessageJson> response = call.execute();
            return response.body();
        }

        public String getCacheKey() {
            return String.format(Locale.US, "id:%d", mId);
        }
    }


}
