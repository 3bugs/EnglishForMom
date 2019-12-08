package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import th.ac.dusit.dbizcom.englishformom.db.SentenceRepository;
import th.ac.dusit.dbizcom.englishformom.etc.Utils;
import th.ac.dusit.dbizcom.englishformom.model.Sentence;
import th.ac.dusit.dbizcom.englishformom.net.ApiClient;
import th.ac.dusit.dbizcom.englishformom.net.GetSentenceResponse;
import th.ac.dusit.dbizcom.englishformom.net.MyRetrofitCallback;
import th.ac.dusit.dbizcom.englishformom.net.WebServices;

import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_CATEGORY;
import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_LIST;

public class SentencesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SentencesActivity.class.getName();

    private ProgressBar mProgressView;

    private List<Sentence> mSentenceList = null;

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
        setupViews();

        if (mSentenceList == null) {
            doGetSentence();
        }
    }

    private void doGetSentence() {
        mProgressView = findViewById(R.id.progress_view);
        mProgressView.setVisibility(View.VISIBLE);

        Retrofit retrofit = ApiClient.getClient();
        WebServices services = retrofit.create(WebServices.class);

        Call<GetSentenceResponse> call = services.getSentence();
        call.enqueue(new MyRetrofitCallback<>(
                this,
                null,
                mProgressView,
                new MyRetrofitCallback.MyRetrofitCallbackListener<GetSentenceResponse>() {
                    @Override
                    public void onSuccess(GetSentenceResponse responseBody) {
                        mSentenceList = responseBody.sentenceList;
                        setupViews();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Utils.showOkDialog(
                                SentencesActivity.this,
                                "Error",
                                errorMessage,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }
                        );
                    }
                }
        ));
    }

    private void setupViews() {
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
        if (mSentenceList == null) {
            return;
        }

        String category = "";

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

        List<Sentence> categorySentenceList = new ArrayList<>();
        for (Sentence sentence : mSentenceList) {
            if (category.equals(sentence.category)) {
                categorySentenceList.add(sentence);
            }
        }

        Intent intent = new Intent(SentencesActivity.this, SentenceDetailsActivity.class);
        intent.putExtra(KEY_SENTENCE_CATEGORY, category);
        intent.putExtra(KEY_SENTENCE_LIST, new Gson().toJson(categorySentenceList));
        startActivity(intent);
    }

    //@Override
    public void _onClick(View v) {
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

        // อ่านข้อมูลทั้งหมดจาก local db มาสร้างเป็นคำสั่ง SQL INSERT แล้วเขียนลงไฟล์
        repository.getSentenceAll(new SentenceRepository.Callback() {
            @Override
            public void onGetSentence(String category, List<Sentence> sentenceList) {
                String sql = "";
                for (Sentence sentence : sentenceList) {
                    sql = sql.concat("(")
                            .concat(quoteText(sentence.momEnglish).concat(", "))
                            .concat(quoteText(sentence.momThai).concat(", "))
                            .concat(quoteText(sentence.childEnglish).concat(", "))
                            .concat(quoteText(sentence.childThai).concat(", "))
                            .concat(quoteText(sentence.momSoundFile).concat(", "))
                            .concat(quoteText(sentence.childSoundFile).concat(", "))
                            .concat(quoteText(sentence.category))
                            .concat("),\n");
                }
                //Log.i(TAG, sql);
                writeToFile(sql);
            }

            @Override
            public void onInsertSentenceSuccess() {
            }
        });
    }

    private String quoteText(String text) {
        return "'".concat(text.replaceAll("'", "''")).concat("'");
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("sql.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
