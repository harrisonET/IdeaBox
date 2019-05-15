package com.example.ideabox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText ideaEditTxt;
    EditText descEditTxt;
    Spinner spinner;
    Spinner catSpinner;
    LinearLayout mileLinear;
    TextView displayCatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        displayCatView  = (TextView)findViewById(R.id.displayCatView);
        ideaEditTxt = (EditText)findViewById(R.id.ideaEditTxt);
        descEditTxt =   (EditText)findViewById(R.id.descEditTxt);

        //milestone linearlayout
        mileLinear = (LinearLayout) findViewById(R.id.milestoneLinear);

        catSpinner = (Spinner) findViewById(R.id.catSpinner);
        catSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerCat, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        catAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        // Apply the adapter to the spinner
        catSpinner.setAdapter(catAdapter);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void addNew(View view){
        String title = ideaEditTxt.getText().toString();
        String desc = descEditTxt.getText().toString();

        List<Milestone> mList = new ArrayList<Milestone>();

        //get milestone
        Log.i("Tag for mileStone: ",Integer.toString(mileLinear.getChildCount()));
        if (mileLinear.getChildCount() > 0){
            for(int i = 0; i < mileLinear.getChildCount();i++){
                EditText ed = (EditText)mileLinear.getChildAt(i);
                mList.add(new Milestone(ed.getText().toString(),false));
            }
        }

        String category;
        //get get category
        switch (displayCatView.getText().toString()){
            case "Unlisted":category = "Unlisted";Log.i("Tag", "Unlisted Cat");break;
            case "For Fun":category = "For Fun";Log.i("Tag", "For Fun Cat"); break;
            case "Doing": category = "Doing";Log.i("Tag", "Doing Cat");break;
            default:category = "Error";Log.i("Tag", "Default Cat");
        }

        if (ideaChecker(title,desc) == true) {
            //insert into database sql
            Idea idea = new Idea(title,desc,category,mList);
            String sql = "INSERT INTO Idea (name,data) VALUES (?, ?)";
            SQLiteStatement statement = MainActivity.eventsDB.compileStatement(sql);
            try {
                byte[] objByte = com.example.ideabox.ObjectSerializer.serialize(idea);
                statement.bindString(1, idea.getName());
                statement.bindBlob(2,objByte);
                statement.execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            //displayDB();
            Toast.makeText(getApplicationContext(), "Idea called " + title + " is created!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void displayDB(){
        Cursor c = MainActivity.eventsDB.rawQuery("SELECT * FROM Idea", null);
        int nameIndex = c.getColumnIndex("name");
        int ideaIndex = c.getColumnIndex("data");
        int idIndex = c.getColumnIndex("id");
        c.moveToFirst();
        try {
            while (c != null) {
                Log.i("Name: ", c.getString(nameIndex));
                Log.i("Data: ", com.example.ideabox.ObjectSerializer.deserialize(c.getBlob(ideaIndex)).toString());
                Log.i("Id: ", Integer.toString(c.getInt(idIndex)));
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean ideaChecker(String title, String desc){
        boolean ok = false;
        String warning = "";
        if (title.isEmpty())
            warning +=  "Title Or Description ";
        else
            ok = true;

        //check for milestone
        if (mileLinear.getChildCount() > 0){
            for(int i = 0; i < mileLinear.getChildCount();i++){
                EditText mile = (EditText)mileLinear.getChildAt(i);
                if (mile.getText().toString().isEmpty()){
                    if (warning.equals(""))
                        warning += " One or more of the milestone";
                    else {
                        warning += ", One or more of the milestone";
                    }
                    ok = false;
                    break;
                }
            }
        }
        if (!warning.isEmpty()) {
            warning += " cannot be empty!";
            Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_LONG).show();

        }
        return ok;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Test Spinner: ",parent.getItemAtPosition(position).toString() );
        boolean f = parent.getItemAtPosition(position).toString().matches(".*\\d.*");
        if (f){
            mileLinear.removeAllViews();
            int size = Integer.parseInt(parent.getItemAtPosition(position).toString());
            generateMilestoneList(size);
        }else{
            displayCatView.setText(parent.getItemAtPosition(position).toString());
            displayCatView.setTextSize(18);
        }
    }

    public void generateMilestoneList(int size){
        for (int i = 0; i < size; i++) {
            // create a new textview
            final EditText rowEditText = new EditText(this);
            // set some properties of rowTextView or something
            rowEditText.setTextSize(18);
            rowEditText.setHint((i+1) + "" );
            // add the textview to the linearlayout
            mileLinear.addView(rowEditText);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
