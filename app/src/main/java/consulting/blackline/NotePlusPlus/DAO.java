package consulting.blackline.NotePlusPlus;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAO {
    @Insert
    void insert (Note note);

    @Update
    void update (Note note);

    @Delete
    void delete (Note note);

    @Query("DELETE FROM EasyNoteTable")
    void deleteAllNotes();

    @Query("SELECT * FROM EasyNoteTable")
    LiveData<List<Note>> getAllNotes();
}
