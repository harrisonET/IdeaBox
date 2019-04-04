package com.example.harrison.ideabox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ideaEditText;
    EditText descEditText;
    RecyclerView recyclerView;
    SQLiteDatabase myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ideaEditText = (EditText) findViewById(R.id.ideaEditText);
        descEditText = (EditText) findViewById(R.id.descEditText);
        myDB = openOrCreateDatabase("idea_db", MODE_PRIVATE, null);
        myDB.execSQL(
                "CREATE TABLE IF NOT EXISTS ideasTable (title VARCHAR(200), description VARCHAR(500))"
        );
        readFromDB();
    }

    public void addNew(View view){
        String title = ideaEditText.getText().toString();
        String desc = descEditText.getText().toString();

        ContentValues row1 = new ContentValues();
        row1.put("title", title);
        row1.put("description", desc);
        myDB.insert("idea_db",null,row1);
        readFromDB();
    }

    public void readFromDB() {
        Cursor cursor = myDB.rawQuery("select title,description from ideasTable",null);
        Log.i("Tag", "Count: " + cursor.getColumnCount());

        while (cursor.moveToNext()){
            String title = cursor.getString(0);
            String desc = cursor.getString(1);
            Log.i("Result", "Col: " + title + " & " + desc);
        }
        cursor.close();
    }

}
