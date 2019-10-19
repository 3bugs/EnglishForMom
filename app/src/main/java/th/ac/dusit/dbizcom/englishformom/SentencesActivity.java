package th.ac.dusit.dbizcom.englishformom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_CATEGORY;
import static th.ac.dusit.dbizcom.englishformom.SentenceDetailsActivity.KEY_SENTENCE_LIST;

public class SentencesActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentences);

        ImageView playgroundImageView = findViewById(R.id.playground_image_view);
        ImageView morningImageView = findViewById(R.id.morning_image_view);
        ImageView eatImageView = findViewById(R.id.eat_image_view);
        ImageView holidayImageView = findViewById(R.id.holiday_text_view);
        ImageView schoolImageView = findViewById(R.id.school_image_view);

        playgroundImageView.setOnClickListener(this);
        morningImageView.setOnClickListener(this);
        eatImageView.setOnClickListener(this);
        holidayImageView.setOnClickListener(this);
        schoolImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int category = 0;
        List<Sentence> sentenceList = new ArrayList<>();

        switch (v.getId()) {
            case R.id.morning_image_view:
                category = Sentence.CATEGORY_MORNING;
                sentenceList.add(new Sentence(
                        "Good morning my little angel. It's time to get out of bed.",
                        "อรุณสวัสดิ์นางฟ้าตัวน้อย ถึงเวลาที่ลูกต้องลุกจากเตียงแล้วนะ",
                        "Good morning, mom. I'm still sleepy.",
                        "อรุณสวัสดิ์ค่ะแม่ แต่หนูยังง่วงอยู่เลย",
                        Sentence.CATEGORY_MORNING
                ));
                sentenceList.add(new Sentence(
                        "Did you have a goodnight sleep?",
                        "เมื่อคืนนอนหลับฝันดีไหมลูก",
                        "No, I dreamed of ghosts chasing me. It was scary.",
                        "ไม่ค่ะ หนูฝันถึงผีกำลังไล่หนู มันน่ากลัวมากเลยค่ะ",
                        Sentence.CATEGORY_MORNING
                ));
                sentenceList.add(new Sentence(
                        "Next time, you should pray before you sleep.",
                        "คราวหน้าต้องสวดมนต์ก่อนนอนนะลูก",
                        "I will remember that mom.",
                        "หนูจะจำไว้ค่ะแม่",
                        Sentence.CATEGORY_MORNING
                ));
                break;
            case R.id.school_image_view:
                category = Sentence.CATEGORY_SCHOOL;
                sentenceList.add(new Sentence(
                        "Are you sure you have everything you need in your bag?",
                        "ลูกแน่ใจแล้วนะ ว่าลูกใส่ของที่จำเป็นทุกอย่างลงในกระเป๋าแล้ว?",
                        "I think so mom.",
                        "หนูคิดว่าอย่างนั้นนะคะ",
                        Sentence.CATEGORY_SCHOOL
                ));
                sentenceList.add(new Sentence(
                        "How about the homework you did last night? Did you bring it? ",
                        "การบ้านที่ลูกทำเมื่อคืนล่ะ? ได้เอามาด้วยไหมลูก",
                        "Yes, mom. It's here.",
                        "อยู่นี่ค่ะแม่",
                        Sentence.CATEGORY_SCHOOL
                ));
                sentenceList.add(new Sentence(
                        "Here's your allowance for today.",
                        "นี่ค่าขนมของลูกสำหรับวันนี้",
                        "Thank you mom.",
                        "ขอบคุณค่ะแม่",
                        Sentence.CATEGORY_SCHOOL
                ));
                break;
            case R.id.playground_image_view:
                category = Sentence.CATEGORY_PLAYGROUND;
                sentenceList.add(new Sentence(
                        "There are many children at the playground today.",
                        "วันนี้มีเด็กหลายคนมาที่สนามเด็กเล่น",
                        "That's better mom. The more the merrier.",
                        "ดีแล้วค่ะ หนูจะได้รู้สึกเบิกบานกว่านี้",
                        Sentence.CATEGORY_PLAYGROUND
                ));
                sentenceList.add(new Sentence(
                        "ฺBe careful not to hurt anyone, okay?",
                        "ระวังด้วยนะลูก อย่าไปทำให้ใครเจ็บเข้าใจไหม?",
                        "ํYes, mom.",
                        "ค่ะแม่",
                        Sentence.CATEGORY_PLAYGROUND
                ));
                sentenceList.add(new Sentence(
                        "Be friendly to everyone.",
                        "เป็นมิตรกับทุกคนนะลูก",
                        "I will mom.",
                        "ค่ะแม่่",
                        Sentence.CATEGORY_PLAYGROUND
                ));
                break;
            case R.id.eat_image_view:
                category = Sentence.CATEGORY_EAT;
                sentenceList.add(new Sentence(
                        "It's time to eat. The table is ready.",
                        "ถึงเวลากินข้าวแล้ว โต๊ะพร้อมแล้วถึงเวลากินข้าว",
                        "Coming mom. I'm starving.",
                        "มาแล้วค่ะแม่ หนูหิวมากเลย",
                        Sentence.CATEGORY_EAT
                ));
                sentenceList.add(new Sentence(
                        "Let me put some rice on your plate. Is this enough? ",
                        "ข้าวพอไหมลูกให้แม่เติมข้าวอีกไหม",
                        "That's enough, mom. Thank you.",
                        "พอแล้วค่ะแม่ ขอบคุณค่ะ",
                        Sentence.CATEGORY_EAT
                ));
                sentenceList.add(new Sentence(
                        "We have green curry, fried chicken and stir-fried vegetables.",
                        "เรามีไก่ทอด แกงเขียวหวานและผัดผัก",
                        "Just a little of the green curry and the stir-fired vegetables but a lot of the fried chicken, please.",
                        "หนูขอแกงเขียวหวานกับผัดผัก นิดหน่อยค่ะ แต่ขอไก่ทอดเยอะๆเลยค่ะแม่",
                        Sentence.CATEGORY_EAT
                ));
                break;
            case R.id.holiday_text_view:
                category = Sentence.CATEGORY_HOLIDAY;
                sentenceList.add(new Sentence(
                        "What day is it today?",
                        "วันนี้คือวันอะไร?",
                        "It's Saturday. It's weekend.",
                        "วันนี้คือวันเสาร์เป็นวันหยุดสุดสัปดาห์",
                        Sentence.CATEGORY_HOLIDAY
                ));
                sentenceList.add(new Sentence(
                        "How's the weather today? ",
                        "วันนี้ี้สภาพอากาศเป็นอย่างไร?",
                        "It's Sunday. It's a beautiful day today.",
                        "วันนี้ี้แดดออกเป็นวันที่อากาศดีครับแม่",
                        Sentence.CATEGORY_HOLIDAY
                ));
                sentenceList.add(new Sentence(
                        "It's been a tiring week. Let's go somewhere to rest and relax.",
                        "เป็นสัปดาห์ที่น่าเบื่อ ไปหาที่ที่ผ่อนคลายเพื่อพักผ่อนกัน",
                        "Yeah! Can we go to the beach? I love to swim.",
                        "เย่! พวกเราไปทะเลกันไหมคะ หนูชอบว่ายน้ำค่ะแม่",
                        Sentence.CATEGORY_HOLIDAY
                ));
                break;
        }
        Intent intent = new Intent(SentencesActivity.this, SentenceDetailsActivity.class);
        intent.putExtra(KEY_SENTENCE_CATEGORY, category);
        intent.putExtra(KEY_SENTENCE_LIST, new Gson().toJson(sentenceList));
        startActivity(intent);
    }
}
