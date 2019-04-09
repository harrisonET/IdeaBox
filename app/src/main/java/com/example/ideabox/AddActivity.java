package com.example.ideabox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText ideaEditTxt;
    EditText descEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ideaEditTxt = findViewById(R.id.ideaEditTxt);
        descEditTxt =  findViewById(R.id.descEditTxt);
    }

    public void addNew(View view){
        String title = ideaEditTxt.getText().toString();
        String desc = descEditTxt.getText().toString();

        if (ideaChecker(title,desc) == true) {
            SharedPreferences sharedPref = this.getSharedPreferences("com.example.ideabox", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(title, desc);
            Toast.makeText(getApplicationContext(), "Idea called " + title + " is created!!", Toast.LENGTH_SHORT).show();
            editor.commit();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        ideaEditTxt.setText("");
        descEditTxt.setText("");
    }
    public boolean ideaChecker(String title, String desc){
        boolean ok = false;

        if (title.isEmpty())
            Toast.makeText(getApplicationContext(),"Cannot be empty!!!",Toast.LENGTH_LONG).show();
        else
            ok = true;

        return ok;
    }

}
