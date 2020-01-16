package com.example.android.access;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_VISITOR_ACTIVITY_REQUEST_CODE = 1;

    private VisitorViewModel mVisitorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        final VisitorAdapter adapter = new VisitorAdapter();
        recyclerView.setAdapter(adapter);

        mVisitorViewModel = ViewModelProviders.of(this).get(VisitorViewModel.class);
        mVisitorViewModel.getAllVisitors().observe(this, new Observer<List<Visitor>>() {
            @Override
            public void onChanged(List<Visitor> visitors) {
                adapter.setVisitors(visitors);
            }
        });

        //to handle deleting an item when swiped left or right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mVisitorViewModel.delete(adapter.getVisitor(viewHolder.getAdapterPosition()));
                Snackbar.make(recyclerView, "visitor deleted", Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //To respond to item click
        adapter.setOnItemClickListener(new VisitorAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(Visitor visitor) {
                Snackbar.make(recyclerView, "Add your action here ooo", Snackbar.LENGTH_SHORT).show();
            }
        });

        // to add new visitor, start the newvisitor activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addVisitorIntent = new Intent(MainActivity.this, NewVisitorActivity.class);
                startActivityForResult(addVisitorIntent, NEW_VISITOR_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_VISITOR_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String visitorFirstname = data.getStringExtra(NewVisitorActivity.EXTRA_VFIRSTNAME);
            String visitorSurname = data.getStringExtra(NewVisitorActivity.EXTRA_VSURNAME);
            String hostId = data.getStringExtra(NewVisitorActivity.EXTRA_HOSTID);
            String visitorCode = data.getStringExtra(NewVisitorActivity.EXTRA_VCODE);
            Date date = Calendar.getInstance().getTime();


            Visitor visitor = new Visitor(visitorFirstname, visitorSurname, hostId, visitorCode, date);
            mVisitorViewModel.insert(visitor);
        } else {
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.delete_all_visitors:
                mVisitorViewModel.deleteAll();
                Toast.makeText(this, "All Visitors Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
