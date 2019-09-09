package com.example.ideabox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideabox.KanbanFragment.DoingFragment;
import com.example.ideabox.KanbanFragment.ForFunFragment;
import com.example.ideabox.KanbanFragment.UnlistedFragment;
import com.example.ideabox.Model.GeneralActivity;
import com.example.ideabox.Model.Idea;
import com.example.ideabox.Serializer.ObjectSerializer;
import com.google.gson.Gson;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText ideaEditTxt,descEditTxt;
    TextView displayCatView;
    Idea editedIdea;
    Spinner catSpinner;
    String initName, initDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
         ideaEditTxt = (EditText) findViewById(R.id.ideaEditTxt);
         descEditTxt = (EditText) findViewById(R.id.descEditTxt);
         displayCatView  = (TextView)findViewById(R.id.displayCatView);
         String ideaStr = getIntent().getStringExtra("idea");
         editedIdea = new Gson().fromJson(ideaStr,Idea.class);
         ideaEditTxt.setText(editedIdea.getName());
         descEditTxt.setText(editedIdea.getDescription());
         initName = ideaEditTxt.getText().toString();
         initDesc = descEditTxt.getText().toString();

        catSpinner = (Spinner) findViewById(R.id.catSpinner);
        catSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerCat, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        catAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        // Apply the adapter to the spinner
        catSpinner.setAdapter(catAdapter);
        switch (editedIdea.getCategory()){
            case "Unlisted": catSpinner.setSelection(0);
            displayCatView.setText(catSpinner.getItemAtPosition(0).toString());break;
            case "For Fun": catSpinner.setSelection(1);
                displayCatView.setText(catSpinner.getItemAtPosition(1).toString());break;
            case "Doing": catSpinner.setSelection(2);
                displayCatView.setText(catSpinner.getItemAtPosition(2).toString());break;
        }

    }

    public void edit(View view){
        String name = ideaEditTxt.getText().toString();
        String desc = descEditTxt.getText().toString();
        editedIdea.setDescription(desc);
        editedIdea.setName(name);
        //edit in sql
        String sql = "UPDATE Idea SET name=?, data=? WHERE name=?";
        SQLiteStatement statement = MainActivity.eventsDB.compileStatement(sql);
        try{
            byte[] objByte = ObjectSerializer.serialize(editedIdea);
            statement.bindString(1, name);
            statement.bindBlob(2,objByte);
            statement.bindString(3,initName);
            statement.execute();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Successfully edited!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void editIdea(Idea idea){

/*        switch (idea.getCategory()){
            case "Unlisted":
                ((UnlistedFragment) MainActivity.unlistedF).refreshAdapter();
                break;
            case "For Fun":
                ((ForFunFragment) MainActivity.forFunF).refreshAdapter();
                break;
            case "Doing":
                ((DoingFragment) MainActivity.doingF).refreshAdapter();
                break;
            default:
                Log.i("IDEAADAPTER","ERROR NOT FOUND");
        }
        */
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        displayCatView.setText(parent.getItemAtPosition(position).toString());
        displayCatView.setTextSize(18);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
