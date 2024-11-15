package com.zybooks.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Button addItemButton;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> itemList;
    private ArrayList<Integer> itemIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        addItemButton = findViewById(R.id.addItemButton);

        itemList = new ArrayList<>();
        itemIds = new ArrayList<>();

        loadItems();

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = itemIds.get(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("ITEM_ID", itemId);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = itemIds.get(position);
                dbHelper.deleteItem(itemId);
                loadItems();
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void loadItems() {
        itemList.clear();
        itemIds.clear();
        Cursor cursor = (Cursor) dbHelper.getAllItems();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    int completed = cursor.getInt(cursor.getColumnIndexOrThrow("completed"));
                    String itemText = name + " - " + date + (completed == 1 ? " (Completed)" : "");
                    itemList.add(itemText);
                    itemIds.add(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                }
            } finally {
                cursor.close();
            }
        }
    }
}
