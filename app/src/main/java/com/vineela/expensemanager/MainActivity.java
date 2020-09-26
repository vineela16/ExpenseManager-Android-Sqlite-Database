package com.vineela.expensemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vineela.expensemanager.Db.ExpenseTable;

public class MainActivity extends AppCompatActivity {
ListView listView;
    Cursor c;
    ExpenseTable expenseTable;
    Toolbar mActionBarToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expenseTable=new ExpenseTable(this);
        expenseTable.opendb();
        listView=(ListView)findViewById(R.id.listview);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
            }
        });

        setadapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in=new Intent(MainActivity.this,ViewActivity.class);
                in.putExtra("position",i);
                startActivity(in);
            }
        });

    }
    private void setadapter(){
        c=expenseTable.getallrecords();
        CustomAdaptor ca = new CustomAdaptor(this);
        listView.setAdapter(ca);
    }



    class CustomAdaptor extends BaseAdapter {
        private Context mcontext;
        public CustomAdaptor(Context c){
            mcontext=c;
        }
        @Override
        public int getCount() {
            return c.getCount();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            c.moveToPosition(i);
            String samount=c.getString(1);
            String scat=c.getString(2);
            String sdate=c.getString(4);


            view=getLayoutInflater().inflate(R.layout.customlayout,null);
            TextView amount=(TextView)view.findViewById(R.id.textView_amount);
            TextView cat=(TextView)view.findViewById(R.id.textView_cat);
            TextView date=(TextView)view.findViewById(R.id.textView_date);
            amount.setText("Amount Rs."+samount);
            cat.setText("Spent on:"+scat);
            date.setText(sdate);
            return view;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setadapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.deleteall) {
            AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this);
            b.setTitle("Delete ");
            b.setMessage("Are You Sure?");
            b.setNegativeButton("No",null);
            b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    expenseTable.deleteallrecords();
                    Toast.makeText(getApplicationContext(),"All Records Deleted",Toast.LENGTH_SHORT).show();
                    setadapter();
                }
            });
            b.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
