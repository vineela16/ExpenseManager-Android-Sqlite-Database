package com.vineela.expensemanager.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by VINEELA on 17-04-2017.
 */

public class ExpenseTable {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public ExpenseTable(Context context){
        dbHelper=new DbHelper(context);
    }
    public void opendb(){
        sqLiteDatabase=dbHelper.getWritableDatabase();
    }
    public void closedb(){
        sqLiteDatabase.close();
    }
    public boolean insertrecord(String amount,String category,String notes,String date){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DbHelper.COL_AMOUNT,amount);
        contentValues.put(DbHelper.COL_CAT,category);
        contentValues.put(DbHelper.COL_NOTE,notes);
        contentValues.put(DbHelper.COL_DATE,date);
        long issaved=sqLiteDatabase.insert(DbHelper.TABLE_NAME,null,contentValues);
        if(issaved==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getallrecords(){
        return sqLiteDatabase.rawQuery("select * from "+DbHelper.TABLE_NAME,null);
    }
    public void deleterecord(String rowid){
        sqLiteDatabase.delete(DbHelper.TABLE_NAME,rowid+"="+DbHelper.ROW_ID,null);
    }
    public void deleteallrecords(){
        sqLiteDatabase.delete(DbHelper.TABLE_NAME,null,null);
    }
    public void update(String rowid,String amount,String category,String notes,String date){
        ContentValues contentValues= new ContentValues();
        contentValues.put(dbHelper.ROW_ID,rowid);
        contentValues.put(DbHelper.COL_AMOUNT,amount);
        contentValues.put(DbHelper.COL_CAT,category);
        contentValues.put(DbHelper.COL_NOTE,notes);
        contentValues.put(DbHelper.COL_DATE,date);
        sqLiteDatabase.update(DbHelper.TABLE_NAME,contentValues,rowid+"="+DbHelper.ROW_ID,null);
    }

}
