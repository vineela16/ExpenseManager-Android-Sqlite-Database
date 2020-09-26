package com.vineela.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.vineela.expensemanager.Db.ExpenseTable;

import java.text.DateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
EditText amount,notes;
    Spinner cat;
    ExpenseTable expenseTable;
    Button save;
    String Catitem;
    Cursor c;
    String[] category={"Food","Shopping","HouseHold","Transport","Entertainment","Recharge","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        expenseTable=new ExpenseTable(this);
        amount=(EditText)findViewById(R.id.editText_amount);
        notes=(EditText)findViewById(R.id.editText_notes);
        cat=(Spinner)findViewById(R.id.spinner_cat);
        save=(Button)findViewById(R.id.button_save);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,category);
        cat.setAdapter(adapter);
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Catitem=category[i];
//                switch (i){
//                    case 0:
//                        Catitem=category[i];
//                        break;
//                    case 1:
//                        Catitem=category[1];
//                        break;
//                    case 2:
//                        Catitem=category[2];
//                        break;
//                    case 3:
//                        Catitem=category[3];
//                        break;
//                    case 4:
//                        Catitem=category[4];
//                        break;
//                    case 5:
//                        Catitem=category[5];
//                        break;
//                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent i = getIntent();
        int option = i.getIntExtra("edit",100);
        if (option == 1) {
            int position = i.getIntExtra("position", 0);
            expenseTable.opendb();
            c = expenseTable.getallrecords();
            c.moveToPosition(position);
            final String crowid=c.getString(0);
            String camount = c.getString(1);
            String ccat = c.getString(2);
            String cnotes=c.getString(3);
            amount.setText(camount);
            amount.setSelection(amount.getText().length());
            notes.setText(cnotes);
            notes.setSelection(notes.getText().length());
            save.setText("update");

            save.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View view) {

                    String samount = amount.getText().toString();
                    String snotes = notes.getText().toString();
                    Date date = new Date(java.lang.System.currentTimeMillis());
                    expenseTable.opendb();
                    expenseTable.update(crowid, samount,Catitem, snotes, DateFormat.getDateTimeInstance().format(date));
                    Toast.makeText(getApplicationContext(), " Updated", Toast.LENGTH_LONG).show();
                    finish();

                }
            });
        }
        else {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String samount = amount.getText().toString();
                    String snotes = notes.getText().toString();
                    Date date = new Date(java.lang.System.currentTimeMillis());
                    expenseTable.opendb();
                    expenseTable.insertrecord(samount, Catitem, snotes, DateFormat.getDateTimeInstance().format(date));
                    Toast.makeText(getApplicationContext(), " saved", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }

    }
}
