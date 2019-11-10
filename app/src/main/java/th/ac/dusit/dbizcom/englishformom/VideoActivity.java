package th.ac.dusit.dbizcom.englishformom;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    static final String KEY_VIDEO_URL = "video_url";

    private VideoView mVideoView;
    private String mVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        mVideoUrl = intent.getStringExtra(KEY_VIDEO_URL);

        setupVideoView();
    }

    private void setupVideoView() {
        mVideoView = findViewById(R.id.video_view);

        Uri video = Uri.parse(mVideoUrl);
        mVideoView.setVideoURI(video);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(false);
                mVideoView.start();
            }
        });
    }
}