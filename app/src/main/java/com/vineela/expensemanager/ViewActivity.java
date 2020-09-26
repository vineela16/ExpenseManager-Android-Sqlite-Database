package com.vineela.expensemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vineela.expensemanager.Db.ExpenseTable;

public class ViewActivity extends AppCompatActivity {
TextView amount,cat,notes,date,notestitle;
    Button delete,edit;
    int position;
    ExpenseTable expenseTable;
    String samount,scat,snotes,rowid,sdate;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        amount=(TextView)findViewById(R.id.textView_amount);
        cat=(TextView)findViewById(R.id.textView_cat);
        notes=(TextView)findViewById(R.id.textView_notes);
        notestitle=(TextView)findViewById(R.id.textView_notestitle);
        date=(TextView)findViewById(R.id.textView_date);

        delete=(Button)findViewById(R.id.button_delete);
        edit=(Button)findViewById(R.id.button_edit);

        Intent intent=getIntent();
        position= intent.getIntExtra("position",0);
        expenseTable=new ExpenseTable(this);
        expenseTable.opendb();
        setdata();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b=new AlertDialog.Builder(ViewActivity.this);
                b.setTitle("Delete ");
                b.setMessage("Are You Sure?");
                b.setNegativeButton("No",null);
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        expenseTable.deleterecord(rowid);
                        Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                b.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ViewActivity.this,AddActivity.class);
                i.putExtra("edit",1);
                i.putExtra("position",position);
                startActivity(i);
            }
        });


    }
    public void setdata(){
        c= expenseTable.getallrecords();
        c.moveToPosition(position);
        rowid=c.getString(0);
        samount= c.getString(1);
        scat=c.getString(2);
        snotes=c.getString(3);
        sdate=c.getString(4);

        amount.setText("Rs."+samount);
        cat.setText(scat);
        if (!snotes.isEmpty()){
            notestitle.setText("Notes");
            notes.setText(snotes);
        }
        else {
            notestitle.setText(null);
            notes.setText(null);
        }
        date.setText(sdate);
    }

    @Override
    protected void onRestart() {
        setdata();
        super.onRestart();
    }
    }

