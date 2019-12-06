package th.ac.dusit.dbizcom.englishformom.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

public class SentenceRepository {

    private static final String SENTENCE_CATEGORY_ALL = "all";

    private Context mContext;

    public SentenceRepository(Context mContext) {
        this.mContext = mContext;
    }

    public void getSentenceByCategory(String category, Callback callback) {
        GetTask getTask = new GetTask(mContext, category, callback);
        getTask.execute();
    }

    public void getSentenceAll(Callback callback) {
        GetTask getTask = new GetTask(mContext, SENTENCE_CATEGORY_ALL, callback);
        getTask.execute();
    }

    public void insertSentence(Sentence sentence, Callback callback) {
        InsertTask insertTask = new InsertTask(mContext, callback);
        insertTask.execute(sentence);
    }

    private static class GetTask extends AsyncTask<Void, Void, List<Sentence>> {

        private Context mContext;
        private Callback mCallback;
        private String mCategory;

        public GetTask(Context context, String category, Callback callback) {
            this.mContext = context;
            this.mCallback = callback;
            this.mCategory = category;
        }

        @Override
        protected List<Sentence> doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(mContext);

            List<Sentence> itemList = null;
            if (SENTENCE_CATEGORY_ALL.equals(mCategory)) {
                itemList = db.sentenceDao().getAll();
            } else {
                itemList = db.sentenceDao().getByCategory(mCategory);
            }
            return itemList;
        }

        @Override
        protected void onPostExecute(List<Sentence> sentenceList) {
            super.onPostExecute(sentenceList);

            mCallback.onGetSentence(mCategory, sentenceList);
        }
    } // ปิด GetTask

    private static class InsertTask extends AsyncTask<Sentence, Void, Void> {

        private Context mContext;
        private Callback mCallback;

        public InsertTask(Context context, Callback callback) {
            this.mContext = context;
            this.mCallback = callback;
        }

        @Override
        protected Void doInBackground(Sentence... sentences) {
            AppDatabase db = AppDatabase.getInstance(mContext);
            db.sentenceDao().insert(sentences[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCallback.onInsertSentenceSuccess();
        }
    } // ปิด InsertTask

    public interface Callback {
        void onGetSentence(String category, List<Sentence> sentenceList);
        void onInsertSentenceSuccess();
    }
}
