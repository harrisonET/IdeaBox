package com.example.ideabox;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        sharedPreferences = this.getSharedPreferences("com.example.ideabox", Context.MODE_PRIVATE);
        loadIdeas();
    }

    public void loadIdeas(){
        int counter = 0;
        Map<String,?> keys = sharedPreferences.getAll();
        if (keys.size() != 0) {
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.i("map values", entry.getKey() + ": " +
                        entry.getValue().toString());
                TextView textView = (TextView) linearLayout.getChildAt(counter);
                textView.setText(entry.getKey());
                textView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.edittext_border));
                counter++;
            }
        }
    }

    public void addIdeas(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void eraseAll(View view){
        new AlertDialog.Builder(this)
                .setTitle("Erase All")
                .setMessage("Do you want to delete all of your ideas ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        linearLayout.removeAllViews();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Alert", "Cancelled");
                    }
                }).show();
    }

}
