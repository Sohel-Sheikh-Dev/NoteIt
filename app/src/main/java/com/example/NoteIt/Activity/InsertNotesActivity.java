package com.example.NoteIt.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.NoteIt.Model.Notes;
import com.example.NoteIt.R;
import com.example.NoteIt.ViewModel.NotesViewModel;
import com.example.NoteIt.databinding.ActivityInsertNotesBinding;

import java.util.Date;


public class InsertNotesActivity extends AppCompatActivity {

    ActivityInsertNotesBinding binding;
    String title, subtitle, notes;
    NotesViewModel notesViewModel;
    String priority = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.doneNotesbtn.setOnClickListener(v -> {
            title = binding.notesTitle.getText().toString();
            subtitle = binding.notesSubtitle.getText().toString();
            notes = binding.notesData.getText().toString();

            if(TextUtils.isEmpty(binding.notesTitle.getText())){
                binding.notesTitle.setError("This field is mandatory");

            }
            else {
                CreateNotes(title, subtitle, notes);
            }
        });

        binding.greenPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.greenPriority.setImageResource(R.drawable.ic_baseline_check_24);
                binding.yellowPriority.setImageResource(0);
                binding.redPriority.setImageResource(0);
                priority = "1";
            }
        });
        binding.yellowPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.greenPriority.setImageResource(0);
                binding.yellowPriority.setImageResource(R.drawable.ic_baseline_check_24);
                binding.redPriority.setImageResource(0);
                priority = "2";
            }
        });
        binding.redPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.greenPriority.setImageResource(0);
                binding.yellowPriority.setImageResource(0);
                binding.redPriority.setImageResource(R.drawable.ic_baseline_check_24);
                priority = "3";
            }
        });

    }

    private void CreateNotes(String title, String subtitle, String notes) {

        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());

        Notes notes1 = new Notes();
        notes1.notesTitle = title;
        notes1.notesSubtitle = subtitle;
        notes1.notes = notes;
        notes1.notesDate = sequence.toString();
        notes1.notesPriority = priority;
        notesViewModel.insertNote(notes1);

        Toast.makeText(this, "Notes Created Sucessfully", Toast.LENGTH_SHORT).show();
        finish();

    }
}