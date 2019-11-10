package th.ac.dusit.dbizcom.englishformom.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import th.ac.dusit.dbizcom.englishformom.model.Sentence;

@Dao
public interface SentenceDao {

    @Query("SELECT * FROM sentence")
    List<Sentence> getAll();

    @Query("SELECT * FROM sentence WHERE category = :category")
    List<Sentence> getByCategory(int category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Sentence... sentences);
}
