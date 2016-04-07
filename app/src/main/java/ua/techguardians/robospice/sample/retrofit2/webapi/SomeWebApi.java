package ua.techguardians.robospice.sample.retrofit2.webapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ua.techguardians.robospice.sample.retrofit2.datamodels.SingleMessageJson;

/**
 * Created on 3/26/16.
 */
public interface SomeWebApi {
    @GET("posts/{id}")
    Call<SingleMessageJson> getSingleMessage(@Path("id") long id);
}
