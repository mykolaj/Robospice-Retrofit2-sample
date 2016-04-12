package ua.techguardians.robospice.sample.retrofit2.requests;

import com.octo.android.robospice.request.retrofit2.RetrofitSpiceRequest;

import java.util.Locale;

import ua.techguardians.robospice.sample.retrofit2.datamodels.SingleMessageJson;
import ua.techguardians.robospice.sample.retrofit2.webapi.SomeWebApi;

/**
 * Created by tony on 4/10/16.
 */
public class MessageRequest extends RetrofitSpiceRequest<SingleMessageJson, SomeWebApi> {

    private final long mId;

    public MessageRequest(long id) {
        super(SingleMessageJson.class, SomeWebApi.class);
        mId = id;
    }

    @Override
    public SingleMessageJson loadDataFromNetwork() throws Exception {
        return getService().getSingleMessage(mId).clone().execute().body();
    }

    public String getCacheKey() {
        return String.format(Locale.US, "id:%d", mId);
    }
}
