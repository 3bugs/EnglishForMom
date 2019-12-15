package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import th.ac.dusit.dbizcom.englishformom.etc.Utils;
import th.ac.dusit.dbizcom.englishformom.model.Word;
import th.ac.dusit.dbizcom.englishformom.net.ApiClient;
import th.ac.dusit.dbizcom.englishformom.net.GetVocabResponse;
import th.ac.dusit.dbizcom.englishformom.net.MyRetrofitCallback;
import th.ac.dusit.dbizcom.englishformom.net.WebServices;

public class VocabActivity extends AppCompatActivity implements
        VocabFragment.VocabFragmentListener {

    private ProgressBar mProgressView;

    private List<Word> mWordList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        if (mWordList == null) {
            doGetVocab();
        }
    }

    private void doGetVocab() {
        mProgressView = findViewById(R.id.progress_view);
        mProgressView.setVisibility(View.VISIBLE);

        Retrofit retrofit = ApiClient.getClient();
        WebServices services = retrofit.create(WebServices.class);

        Call<GetVocabResponse> call = services.getVocab();
        call.enqueue(new MyRetrofitCallback<>(
                this,
                null,
                mProgressView,
                new MyRetrofitCallback.MyRetrofitCallbackListener<GetVocabResponse>() {
                    @Override
                    public void onSuccess(GetVocabResponse responseBody) {
                        mWordList = responseBody.wordList;
                        setupPager();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Utils.showOkDialog(
                                VocabActivity.this,
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

    private void setupPager() {
        VocabPagerAdapter adapter = new VocabPagerAdapter(
                VocabActivity.this,
                getSupportFragmentManager(),
                new String[]{
                        "morning", "school", "playground", "eat", "weekend"
                }
        );

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
    }


    @Override
    public List<Word> getWordListByCategory(String category) {
        List<Word> tempList = new ArrayList<>();

        for (Word word : mWordList) {
            if (category.equalsIgnoreCase(word.category)) {
                tempList.add(word);
            }
        }
        return tempList;
    }

    @Override
    public void onClickWord(Word word) {

    }

    private static class VocabPagerAdapter extends FragmentStatePagerAdapter {

        private Context mContext;
        private String[] mVocabCategoryList;

        VocabPagerAdapter(Context context, @NonNull FragmentManager fm, String[] vocabCategoryList) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mContext = context;
            mVocabCategoryList = vocabCategoryList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return VocabFragment.newInstance(mVocabCategoryList[position]);
        }

        @Override
        public int getCount() {
            return mVocabCategoryList.length;
        }
    }
}
