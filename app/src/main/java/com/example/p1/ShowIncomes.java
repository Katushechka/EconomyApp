package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowIncomes extends AppCompatActivity {
    private EconomyViewModel economyViewModel;
 //   private ShowAllIncomesFragment showAllIncomesFragment;
    private Button showIncomesWithinDateinterval;
    static final String CHOICE = "choice" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incomes);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        showIncomesWithinDateinterval = (Button) findViewById(R.id.btn);
        showIncomesWithinDateinterval.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ShowIncomes.this, Dateinterval.class);
                intent.putExtra(CHOICE, "1");
                startActivity(intent);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EconomyListAdapter adapter = new EconomyListAdapter();
        recyclerView.setAdapter(adapter);
//get an instance of ViewModel which is already exist
        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);
        economyViewModel.getAllNotes().observe(this, new Observer<List<Income>>() {
            @Override
            public void onChanged(@Nullable List<Income> incomes) {
                adapter.setIncomes(incomes);
            }
        });

        populateList();


        //delete an item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                economyViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ShowIncomes.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new EconomyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Income income) {
                Intent intent = new Intent(ShowIncomes.this, ShowSelectedIncome.class);
                intent.putExtra(SetNote.EXTRA_ID, income.getId());
                intent.putExtra(SetNote.EXTRA_TITLE, income.getTitle());
                intent.putExtra(SetNote.EXTRA_SUM, income.getSum());
                intent.putExtra(SetNote.EXTRA_CATEGORY, income.getCategory());
                intent.putExtra(SetNote.EXTRA_DATE, income.getDate());
                startActivity(intent);

            }
        } );

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

    }

    //delete all notes

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                economyViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void populateList (){
        LiveData<List<Income>> allNotes = economyViewModel.getAllNotes();
        //Log.e(allNotes);
    }
}
