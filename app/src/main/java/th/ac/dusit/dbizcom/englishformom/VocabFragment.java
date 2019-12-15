package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Word;

public class VocabFragment extends Fragment {

    private static final String ARG_VOCAB_CATEGORY = "vocab_category";

    private String mCategory;
    private List<Word> mWordList;

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
            setupRecyclerView();
        }
    }

    private void setupRecyclerView() {
        //todo:
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
}
