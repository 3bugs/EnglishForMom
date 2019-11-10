package th.ac.dusit.dbizcom.englishformom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import th.ac.dusit.dbizcom.englishformom.etc.Utils;

import static th.ac.dusit.dbizcom.englishformom.VideoActivity.KEY_VIDEO_URL;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ImageView playgroundImageView = findViewById(R.id.playground_image_view);
        ImageView morningImageView = findViewById(R.id.morning_image_view);
        ImageView eatImageView = findViewById(R.id.eat_image_view);
        ImageView holidayImageView = findViewById(R.id.holiday_text_view);
        ImageView schoolImageView = findViewById(R.id.school_image_view);

        playgroundImageView.setOnClickListener(this);
        morningImageView.setOnClickListener(this);
        eatImageView.setOnClickListener(this);
        holidayImageView.setOnClickListener(this);
        schoolImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String videoUrl = null;

        switch (v.getId()) {
            case R.id.morning_image_view:
                videoUrl = getString(R.string.video_url_1);
                break;
            case R.id.school_image_view:
                //videoUrl = "";
                break;
            case R.id.playground_image_view:
                videoUrl = getString(R.string.video_url_2);
                break;
            case R.id.eat_image_view:
                //videoUrl = "";
                break;
            case R.id.holiday_text_view:
                videoUrl = "";
                //break;
        }

        if (videoUrl == null) {
            Utils.showLongToast(this, "ยังไม่เสร็จครับ");
            return;
        }

        Intent intent = new Intent(AnimationActivity.this, VideoActivity.class);
        intent.putExtra(KEY_VIDEO_URL, videoUrl);
        startActivity(intent);
    }
}
