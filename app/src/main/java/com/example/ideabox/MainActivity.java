package com.example.ideabox;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    SectionsPageAdapter sectionsPageAdapter;
    static Fragment unlistedF;
    static Fragment forFunF;
    static Fragment doingF;
    static SQLiteDatabase eventsDB;
    //idea list for each category
    static ArrayList<Idea>uIdeaList;
    static ArrayList<Idea>fIdeaList;
    static ArrayList<Idea>dIdeaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uIdeaList = new ArrayList<Idea>();
        fIdeaList = new ArrayList<Idea>();
        dIdeaList = new ArrayList<Idea>();

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(viewPager);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        eventsDB = this.openOrCreateDatabase("IdeaDB", MODE_PRIVATE, null);
        eventsDB.execSQL("CREATE TABLE IF NOT EXISTS Idea (name VARCHAR, data BLOB, id INTEGER PRIMARY KEY)");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.i("Page Selected: ", Integer.toString(i));

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        fillIdeaList();
        //loadIdeas();
    }

    public void fillIdeaList(){
        //displayDB();
        loadDB();
//        Log.i("U", uIdeaList.toString());
//        Log.i("F", fIdeaList.toString());
//        Log.i("D", dIdeaList.toString());

    }

    public void loadDB(){
        clearIdeaLists();
        Cursor c = eventsDB.rawQuery("SELECT * FROM Idea", null);
        int nameIndex = c.getColumnIndex("name");
        int ideaIndex = c.getColumnIndex("data");
        int idIndex = c.getColumnIndex("id");
        c.moveToFirst();
        try {
            while (c != null) {
                //Log.i("Name: ", c.getString(nameIndex));
                //Log.i("Data: ", com.example.ideabox.ObjectSerializer.deserialize(c.getBlob(ideaIndex)).toString());
                //Log.i("Id: ", Integer.toString(c.getInt(idIndex)));
                //put into ideaList
                Idea idea = (Idea) com.example.ideabox.ObjectSerializer.deserialize(c.getBlob(ideaIndex));
                if (idea.getCategory().equals("Unlisted")){
                    uIdeaList.add(idea);
                }else if (idea.getCategory().equals("For Fun"))
                    fIdeaList.add(idea);
                else if (idea.getCategory().equals("Doing")){
                    dIdeaList.add(idea);
                }else{
                    Log.i("Anomaly", "Detected");
                }
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void displayDB(){
        Cursor c = eventsDB.rawQuery("SELECT * FROM Idea", null);
        int nameIndex = c.getColumnIndex("name");
        int ideaIndex = c.getColumnIndex("data");
        int idIndex = c.getColumnIndex("id");
        c.moveToFirst();
        try {
            while (c != null) {
                Log.i("Name: ", c.getString(nameIndex));
                Log.i("Data: ", com.example.ideabox.ObjectSerializer.deserialize(c.getBlob(ideaIndex)).toString());
                Log.i("Id: ", Integer.toString(c.getInt(idIndex)));

                if (c != null)
                    c.moveToNext();
                else
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void clearIdeaLists(){
        uIdeaList = new ArrayList<Idea>();
        fIdeaList = new ArrayList<Idea>();
        dIdeaList = new ArrayList<Idea>();
    }

    public void loadIdeas(){

    }

    private void setUpViewPager(ViewPager viewPager){
        unlistedF = new UnlistedFragment();
        forFunF = new ForFunFragment();
        doingF = new DoingFragment();

        sectionsPageAdapter.addFragment(unlistedF, "Unlisted");
        sectionsPageAdapter.addFragment(forFunF, "For Fun");
        sectionsPageAdapter.addFragment(doingF, "Doing");
        viewPager.setAdapter(sectionsPageAdapter);
    }


    public void goToEditView(View view){
        TextView textView = (TextView) view;
        if (textView.getText().toString() != ""){
            Intent intent = new Intent(this,EditActivity.class);
            intent.putExtra("title",textView.getText().toString());
            startActivity(intent);
        }
    }

    public void addIdeas(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}
