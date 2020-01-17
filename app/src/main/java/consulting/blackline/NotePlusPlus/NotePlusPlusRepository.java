package consulting.blackline.NotePlusPlus;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

public class NotePlusPlusRepository {

    private DAO dao;
    private LiveData<List<Note>> allNotes;

    public NotePlusPlusRepository(Application application) {
        NotePlusPlusDatabase database = NotePlusPlusDatabase.getInstance(application);
        dao = database.dao();
        allNotes = dao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(dao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(dao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(dao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(dao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private DAO dao;

        private InsertNoteAsyncTask(DAO _dao) {
            this.dao = _dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private DAO dao;

        private UpdateNoteAsyncTask(DAO _dao) {
            this.dao = _dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private DAO dao;

        private DeleteNoteAsyncTask(DAO _dao) {
            this.dao = _dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DAO dao;

        private DeleteAllNotesAsyncTask(DAO _dao) {
            this.dao = _dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllNotes();
            return null;
        }
    }
}
