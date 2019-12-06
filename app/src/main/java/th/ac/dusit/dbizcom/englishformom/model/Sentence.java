package th.ac.dusit.dbizcom.englishformom.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "sentence")
public class Sentence {

    public static final String CATEGORY_MORNING = "morning";
    public static final String CATEGORY_SCHOOL = "school";
    public static final String CATEGORY_PLAYGROUND = "playground";
    public static final String CATEGORY_EAT = "eat";
    public static final String CATEGORY_WEEKEND = "weekend";

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    public final int id;
    @SerializedName("mom_english")
    public final String momEnglish;
    @SerializedName("mom_thai")
    public final String momThai;
    @SerializedName("child_english")
    public final String childEnglish;
    @SerializedName("child_thai")
    public final String childThai;
    @SerializedName("mom_sound_file")
    public final String momSoundFile;
    @SerializedName("child_sound_file")
    public final String childSoundFile;
    @SerializedName("category")
    public final String category;

    public Sentence(int id, String momEnglish, String momSoundFile, String momThai,
                    String childEnglish, String childSoundFile, String childThai,
                    String category) {
        this.id = id;
        this.momEnglish = momEnglish;
        this.momThai = momThai;
        this.childEnglish = childEnglish;
        this.childThai = childThai;
        this.momSoundFile = momSoundFile;
        this.childSoundFile = childSoundFile;
        this.category = category;
    }
}
