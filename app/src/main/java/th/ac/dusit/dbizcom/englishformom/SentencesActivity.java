package th.ac.dusit.dbizcom.englishformom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_CATEGORY;

public class SentencesActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);

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
        int category = 0;

        switch (v.getId()) {
            case R.id.playground_image_view:
                category = Sentence.CATEGORY_PLAYGROUND;
                break;
            case R.id.morning_image_view:
                category = Sentence.CATEGORY_MORNING;
                break;
            case R.id.eat_image_view:
                category = Sentence.CATEGORY_EAT;
                break;
            case R.id.holiday_text_view:
                category = Sentence.CATEGORY_HOLIDAY;
                break;
            case R.id.school_image_view:
                category = Sentence.CATEGORY_SCHOOL;
                break;
        }
        Intent intent = new Intent(SentencesActivity.this, SentenceDetailsActivity.class);
        intent.putExtra(KEY_SENTENCE_CATEGORY, category);
        startActivity(intent);
    }
}
