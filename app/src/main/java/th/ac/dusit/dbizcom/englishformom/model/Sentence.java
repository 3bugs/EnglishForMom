package th.ac.dusit.dbizcom.englishformom.model;

public class Sentence {

    public static final int CATEGORY_MORNING = 0;
    public static final int CATEGORY_SCHOOL = 1;
    public static final int CATEGORY_PLAYGROUND = 2;
    public static final int CATEGORY_EAT = 3;
    public static final int CATEGORY_HOLIDAY = 4;

    public final String momEnglish;
    public final String momThai;
    public final String childEnglish;
    public final String childThai;
    public final int category;

    public Sentence(String momEnglish, String momThai, String childEnglish, String childThai, int category) {
        this.momEnglish = momEnglish;
        this.momThai = momThai;
        this.childEnglish = childEnglish;
        this.childThai = childThai;
        this.category = category;
    }
}
