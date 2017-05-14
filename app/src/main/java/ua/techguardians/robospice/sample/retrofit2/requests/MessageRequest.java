package ua.techguardians.robospice.sample.retrofit2.requests;

import com.octo.android.robospice.request.retrofit2.RetrofitSpiceRequest;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Response;
import ua.techguardians.robospice.sample.retrofit2.errors.CustomRequestFailureException;
import ua.techguardians.robospice.sample.retrofit2.responses.SingleMessageJson;
import ua.techguardians.robospice.sample.retrofit2.webapi.SomeWebApi;

/**
 * Created on 4/10/16.
 * Author: Anton Mykolaichuk
 */
public class MessageRequest extends RetrofitSpiceRequest<SingleMessageJson, SomeWebApi> {

    private final long mId;

    public MessageRequest(long id) {
        super(SingleMessageJson.class, SomeWebApi.class);
        mId = id;
    }

    @Override
    public SingleMessageJson loadDataFromNetwork() throws Exception {
        final Response<SingleMessageJson> response = getService().getSingleMessage(mId).clone().execute();

        if (!response.isSuccessful()) {

            // We need this method to throw Exception if we want Robospice to notify a request's
            // listeners about a failure (e.g. call a RequestListener<T>.onRequestFailure(SpiceException spiceException) method).
            //
            // In case no exception is thrown here Robospice always calls a RequestListener<T>.onRequestSuccess(T result) method.
            //
            // So here's one way how to achieve onRequestFailure behaviour

            String errorBody = null;
            try {
                 errorBody = response.errorBody().string();
            } catch (IOException e) {
                // Suppress cases when we can't get an errorBody.
            }
            final int httpCode = response.code();
            final String message = response.message();

            // Make an instance of a custom Exception and path any data into it.

            final CustomRequestFailureException exception = new CustomRequestFailureException(httpCode, message);
            exception.setErrorBody(errorBody);
            throw exception;
        }

        return response.body();
    }

    public String getCacheKey() {
        return String.format(Locale.US, "id:%d", mId);
    }
}
