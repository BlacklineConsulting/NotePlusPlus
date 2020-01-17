package consulting.blackline.NotePlusPlus;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Note.class},version = 1 ,exportSchema = true)
public abstract class NotePlusPlusDatabase extends RoomDatabase {

    private static NotePlusPlusDatabase instance;
    public abstract DAO dao();

    // singleton beneath

    public static synchronized NotePlusPlusDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NotePlusPlusDatabase.class, "EasyNoteTable")
                    .fallbackToDestructiveMigration()
                    .build();
        }


        return instance;
    }

}
