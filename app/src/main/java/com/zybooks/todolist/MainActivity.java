package com.zybooks.todolist;
import com.zybooks.todolist.ToDoAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.todolist.model.ToDoItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button addItemButton;
    private DatabaseHelper dbHelper;
    private ArrayList<ToDoItem> itemList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        addItemButton = findViewById(R.id.addItemButton);

        itemList = new ArrayList<>();

        // Set up the "Add Item" button
        addItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            // Open EditActivity for adding a new item
            startActivity(intent);
        });

        // Set up ListView click listeners
        listView.setOnItemClickListener((parent, view, position, id) -> {
            ToDoItem selectedItem = itemList.get(position);
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("ITEM_ID", selectedItem.getId()); // Pass the item ID to EditActivity
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ToDoItem selectedItem = itemList.get(position);
            dbHelper.deleteItem(selectedItem.getId());
            loadItems(); // Refresh the list
            Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            return true;
        });

        // Initial load of the list
        loadItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the list when returning to MainActivity
        loadItems();
    }

    public void loadItems() {
        // Clear the current list
        itemList.clear();
        Cursor cursor = dbHelper.getAllItems();

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    boolean completed = cursor.getInt(cursor.getColumnIndex("completed")) == 1;

                    itemList.add(new ToDoItem(id, name, date, completed));
                }
            } finally {
                cursor.close();
            }
        }

        // Create the adapter and set it to the ListView
        adapter = new ToDoAdapter(this, itemList, dbHelper);
        listView.setAdapter(adapter);
    }
    public void editItem(int itemId) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("ITEM_ID", itemId);
        startActivity(intent);
    }

}
