package th.ac.dusit.dbizcom.englishformom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.db.SentenceRepository;
import th.ac.dusit.dbizcom.englishformom.model.Sentence;

import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_CATEGORY;
import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_LIST;

public class SentencesActivity extends AppCompatActivity implements View.OnClickListener {

    private SentenceRepository.Callback mCallback = new SentenceRepository.Callback() {
        @Override
        public void onGetSentence(String category, List<Sentence> sentenceList) {
            Intent intent = new Intent(SentencesActivity.this, SentenceDetailsActivity.class);
            intent.putExtra(KEY_SENTENCE_CATEGORY, category);
            intent.putExtra(KEY_SENTENCE_LIST, new Gson().toJson(sentenceList));
            startActivity(intent);
        }

        @Override
        public void onInsertSentenceSuccess() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);

        ImageView playgroundImageView = findViewById(R.id.playground_image_view);
        ImageView morningImageView = findViewById(R.id.morning_image_view);
        ImageView eatImageView = findViewById(R.id.eat_image_view);
        ImageView weekendImageView = findViewById(R.id.weekend_image_view);
        ImageView schoolImageView = findViewById(R.id.school_image_view);

        playgroundImageView.setOnClickListener(this);
        morningImageView.setOnClickListener(this);
        eatImageView.setOnClickListener(this);
        weekendImageView.setOnClickListener(this);
        schoolImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SentenceRepository repository = new SentenceRepository(this);
        String category = null;

        switch (v.getId()) {
            case R.id.morning_image_view:
                category = Sentence.CATEGORY_MORNING;
                break;
            case R.id.school_image_view:
                category = Sentence.CATEGORY_SCHOOL;
                break;
            case R.id.playground_image_view:
                category = Sentence.CATEGORY_PLAYGROUND;
                break;
            case R.id.eat_image_view:
                category = Sentence.CATEGORY_EAT;
                break;
            case R.id.weekend_image_view:
                category = Sentence.CATEGORY_WEEKEND;
                break;
        }

        repository.getSentenceByCategory(category, mCallback);
    }
}
