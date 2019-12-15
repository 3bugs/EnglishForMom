package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Word;

public class VocabFragment extends Fragment {

    private static final String ARG_VOCAB_CATEGORY = "vocab_category";

    private String mCategory;
    private List<Word> mWordList;

    private RecyclerView mRecyclerView;

    private VocabFragmentListener mListener;

    public VocabFragment() {
        // Required empty public constructor
    }

    public static VocabFragment newInstance(String category) {
        VocabFragment fragment = new VocabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VOCAB_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_VOCAB_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vocab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mListener != null && mCategory != null) {
            mWordList = mListener.getWordListByCategory(mCategory);

            ImageView titleImageView = view.findViewById(R.id.title_image_view);
            int titleImageResId = 0;
            switch (mCategory) {
                case "morning":
                    titleImageResId = R.drawable.title_category_morning;
                    break;
                case "school":
                    titleImageResId = R.drawable.title_category_school;
                    break;
                case "playground":
                    titleImageResId = R.drawable.title_category_playground;
                    break;
                case "eat":
                    titleImageResId = R.drawable.title_category_eat;
                    break;
                case "weekend":
                    titleImageResId = R.drawable.title_category_weekend;
                    break;
            }
            titleImageView.setImageResource(titleImageResId);

            setupRecyclerView(view);
        }
    }

    private void setupRecyclerView(View view) {
        if (getActivity() == null) {
            return;
        }

        WordListAdapter adapter = new WordListAdapter(
                getActivity(),
                mWordList,
                mListener
        );

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpacingDecoration(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VocabFragmentListener) {
            mListener = (VocabFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement VocabFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface VocabFragmentListener {
        List<Word> getWordListByCategory(String category);

        void onClickWord(Word word);
    }

    private static class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

        private final Context mContext;
        private final List<Word> mWordList;
        private final VocabFragmentListener mListener;

        WordListAdapter(Context context, List<Word> wordList, VocabFragmentListener listener) {
            mContext = context;
            mWordList = wordList;
            mListener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_word, parent, false
            );
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder h, int position) {
            final Word word = mWordList.get(position);

            h.mWord = word;
            h.mWordTextView.setText(
                    String.valueOf(position + 1)
                            .concat(". ")
                            .concat(word.word)
            );
            h.mMeaningTextView.setText(word.meaning);
        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView mWordTextView;
            private final TextView mMeaningTextView;

            private Word mWord;

            ViewHolder(View itemView) {
                super(itemView);

                mWordTextView = itemView.findViewById(R.id.word_text_view);
                mMeaningTextView = itemView.findViewById(R.id.meaning_text_view);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onClickWord(mWord);
                    }
                });
            }
        }
    }

    public class SpacingDecoration extends RecyclerView.ItemDecoration {

        private final static int MARGIN_TOP_IN_DP = 8;
        private final static int MARGIN_BOTTOM_IN_DP = 16;
        private final int mMarginTop, mMarginBottom;

        SpacingDecoration(@NonNull Context context) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            mMarginTop = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    MARGIN_TOP_IN_DP,
                    metrics
            );
            mMarginBottom = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    MARGIN_BOTTOM_IN_DP,
                    metrics
            );
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            final int itemPosition = parent.getChildAdapterPosition(view);
            if (itemPosition == RecyclerView.NO_POSITION) {
                return;
            }
            if (itemPosition == 0) {
                outRect.top = mMarginTop;
            }
            final RecyclerView.Adapter adapter = parent.getAdapter();
            if ((adapter != null) && (itemPosition == adapter.getItemCount() - 1)) {
                outRect.bottom = mMarginBottom;
            }
        }
    }

}
