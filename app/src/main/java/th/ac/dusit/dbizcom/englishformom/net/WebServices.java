package th.ac.dusit.dbizcom.englishformom.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {

    /*@GET("get_sentence")
    Call<GetSentenceResponse> getSentence(
    );*/

    /*@FormUrlEncoded
    @POST("add_rating")
    Call<AddRatingResponse> addRating(
            @Field("id") int itemId,
            @Field("type") String itemType,
            @Field("rate") int rate
    );*/

    @GET("get_sentence")
    Call<GetSentenceResponse> getSentence(
    );

    @GET("get_sentence")
    Call<GetSentenceResponse> getSentenceByCategory(
            @Query("category") String category
    );

    @GET("get_animation")
    Call<GetAnimationResponse> getAnimation(
    );
}