package com.zybooks.todolist.model;

//todoitem class
public class ToDoItem {

//declaring variables
    private int id;
    private String name;
    private String date;
    private boolean completed;

//creates an item without an id
    public ToDoItem(String name, String date, boolean completed) {
        this.name = name;
        this.date = date;
        this.completed = completed;
    }
// creates an item with an id
    public ToDoItem(int id, String name, String date, boolean completed) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.completed = completed;
    }

    // Getter and Setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}