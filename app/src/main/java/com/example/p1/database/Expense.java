package com.example.p1.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense")
public class Expense {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "expenseId")
    private int id;

    @ColumnInfo (name = "expenseTitle")
    private String title;

    @ColumnInfo (name = "expenseSum")
    private int sum;

    @ColumnInfo (name = "expenseCategory")
    private String category;

    @ColumnInfo (name = "expenseDate")
    private String date;

    @ColumnInfo (name = "barcode")
    private String barcode;

    ;


    public Expense (String title, int sum, String category, String date, String barcode){
        this.title=title;
        this.sum=sum;
        this.category=category;
        this.date=date;
        this.barcode=barcode;
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

    public String getBarcode(){
        return barcode;
    }





    public void setId(int id) {
        this.id=id;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setISum(int sum) {
        this.sum=sum;
    }

    public void setCategory(String category) {
        this.category=category;
    }

    public void setDate(String date) {
        this.date=date;
    }
    public void setBarcode(String barcode) {
        this.barcode=barcode;
    }
}
