package com.zybooks.todolist;

import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.zybooks.todolist.model.ToDoItem;
import android.content.ContentValues;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todoList.db";

    private static final String TABLE_TODO = "todo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_COMPLETED = "completed";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_TODO + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_COMPLETED + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(sqLiteDatabase);
    }

    public void insertItem(ToDoItem item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_COMPLETED, item.isCompleted() ? 1 : 0);
        sqLiteDatabase.insert(TABLE_TODO, null, values);
    }

    public void updateToDo(ToDoItem item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_DATE, item.getDate());
        values.put(COLUMN_COMPLETED, item.isCompleted() ? 1 : 0);
        sqLiteDatabase.update(TABLE_TODO, values, COLUMN_ID + "=?", new String[]{String.valueOf(item.getId())});

    }

    public void deleteItem(int itemId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_TODO, COLUMN_ID + "=?", new String[]{String.valueOf(itemId)});
    }

    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TODO, null, null, null, null, null, COLUMN_DATE + " ASC");
    }

    public void markItemComplete(int itemId, boolean isCompleted) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, isCompleted ? 1 : 0);
        sqLiteDatabase.update(TABLE_TODO, values, COLUMN_ID + "=?", new String[]{String.valueOf(itemId)});
    }

}