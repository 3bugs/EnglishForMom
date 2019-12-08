package th.ac.dusit.dbizcom.englishformom.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Animation;

public class GetAnimationResponse extends BaseResponse {

    @SerializedName("data_list")
    public List<Animation> animationList;

}