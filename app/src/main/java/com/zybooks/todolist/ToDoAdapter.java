package com.zybooks.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ArrayAdapter;

import com.zybooks.todolist.model.ToDoItem;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<ToDoItem> {

    private DatabaseHelper dbHelper;

    public ToDoAdapter(@NonNull Context context, List<ToDoItem> items, DatabaseHelper dbHelper) {
        super(context, 0, items);
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }

        ToDoItem item = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        CheckBox cbComplete = convertView.findViewById(R.id.cbComplete);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        tvName.setText(item.getName());
        tvDate.setText(item.getDate());
        cbComplete.setChecked(item.isCompleted());

        cbComplete.setOnClickListener(v -> dbHelper.markItemComplete(item.getId(), cbComplete.isChecked()));
        btnEdit.setOnClickListener(v -> {
            // Navigate to EditActivity for editing
            ((MainActivity) getContext()).editItem(item.getId());
        });
        btnDelete.setOnClickListener(v -> {
            // Delete item
            dbHelper.deleteItem(item.getId());
            ((MainActivity) getContext()).loadItems(); // Refresh ListView
        });

        return convertView;
    }
}
