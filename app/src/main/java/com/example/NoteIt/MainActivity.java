package com.example.NoteIt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.NoteIt.Activity.InsertNotesActivity;
import com.example.NoteIt.Adapter.NotesAdapter;
import com.example.NoteIt.Model.Notes;
import com.example.NoteIt.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesbtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecyler;
    NotesAdapter adapter;
    SearchView searchView;
    RelativeLayout emptll;
    LinearLayout filter;

    TextView nofilter, hightolow, lowtohigh;
    List<Notes> filternotesalllist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesbtn = findViewById(R.id.newNotesbtn);

        nofilter = findViewById(R.id.nofilter);
        hightolow = findViewById(R.id.highToLow);
        lowtohigh = findViewById(R.id.lowToHigh);



        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);

        nofilter.setOnClickListener(v -> {
            loadData(0);
            nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
            hightolow.setBackgroundResource(R.drawable.filter_ui_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_ui_shape);
        });

        hightolow.setOnClickListener(v -> {
            loadData(1);
            nofilter.setBackgroundResource(R.drawable.filter_ui_shape);
            hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_ui_shape);
        });

        lowtohigh.setOnClickListener(v -> {
            loadData(2);
            nofilter.setBackgroundResource(R.drawable.filter_ui_shape);
            hightolow.setBackgroundResource(R.drawable.filter_ui_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
        });

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesbtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });

//        recyclerView = findViewById(R.id.notes_recycler_view);
        emptll = findViewById(R.id.empty_views);
        filter = findViewById(R.id.filter);


        notesRecyler = findViewById(R.id.notesRecycler);

        notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filternotesalllist = notes;

            if (notes.isEmpty()){
                emptll.setBackgroundColor(getResources().getColor(R.color.white));
                notesRecyler.setVisibility(View.GONE);
                filter.setVisibility(View.GONE);
                emptll.setVisibility(View.VISIBLE);
            } else {
                notesRecyler.setVisibility(View.VISIBLE);
                filter.setVisibility(View.VISIBLE);
                emptll.setVisibility(View.GONE);


                //                notesRecyler.setBackgroundColor(Integer.parseInt("#E8E8E8"));
            }

            }
        });

    }

    public void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        } else if (i == 1) {
            notesViewModel.hightolow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        } else if (i == 2) {
            notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        }

    }

    public void setAdapter(List<Notes> notes) {
        notesRecyler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this,notes);
        notesRecyler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search notes Here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                NotesFilter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String newText) {
        ArrayList<Notes> FilterNames = new ArrayList<>();

        for(Notes notes:this.filternotesalllist){
            if(notes.notesTitle.contains(newText)||notes.notesSubtitle.contains(newText)){
                FilterNames.add(notes);
            }
        }
        this.adapter.searchNotes(FilterNames);
    }


}