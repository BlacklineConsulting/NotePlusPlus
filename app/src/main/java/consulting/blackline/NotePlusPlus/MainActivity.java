package consulting.blackline.NotePlusPlus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NotePlusPlusViewModel notePlusPlusViewModel;
    private Calendar c;
    private String currentDate;
    private String currentTime;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        notePlusPlusViewModel = ViewModelProviders.of(this).get(NotePlusPlusViewModel.class);
        notePlusPlusViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                notePlusPlusViewModel.delete(adapter.getNotePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_CONTENT, note.getContent());
                intent.putExtra(AddEditNoteActivity.EXTRA_DATE, note.getDate());
                intent.putExtra(AddEditNoteActivity.EXTRA_TIME, note.getDate());
                startActivityForResult(intent, 2);
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if(mAuth.getCurrentUser() != null) {
                    mAuth.signOut();
                }
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.search:


                startActivity(new Intent(this, AlbumAPIActivity.class));
                finish();
                Toast.makeText(this, "See your Album", Toast.LENGTH_SHORT).show();
                return true;




            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        ((MenuInflater) menuInflater).inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_CONTENT);


            // set current date and time
            c = Calendar.getInstance();
            currentDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
            Log.d("DATE", "Date: "+ currentDate);
            currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));
            Log.d("TIME", "Time: "+currentTime);


            Note note = new Note(title, description, currentDate, currentTime);
            notePlusPlusViewModel.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_CONTENT);

            Note note = new Note(title, description, currentDate, currentTime);
            note.setId(id);
            notePlusPlusViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
