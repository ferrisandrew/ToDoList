package com.zybooks.todolist;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.todolist.model.ToDoItem;

public class EditActivity extends AppCompatActivity {

    private EditText Input;
    private DatePicker Date;
    private Button SaveButton;
    private DatabaseHelper dbHelper;
    private int ItemID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //variables
        Input = findViewById(R.id.Input);
        Date = findViewById(R.id.Date);
        SaveButton = findViewById(R.id.SaveButton);
        dbHelper = new DatabaseHelper(this);

        // Get the ItemID from the Intent
        ItemID = getIntent().getIntExtra("ITEM_ID", -1);

        if (ItemID != -1) {
            loadItem();
        }
        //listener for the save button
        SaveButton.setOnClickListener(v -> {
            String name = Input.getText().toString();
            String date = Date.getYear() + "-" + (Date.getMonth() + 1) + "-" + Date.getDayOfMonth();
            boolean completed = false;

            if (ItemID == -1) {
                // Add a new item
                dbHelper.insertItem(new ToDoItem(name, date, completed));
            } else {
                // Update an existing item
                dbHelper.updateToDo(new ToDoItem(ItemID, name, date, completed));
            }

            finish();
        });
    }
    //load item method
    private void loadItem() {
        Cursor cursor = dbHelper.getAllItems();//loads all items

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("id");
                if (idIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    if (id == ItemID) {
                        int nameIndex = cursor.getColumnIndex("name");
                        int dateIndex = cursor.getColumnIndex("date");

                        if (nameIndex != -1 && dateIndex != -1) {
                            String name = cursor.getString(nameIndex);
                            String[] dateParts = cursor.getString(dateIndex).split("-");

                            Input.setText(name);//sets the text as the text in the input box

                            if (dateParts.length == 3) {//gets the date as integers
                                int year = Integer.parseInt(dateParts[0]);
                                int month = Integer.parseInt(dateParts[1]) - 1;
                                int day = Integer.parseInt(dateParts[2]);

                                Date.updateDate(year, month, day);//sets the date from datepicker
                            }
                        }
                        break;
                    }
                }
            }
            cursor.close();
        }
    }
}
