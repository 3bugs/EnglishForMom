package th.ac.dusit.dbizcom.englishformom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.Locale;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class SentenceDetailsFragment extends Fragment {

    private static final String ARG_SENTENCE_JSON = "sentence_json";

    private Sentence mSentence;

    private SentenceDetailsFragmentListener mListener;

    public SentenceDetailsFragment() {
        // Required empty public constructor
    }

    public static SentenceDetailsFragment newInstance(Sentence sentence) {
        SentenceDetailsFragment fragment = new SentenceDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SENTENCE_JSON, new Gson().toJson(sentence));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String sentenceJson = getArguments().getString(ARG_SENTENCE_JSON);
            mSentence = new Gson().fromJson(sentenceJson, Sentence.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sentence_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView momEnTextView = view.findViewById(R.id.mom_en_text_view);
        TextView momThTextView = view.findViewById(R.id.mom_th_text_view);
        TextView childEnTextView = view.findViewById(R.id.child_en_text_view);
        TextView childThTextView = view.findViewById(R.id.child_th_text_view);

        momEnTextView.setText(mSentence.momEnglish);
        momThTextView.setText(mSentence.momThai);
        childEnTextView.setText(mSentence.childEnglish);
        childThTextView.setText(mSentence.childThai);

        ImageButton momSoundButton = view.findViewById(R.id.mom_sound_button);
        momSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.playSoundFromAsset(mSentence.momSoundFile);
                }
            }
        });

        ImageButton childSoundButton = view.findViewById(R.id.child_sound_button);
        childSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.playSoundFromAsset(mSentence.childSoundFile);
                }
            }
        });

        String msg = String.format(
                Locale.getDefault(),
                "Mom sound: %s\nChild sound: %s",
                mSentence.momSoundFile,
                mSentence.childSoundFile
        );
        //Utils.showLongToast(getActivity(), msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SentenceDetailsFragmentListener) {
            mListener = (SentenceDetailsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SentenceDetailsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface SentenceDetailsFragmentListener {
        void playSoundFromAsset(String fileName);
    }
}
