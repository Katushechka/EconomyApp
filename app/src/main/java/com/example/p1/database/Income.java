package com.example.p1.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "income")
public class Income {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo (name = "incomeId")
    private int id;

    @ColumnInfo (name = "incomeTitle")
    private String title;

    @ColumnInfo (name = "incomeSum")
    private int sum;

    @ColumnInfo (name = "incomeCategory")
    private String category;

    @ColumnInfo (name = "incomeDate")
    private String date;




    public Income (String title, int sum, String category, String date) {
        this.title=title;
        this.sum=sum;
        this.category=category;
        this.date=date;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public int getSum(){
        return sum;
    }

    public String getCategory(){
        return category;
    }

    public String getDate(){
        return date;
    }





    public void setId(int id) {
        this.id=id;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setSum(int sum) {
        this.sum=sum;
    }

    public void setCategory(String category) {
        this.category=category;
    }

    public void setDate(String date) {
        this.date=date;
    }
}
