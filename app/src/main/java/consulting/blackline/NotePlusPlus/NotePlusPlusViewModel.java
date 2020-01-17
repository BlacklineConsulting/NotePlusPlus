package consulting.blackline.NotePlusPlus;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import java.util.List;
import androidx.lifecycle.LiveData;

public class NotePlusPlusViewModel extends AndroidViewModel {

    private NotePlusPlusRepository repository;
    private LiveData<List<Note>> allNotes;
    public NotePlusPlusViewModel(@NonNull Application application) {
        super(application);
        repository = new NotePlusPlusRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }


    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
