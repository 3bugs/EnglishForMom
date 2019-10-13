package th.ac.dusit.dbizcom.englishformom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class SentenceDetailsActivity extends AppCompatActivity {

    static final String KEY_SENTENCE_CATEGORY = "sentence_category";

    private int mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_details);

        Intent intent = getIntent();
        mCategory = intent.getIntExtra(KEY_SENTENCE_CATEGORY, 0);

        String title = null;
        switch (mCategory) {
            case Sentence.CATEGORY_MORNING:
                title = getString(R.string.sentence_category_morning);
                break;
            case Sentence.CATEGORY_SCHOOL:
                title = getString(R.string.sentence_category_school);
                break;
            case Sentence.CATEGORY_PLAYGROUND:
                title = getString(R.string.sentence_category_playground);
                break;
            case Sentence.CATEGORY_EAT:
                title = getString(R.string.sentence_category_eat);
                break;
            case Sentence.CATEGORY_HOLIDAY:
                title = getString(R.string.sentence_category_holiday);
                break;
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
        TextView titleTextView = findViewById(R.id.title_text_view);
        titleTextView.setText(title);

        ImageButton momSoundButton = findViewById(R.id.mom_sound_button);
        momSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(
                        SentenceDetailsActivity.this,
                        R.raw.morning_mom_01
                );
                mp.start();
            }
        });
        ImageButton childSoundButton = findViewById(R.id.child_sound_button);
        childSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(
                        SentenceDetailsActivity.this,
                        R.raw.morning_child_01
                );
                mp.start();
            }
        });
    }
}