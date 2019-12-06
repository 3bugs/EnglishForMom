package th.ac.dusit.dbizcom.englishformom.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

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

    @POST("get_sentence")
    Call<GetSentenceResponse> getSentenceByCategory(
            @Field("category") String category
    );
}