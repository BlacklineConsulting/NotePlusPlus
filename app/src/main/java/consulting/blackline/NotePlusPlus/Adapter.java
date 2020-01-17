package consulting.blackline.NotePlusPlus;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_note_item, parent, false);
        return new NoteHolder(itemView);
    }

    public Note getNotePosition(int position){
        return notes.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewContent.setText(currentNote.getContent());
        holder.textViewDate.setText(currentNote.getDate());
        holder.textViewTime.setText(currentNote.getTime());

    }

    @Override
    public int getItemCount() {

        int size = notes.size();
        return size;

    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewContent;

        private TextView textViewDate;
        private TextView textViewTime;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewContent = itemView.findViewById(R.id.text_view_content);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}