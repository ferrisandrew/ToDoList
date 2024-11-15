package com.zybooks.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText Input;
    private DatePicker Date;
    private Button SaveButton;
    private DatabaseHelper dbHelper;
    private int ItemID = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Input.getText().toString();
                String date = Date.getYear() + "-" + (Date.getMonth() + 1) + "-" + Date.getDayOfMonth();

                if (ItemID == -1) {
                    dbHelper.insertItem(new ToDoItem(name, date, false));
                } else {
                    dbHelper.updateToDo(new ToDoItem(ItemID, name, date, false));
                }
                finish();
            }
        });
    }
}
