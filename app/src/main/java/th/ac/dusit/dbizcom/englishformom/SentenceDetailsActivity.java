package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.etc.Utils;
import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class SentenceDetailsActivity extends AppCompatActivity implements
        SentenceDetailsFragment.SentenceDetailsFragmentListener {

    private static final String TAG = SentenceDetailsActivity.class.getName();

    static final String KEY_SENTENCE_CATEGORY = "sentence_category";
    static final String KEY_SENTENCE_LIST = "sentence_list";

    private MediaPlayer mMediaPlayer;
    private String mCategory;
    private List<Sentence> mSentenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_details);

        Intent intent = getIntent();
        mCategory = intent.getStringExtra(KEY_SENTENCE_CATEGORY);

        String sentenceListJson = intent.getStringExtra(KEY_SENTENCE_LIST);
        mSentenceList = new Gson().fromJson(sentenceListJson, new TypeToken<List<Sentence>>() {
        }.getType());

        String title = null;
        int categoryImageResId = 0;
        switch (mCategory) {
            case Sentence.CATEGORY_MORNING:
                title = getString(R.string.sentence_category_morning);
                categoryImageResId = R.drawable.title_category_morning;
                break;
            case Sentence.CATEGORY_SCHOOL:
                title = getString(R.string.sentence_category_school);
                categoryImageResId = R.drawable.title_category_school;
                break;
            case Sentence.CATEGORY_PLAYGROUND:
                title = getString(R.string.sentence_category_playground);
                categoryImageResId = R.drawable.title_category_playground;
                break;
            case Sentence.CATEGORY_EAT:
                title = getString(R.string.sentence_category_eat);
                categoryImageResId = R.drawable.title_category_eat;
                break;
            case Sentence.CATEGORY_WEEKEND:
                title = getString(R.string.sentence_category_holiday);
                categoryImageResId = R.drawable.title_category_weekend;
                break;
        }

        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }*/
        ImageView categoryImageView = findViewById(R.id.category_image_view);
        categoryImageView.setImageResource(categoryImageResId);

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

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    private void setupPager() {
        SentencePagerAdapter adapter = new SentencePagerAdapter(
                SentenceDetailsActivity.this,
                getSupportFragmentManager(),
                mSentenceList
        );

        ViewPager viewPager = findViewById(R.id.view_pager);

        /*int pagerPadding = 50;
        viewPager.setClipToPadding(false);
        viewPager.setPadding(pagerPadding, 0, pagerPadding, 0);*/

        viewPager.setAdapter(adapter);
    }

    @Override
    public void playSoundFromAsset(String fileName) {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }

            mMediaPlayer.release();
            mMediaPlayer = new MediaPlayer();

            AssetFileDescriptor descriptor = getAssets().openFd(fileName);
            mMediaPlayer.setDataSource(
                    descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(),
                    descriptor.getLength()
            );
            descriptor.close();

            mMediaPlayer.prepare();
            mMediaPlayer.setVolume(1f, 1f);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
            Utils.showOkDialog(
                    SentenceDetailsActivity.this,
                    "Error",
                    e.getMessage(),
                    null
            );
        }
    }

    @Override
    public void playSoundFromUrl(String url, final View button, final View progress) {
        Log.i(TAG, "Sound URL: ".concat(url));

        button.setEnabled(false);
        progress.setVisibility(View.VISIBLE);

        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }

            mMediaPlayer.release();
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    button.setEnabled(true);
                    progress.setVisibility(View.INVISIBLE);
                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    button.setEnabled(true);
                    progress.setVisibility(View.INVISIBLE);
                    return false;
                }
            });

            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(url);

            mMediaPlayer.prepare();
            mMediaPlayer.setVolume(1f, 1f);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.start();

        } catch (Exception e) {
            button.setEnabled(true);
            progress.setVisibility(View.INVISIBLE);

            e.printStackTrace();
            Utils.showOkDialog(
                    SentenceDetailsActivity.this,
                    "Error",
                    e.getMessage(),
                    null
            );
        }
    }

    private static class SentencePagerAdapter extends FragmentStatePagerAdapter {

        private Context mContext;
        private List<Sentence> mSentenceList;

        SentencePagerAdapter(Context context,  @NonNull FragmentManager fm, List<Sentence> sentenceList) {
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
