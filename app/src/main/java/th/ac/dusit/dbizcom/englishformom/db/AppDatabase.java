package th.ac.dusit.dbizcom.englishformom.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

@Database(entities = {Sentence.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "english_for_mom.db";

    public abstract SentenceDao sentenceDao();

    private static AppDatabase mInstance;

    public static synchronized AppDatabase getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DB_NAME
                    )
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            final List<Sentence> sentenceList = new ArrayList<>();
                            populateData(sentenceList);
                            final Sentence[] sentences = sentenceList.toArray(new Sentence[0]);

                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mInstance.sentenceDao().insert(sentences);
                                }
                            });
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                        }
                    })
                    .build();
        }
        return mInstance;
    }

    private static void populateData(List<Sentence> sentenceList) {
        sentenceList.add(new Sentence(
                0,
                "Good morning my little angel. It's time to get out of bed.",
                "Morning1.mp3",
                "อรุณสวัสดิ์นางฟ้าตัวน้อย ถึงเวลาที่ลูกต้องลุกจากเตียงแล้วนะ",
                "Good morning, mom. I'm still sleepy.",
                "Morningb1.mp3",
                "อรุณสวัสดิ์ค่ะแม่ แต่หนูยังง่วงอยู่เลย",
                Sentence.CATEGORY_MORNING
        ));

        sentenceList.add(new Sentence(
                0,
                "Did you have a goodnight sleep?",
                "Morning2.mp3",
                "เมื่อคืนนอนหลับฝันดีไหมลูก",
                "No, I dreamed of ghosts chasing me. It was scary.",
                "Morningb2.mp3",
                "ไม่ค่ะ หนูฝันถึงผีกำลังไล่หนู มันน่ากลัวมากเลยค่ะ",
                Sentence.CATEGORY_MORNING
        ));

        sentenceList.add(new Sentence(
                0,
                "Next time, you should pray before you sleep.",
                "Morning3.mp3",
                "คราวหน้าต้องสวดมนต์ก่อนนอนนะลูก",
                "I will remember that mom.",
                "Morningb3.mp3",
                "หนูจะจำไว้ค่ะแม่",
                Sentence.CATEGORY_MORNING
        ));

        sentenceList.add(new Sentence(
                0,
                "OK, it's 6 o'clock it's time for you to take a shower and brush your teeth.",
                "Morning4.mp3",
                "โอเค ตอนนี้6โมงเช้าแล้ว ถึงเวลาที่ลูกจะต้องไปอาบน้ำแปรงฟันแล้วนะ",
                "I'm lazy. My body feels heavy.",
                "Morningb4.mp3",
                "หนูขี้เกียจจังเลยค่ะแม่ หนูตัวรู้สึกหนักไปหมด",
                Sentence.CATEGORY_MORNING
        ));

        sentenceList.add(new Sentence(
                0,
                "Your Breakfast is ready, sausages, egg and chocolate milk.",
                "Morning5.mp3",
                "อาหารเช้าของลูกพร้อมแล้วนะ มีไส้กรอก ไข่ และ นมช็อคโกแลต",
                "Yeah ! My favorites.",
                "Morningb5.mp3",
                "อาหารโปรดของหนูเลยค่ะคุณแม่",
                Sentence.CATEGORY_MORNING
        ));

        sentenceList.add(new Sentence(
                0,
                "Hurry up ! It's time to go to school. Your school bus is waiting.",
                "Morning6.mp3",
                "เร็วเข้าลูก ถึงเวลาที่ลูกต้องไปโรงเรียนแล้วนะ รถบัสโรงเรียนกำลังรอลูกอยู่",
                "Yes, mom. I'm coming.",
                "Morningb6.mp3",
                "ค่ะแม่หนูมาแล้วค่ะ",
                Sentence.CATEGORY_MORNING
        ));

        sentenceList.add(new Sentence(
                0,
                "Are you sure you have everything you need in your bag?",
                "Off1.mp3",
                "ลูกแน่ใจแล้วนะ ว่าลูกใส่ของที่จำเป็นทุกอย่างลงในกระเป๋าแล้ว?",
                "I think so mom.",
                "Offb1.mp3",
                "หนูคิดว่าอย่างนั้นนะคะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "How about the homework you did last night? Did you bring it?",
                "Off2.mp3",
                "การบ้านที่ลูกทำเมื่อคืนล่ะ? ได้เอามาด้วยไหมลูก",
                "Yes, mom. It's here.",
                "Offb2.mp3",
                "อยู่นี่ค่ะแม่",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Here's your allowance for today.",
                "Off3.mp3",
                "นี่ค่าขนมของลูกสำหรับวันนี้",
                "Thank you mom.",
                "Offb3.mp3",
                "ขอบคุณค่ะแม่",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Listen to your teachers attentively, participate actively in the activities.",
                "Off4.mp3",
                "ตั้งใจฟังคุณครูนะและมีส่วนร่วมในกิจกรรมด้วยนะลูก",
                "Yes, mom.",
                "Offb4.mp3",
                "ค่ะแม่",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Don't quarrel against anyone.",
                "Off5.mp3",
                "อย่าไปทะเลาะ ต่อต้านกับใครนะลูก",
                "I won't mom.",
                "Offb5.mp3",
                "หนูจะไม่ทำค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Eat up your lunch.",
                "Off6.mp3",
                "กินอาหารกลางวันด้วยนะลูก",
                "Okay, mom.",
                "Offb6.mp3",
                "ค่ะแม่",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Okay, give mom a big hug and kiss. Off you go.",
                "Off7.mp3",
                "ให้แม่กอดกับหอมก่อนจะไปหน่อยลูก",
                "Okay, see you in the afternoon, mom.",
                "Offb7.mp3",
                "ค่ะแม่ เจอกันตอนเย็นค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "How was school today?",
                "After1.mp3",
                "ที่โรงเรียนวันนี้เป็นยังไงบ้างลูก?",
                "It was fun.",
                "Afterb1.mp3",
                "สนุกดีค่ะแม่",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "What did you have for lunch at school?",
                "After2.mp3",
                "วันนี้ลูกกินข้าวเที่ยงกับอะไร?",
                "My favorite pork steak.",
                "Afterb2.mp3",
                "กินเสต็กหมูของโปรดของหนูค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "What was the best subject you studied today?",
                "After3.mp3",
                "วันนี้ลูกชอบเรียนวิชาอะไรที่สุด?",
                "English. We played a very interesting game.",
                "Afterb3.mp3",
                "วิชาภาษาอังกฤษค่ะ พวกเราเล่นเกมที่น่าสนใจด้วยค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "And what subject did you have a hard time?",
                "After4.mp3",
                "แล้ววิชาอะไรที่ลูกรู้สึกว่ามันยากล่ะ?",
                "As usual, Math. I can't do Division.",
                "Afterb4.mp3",
                "ตามเคยค่ะวิชาคณิตศาสตร์หนูทำไม่ได้เลยค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Did you have a fight with any of your classmates?",
                "After5.mp3",
                "ลููกได้ไปทะเลาะกับเพื่อนในห้องรึเปล่า?",
                "No. Everybody was friendly today.",
                "Afterb5.mp3",
                "ไม่ค่ะ วันนี้ทุกคนน่ารักมากค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Were you scolded or hit by any of your teachers?",
                "After6.mp3",
                "ลูกโดนคุณครูดุหรือโดนตีบ้างไหม?",
                "Just a pinch on my arm. I was not listening.",
                "Afterb6.mp3",
                "แค่โดนหยิกตรงแขนค่ะ เพราะหนูไม่ฟังเอง",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Next time listen attentively to your teachers.",
                "After7.mp3",
                "คราวหน้าตั้งใจฟังคุณครูนะลูก",
                "I will.",
                "Afterb7.mp3",
                "ค่ะแม่",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Do you have a lot of homework?",
                "After8.mp3",
                "มีการบ้านเยอะไหมลูก?",
                "Only in Science.",
                "Afterb8.mp3",
                "มีแค่วิทยาศาสตร์วิชาเดียวค่ะ",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "Did you have any tests or quizzes today?",
                "After9.mp3",
                "วันนี้มีสอบหรือแบบทดสอบไหมลูก",
                "Yes, mom. I had one in Social Studies.",
                "Afterb9.mp3",
                "มี1วิชาค่ะ วิชาสังคม",
                Sentence.CATEGORY_SCHOOL
        ));

        sentenceList.add(new Sentence(
                0,
                "There are many children at the playground today.",
                "Play1.mp3",
                "วันนี้มีเด็กหลายคนมาที่สนามเด็กเล่น",
                "That's better mom. The more the merrier.",
                "Playb1.mp3",
                "ดีแล้วค่ะ หนูจะได้รู้สึกเบิกบานกว่านี้",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "ฺBe careful not to hurt anyone, okay?",
                "Play2.mp3",
                "ระวังด้วยนะลูก อย่าไปทำให้ใครเจ็บเข้าใจไหม?",
                "Yes, mom.",
                "Playb2.mp3",
                "ค่ะแม่",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "Be friendly to everyone.",
                "Pkay3.mp3",
                "เป็นมิตรกับทุกคนนะลูก",
                "I will mom.",
                "Playb3.mp3",
                "ค่ะแม่่",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "Now, go join the other kids on that slide.",
                "Play4.mp3",
                "งั้นไปสนุกกับเด็กคนอื่นที่อยู่บนสไลด์ทางนั้น",
                "Okay, mom.",
                "Playb4.mp3",
                "โอเคค่ะแม่",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "Did you have fun on the slide?",
                "Play5.mp3",
                "สนุกกับสไลด์ไหมลูก?",
                "Yes, I enjoyed it very much.",
                "Playb5.mp3",
                "สมุกมากๆเลยค่ะแม่",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "Next, go try the merry-go-round.",
                "Play6.mp3",
                "ต่อไป ลองไปเล่นม้าหมุนดูไหมลูก?",
                "Yes, mom. I'm going.",
                "Playb6.mp3",
                "ค่ะหนูกำลังไปค่ะ",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "How was the merry-go-round?",
                "Play7.mp3",
                "ม้าหมุนเป็นไงบ้างลูก?",
                "It's fun but it made me feel dizzy.",
                "Playb7.mp3",
                "สนุกค่ะแต่มันทำให้หนูรู้สึกเวียนหัวค่ะ",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "Now, what do you want to play next?",
                "Play8.mp3",
                "ตอนนี้อยากเล่นอะไรต่อรึเปล่า?",
                "I just want to sit on the swing.",
                "Playb8.mp3",
                "หนูอยากนั่งแค่ชิงช้าค่ะ",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "Okay, it's time to go home.",
                "Play9.mp3",
                "ถึงเวลากลับบ้านแล้วค่ะ",
                "Thank you mom, for a fun and happy day.",
                "Playb9.mp3",
                "ขอบคุณสำหรับความสนุกและความสุขสำหรับวันนี้ค่ะแม่",
                Sentence.CATEGORY_PLAYGROUND
        ));

        sentenceList.add(new Sentence(
                0,
                "What day is it today?",
                "Week1.mp3",
                "วันนี้คือวันอะไร?",
                "It's Saturday. It's weekend.",
                "Weekb1.mp3",
                "วันนี้คือวันเสาร์เป็นวันหยุดสุดสัปดาห์",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "How's the weather today?",
                "Week2.mp3",
                "วันนี้สภาพอากาศเป็นอย่างไร?",
                "It's Sunday. It's a beautiful day today.",
                "Weekb2.mp3",
                "วันนี้แดดออกเป็นวันที่อากาศดีครับแม่",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "It's been a tiring week. Let's go somewhere to rest and relax.",
                "Week3.mp3",
                "เป็นสัปดาห์ที่น่าเบื่อ ไปหาที่ที่ผ่อนคลายเพื่อพักผ่อนกัน",
                "Yeah! Can we go to the beach? I love to swim.",
                "Weekb3.mp3",
                "เย่! พวกเราไปทะเลกันไหมคะ หนูชอบว่ายน้ำค่ะแม่",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "Ok! Let's get ready and pack the things we need.",
                "Week4.mp3",
                "โอเค! เตรียมตัวให้พร้อมและเก็บของสิ่งที่ต้องการ",
                "Ok, mom. Let me help you.",
                "Weekb4.mp3",
                "ค่ะแม่ ให้หนูช่วยเก็บนะคะ",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "Sunscreen, swimsuits, first-aid kit, towels, snacks, water bottles. Everything we need is ready.",
                "Week5.mp3",
                "ครีมกันแดด ชุดว่ายน้ำ ชุดปฐมพยาบาล ผ้าขนหนู ขนม น้ำ ทุกอย่างที่ต้องการพร้อมไหม",
                "Yes, mom. Everything is ready.",
                "Weekb5.mp3",
                "ค่ะแม่ ทุกอย่างพร้อมแล้วค่ะ",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "Go tell Dad to ready the car right away.",
                "Week6.mp3",
                "ไปบอกพ่อว่าให้เตรียมรถให้พร้อม",
                "Dad has been ready and waiting for half an hour.",
                "Weekb6.mp3",
                "พ่อพร้อมแล้วค่ะ และรออยู่ครึ่งชั่วโมงแล้วค่ะ",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "Ok, help me carry these things.",
                "Week7.mp3",
                "ช่วยแม่ยกของพวกนี้ด้วยนะ",
                "Sure mom.",
                "Weekb7.mp3",
                "ได้ค่ะแม่",
                Sentence.CATEGORY_WEEKEND
        ));

        sentenceList.add(new Sentence(
                0,
                "It's time to eat. The table is ready.",
                "Eat1.mp3",
                "ถึงเวลากินข้าวแล้ว โต๊ะพร้อมแล้วถึงเวลากินข้าว",
                "Coming mom. I'm starving.",
                "Eatb1.mp3",
                "มาแล้วค่ะแม่ หนูหิวมากเลย",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "Let me put some rice on your plate. Is this enough?",
                "Eat2.mp3",
                "ข้าวพอไหมลูกให้แม่เติมข้าวอีกไหม",
                "That's enough, mom. Thank you.",
                "Eatb2.mp3",
                "พอแล้วค่ะแม่ ขอบคุณค่ะ",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "We have green curry, fried chicken and stir-fried vegetables.",
                "Eat3.mp3",
                "เรามีไก่ทอด แกงเขียวหวานและผัดผัก",
                "Just a little of the green curry and the stir-fired vegetables but a lot of the fried chicken, please.",
                "Eatb3.mp3",
                "หนูขอแกงเขียวหวานกับผัดผัก นิดหน่อยค่ะ แต่ขอไก่ทอดเยอะๆเลยนะคะแม่",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "How's the food?",
                "Eat4.mp3",
                "อาหารเป็นยังไงบ้านลูก",
                "The green curry is spicy but the chicken is yummy.",
                "Eatb4.mp3",
                "แกงเขียวหวานเผ็ดมากค่ะ แต่ไก่ทอดอร่อยมากเลยค่ะ",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "Would you like some more?",
                "Eat5.mp3",
                "เอาอีกไหมลูก",
                "Yes, mom. Some more rice and chicken please.",
                "Eatb5.mp3",
                "ค่ะแม่ หนูขอเพิ่มข้าวกับไก่ทอดค่ะ",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "Chew your food thoroughly. You might get choke.",
                "Eat6.mp3",
                "เคี้ยวอาหารให้ละเอียดนะลูกไม่งั้นอาจทำให้ติดคอได้",
                "I will, mom.",
                "Eatb6.mp3",
                "ค่ะแม่",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "And remember not to talk with your mouth full.",
                "Eat7.mp3",
                "และจำไว้นะลูกห้ามพูดตอนที่ อาหารเต็มปากนะลูก",
                "I'll always remember that mom.",
                "Eatb7.mp3",
                "หนูจะจำสิ่งนั้นเสมอแค่แม่",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "How about the dessert?",
                "Eat8.mp3",
                "เอาของหวานไหมลูก?",
                "No, thanks mom. I'm very full.",
                "Eatb8.mp3",
                "ไม่ค่ะ หนูอิ่มมากแล้วค่ะ",
                Sentence.CATEGORY_EAT
        ));

        sentenceList.add(new Sentence(
                0,
                "Ok, go and wash your mouth and hands.",
                "Eat9.mp3",
                "ไปล้างมือล้างปากนะลูก",
                "Yes, mom. Thank you for a yummy dinner.",
                "Eatb9.mp3",
                "ค่ะแม่ ขอบคุณสำหรัยอาหารเย็นแสนอร่อยค่ะแม่",
                Sentence.CATEGORY_EAT
        ));

    }
}
