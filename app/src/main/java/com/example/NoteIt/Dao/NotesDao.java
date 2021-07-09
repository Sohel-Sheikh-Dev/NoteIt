package com.example.NoteIt.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.NoteIt.Model.Notes;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {

    @Query("SELECT * FROM NotesDatabase")
    LiveData<List<Notes>> getAllNotes();

    @Query("SELECT * FROM NotesDatabase ORDER BY notes_Priority DESC")
    LiveData<List<Notes>> highToLow();

    @Query("SELECT * FROM NotesDatabase ORDER BY notes_Priority ASC")
    LiveData<List<Notes>> lowToHigh();

    @Insert
    void insertNotes(Notes... notes);

    @Query("DELETE FROM NotesDatabase WHERE id = :id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Notes notes);

}
