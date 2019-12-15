package th.ac.dusit.dbizcom.englishformom.model;

import com.google.gson.annotations.SerializedName;

public class Word {

    @SerializedName("id")
    public final int id;
    @SerializedName("word")
    public final String word;
    @SerializedName("meaninig")
    public final String meaning;
    @SerializedName("part_of_speech")
    public final String partOfSpeech;
    @SerializedName("category")
    public final String category;

    public Word(int id, String word, String meaning, String partOfSpeech, String category) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.partOfSpeech = partOfSpeech;
        this.category = category;
    }
}
