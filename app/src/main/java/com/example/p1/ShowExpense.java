package com.example.p1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p1.database.Expense;
import com.example.p1.listAdapter.ExpenseListAdapter;

import java.util.List;

public class ShowExpense extends AppCompatActivity {
    private EconomyViewModel economyViewModel;
    private Button showExpensesWithinDateinterval;
    static final String CHOICE = "choice" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        showExpensesWithinDateinterval = (Button) findViewById(R.id.btn);
        showExpensesWithinDateinterval.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent (ShowExpense.this, Dateinterval.class);
                intent.putExtra(CHOICE, "2");
                startActivity(intent);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ExpenseListAdapter adapter = new ExpenseListAdapter();
        recyclerView.setAdapter(adapter);

        economyViewModel = ViewModelProviders.of(this).get(EconomyViewModel.class);
        economyViewModel.getAllExpenses().observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenses) {
                adapter.setExpenses(expenses);
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
                economyViewModel.deleteExpense(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ShowExpense.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ExpenseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Expense expense) {
                Intent intent = new Intent(ShowExpense.this, ShowSelectedExpense.class);
                intent.putExtra(SetNote.EXTRA_ID, expense.getId());
                intent.putExtra(SetNote.EXTRA_TITLE, expense.getTitle());
                intent.putExtra(SetNote.EXTRA_SUM, expense.getSum());
                intent.putExtra(SetNote.EXTRA_CATEGORY, expense.getCategory());
                intent.putExtra(SetNote.EXTRA_DATE, expense.getDate());
                intent.putExtra(SetNote.EXTRA_BARCODE, expense.getBarcode());
                startActivity(intent);

            }
        } );
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
                economyViewModel.deleteAllExpenses();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateList (){
        LiveData<List<Expense>> allNotes = economyViewModel.getAllExpenses();
    }
}
