package th.ac.dusit.dbizcom.englishformom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener mOnClickImageViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.dialogues_image_view:
                    intent = new Intent(MainActivity.this, SentencesActivity.class);
                    break;
                case R.id.vocabulary_image_view:
                    intent = new Intent(MainActivity.this, VocabActivity.class);
                    break;
                case R.id.animation_image_view:
                    intent = new Intent(MainActivity.this, AnimationActivity.class);
                    break;
                case R.id.developer_image_view:
                    intent = new Intent(MainActivity.this, AboutActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView dialoguesImageView = findViewById(R.id.dialogues_image_view);
        dialoguesImageView.setOnClickListener(mOnClickImageViewListener);

        ImageView vocabularyImageView = findViewById(R.id.vocabulary_image_view);
        vocabularyImageView.setOnClickListener(mOnClickImageViewListener);

        ImageView animationImageView = findViewById(R.id.animation_image_view);
        animationImageView.setOnClickListener(mOnClickImageViewListener);

        ImageView developerImageView = findViewById(R.id.developer_image_view);
        developerImageView.setOnClickListener(mOnClickImageViewListener);
    }
}
