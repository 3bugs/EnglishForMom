package th.ac.dusit.dbizcom.englishformom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import th.ac.dusit.dbizcom.englishformom.etc.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView dialoguesImageView = findViewById(R.id.dialogues_image_view);
        dialoguesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SentencesActivity.class);
                startActivity(intent);
            }
        });

        ImageView vocabularyImageView = findViewById(R.id.vocabulary_image_view);
        vocabularyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showLongToast(MainActivity.this, "ยังไม่เสร็จครับ");
            }
        });

        ImageView animationImageView = findViewById(R.id.animation_image_view);
        animationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimationActivity.class);
                startActivity(intent);
            }
        });

        ImageView developerImageView = findViewById(R.id.developer_image_view);
        developerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
