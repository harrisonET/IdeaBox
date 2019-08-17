package com.example.ideabox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideabox.Model.Idea;
import com.google.gson.Gson;

public class EditActivity extends AppCompatActivity {
    EditText ideaEditTxt,descEditTxt;
    TextView displayCatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
         ideaEditTxt = (EditText) findViewById(R.id.ideaEditTxt);
         descEditTxt = (EditText) findViewById(R.id.descEditTxt);
         displayCatView  = (TextView)findViewById(R.id.displayCatView);
         String ideaStr = getIntent().getStringExtra("idea");
         Idea editedIdea = new Gson().fromJson(ideaStr,Idea.class);
         ideaEditTxt.setText(editedIdea.getName());
         descEditTxt.setText(editedIdea.getDescription());
         displayCatView.setText(editedIdea.getCategory());

    }

    public void edit(View view){
/*        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(titleTxtView.getText().toString(),descEditTxt.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"Successfully edited!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        */
    }

}
