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

public class EditActivity extends AppCompatActivity {
    TextView titleTxtView;
    EditText descEditTxt;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
         titleTxtView = (TextView) findViewById(R.id.titleTxtView);
         descEditTxt = (EditText) findViewById(R.id.descEditTxt);
         sharedPreferences = this.getSharedPreferences("com.example.ideabox", Context.MODE_PRIVATE);
         String title = getIntent().getStringExtra("title");
         titleTxtView.setText(title);
         descEditTxt.setText(sharedPreferences.getString(title,"Not found!"));
    }

    public void edit(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(titleTxtView.getText().toString(),descEditTxt.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"Successfully edited!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
