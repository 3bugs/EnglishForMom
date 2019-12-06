package th.ac.dusit.dbizcom.englishformom.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class GetSentenceResponse extends BaseResponse {

    @SerializedName("data_list")
    public List<Sentence> sentenceList;

}