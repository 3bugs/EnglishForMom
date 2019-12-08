package th.ac.dusit.dbizcom.englishformom.model;

import com.google.gson.annotations.SerializedName;

public class Animation {

    @SerializedName("id")
    public final int id;
    @SerializedName("category")
    public final String category;
    @SerializedName("video_url")
    public final String videoUrl;
    @SerializedName("video_duration")
    public final float videoDuration;

    public Animation(int id, String category, String videoUrl, float videoDuration) {
        this.id = id;
        this.category = category;
        this.videoUrl = videoUrl;
        this.videoDuration = videoDuration;
    }
}
