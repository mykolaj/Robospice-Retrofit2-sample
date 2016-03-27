package ua.techguardians.robospicetest.webapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ua.techguardians.robospicetest.datamodels.SingleMessageJson;

/**
 * Created on 3/26/16.
 */
public interface SomeWebApi {
    @GET("posts/{id}")
    Call<SingleMessageJson> getSingleMessage(@Path("id") long id);
}
