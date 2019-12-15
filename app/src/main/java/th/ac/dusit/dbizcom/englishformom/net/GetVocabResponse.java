package th.ac.dusit.dbizcom.englishformom.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Word;

public class GetVocabResponse extends BaseResponse {

    @SerializedName("data_list")
    public List<Word> wordList;

}