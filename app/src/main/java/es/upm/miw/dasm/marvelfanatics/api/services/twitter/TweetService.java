package es.upm.miw.dasm.marvelfanatics.api.services.twitter;

import es.upm.miw.dasm.marvelfanatics.api.models.twitter.Tweet;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface TweetService {

    @FormUrlEncoded
    @POST("/1.1/statuses/update.json")
    Call<Tweet> update(@Field("status") String status);

}
