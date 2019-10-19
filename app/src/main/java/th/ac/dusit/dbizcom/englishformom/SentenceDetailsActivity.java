package th.ac.dusit.dbizcom.englishformom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class SentenceDetailsActivity extends AppCompatActivity implements
        SentenceDetailsFragment.SentenceDetailsFragmentListener {

    static final String KEY_SENTENCE_CATEGORY = "sentence_category";
    static final String KEY_SENTENCE_LIST = "sentence_list";

    private int mCategory;
    private List<Sentence> mSentenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_details);

        Intent intent = getIntent();
        mCategory = intent.getIntExtra(KEY_SENTENCE_CATEGORY, 0);

        String sentenceListJson = intent.getStringExtra(KEY_SENTENCE_LIST);
        mSentenceList = new Gson().fromJson(sentenceListJson, new TypeToken<List<Sentence>>() {
        }.getType());

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

        setupPager();

        /*ImageButton momSoundButton = findViewById(R.id.mom_sound_button);
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
        });*/
    }

    private void setupPager() {
        SentencePagerAdapter adapter = new SentencePagerAdapter(
                SentenceDetailsActivity.this,
                getSupportFragmentManager(),
                mSentenceList
        );
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
    }

    private static class SentencePagerAdapter extends FragmentStatePagerAdapter {

        private Context mContext;
        private List<Sentence> mSentenceList;

        public SentencePagerAdapter(Context context,  @NonNull FragmentManager fm, List<Sentence> sentenceList) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mContext = context;
            mSentenceList = sentenceList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return SentenceDetailsFragment.newInstance(mSentenceList.get(position));
        }

        @Override
        public int getCount() {
            return mSentenceList.size();
        }
    }
}
