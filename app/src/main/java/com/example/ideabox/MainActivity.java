package com.example.ideabox;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ideabox.Adapter.SectionsPageAdapter;
import com.example.ideabox.KanbanFragment.DoingFragment;
import com.example.ideabox.KanbanFragment.ForFunFragment;
import com.example.ideabox.KanbanFragment.UnlistedFragment;
import com.example.ideabox.Model.GeneralActivity;
import com.example.ideabox.Model.Idea;
import com.example.ideabox.Serializer.ObjectSerializer;

import java.util.ArrayList;

public class MainActivity extends GeneralActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    public static SectionsPageAdapter sectionsPageAdapter;
    public static Fragment unlistedF;
    public static Fragment forFunF;
    public static Fragment doingF;
    public static SQLiteDatabase eventsDB;
    //idea list for each category
    public static ArrayList<Idea>uIdeaList;
    public static ArrayList<Idea>fIdeaList;
    public static ArrayList<Idea>dIdeaList;
    FloatingActionButton fab;

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
        fab = findViewById(R.id.fabAddIdea);
        eventsDB = this.openOrCreateDatabase("IdeaDB", MODE_PRIVATE, null);
        eventsDB.execSQL("CREATE TABLE IF NOT EXISTS Idea (name VARCHAR, data BLOB, id INTEGER PRIMARY KEY)");
        //to erase all
        //eventsDB.execSQL("DELETE FROM Idea");

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                    startActivity(intent);
            }
        });

        fillIdeaList();
    }

    public void fillIdeaList(){
        Log.i("Test", "Displaying DB");
        displayDB();
        loadDB();
//        Log.i("U", uIdeaList.toString());
//        Log.i("ForFunList", fIdeaList.toString());
//        Log.i("DoingList", dIdeaList.toString());

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
                //Log.i("Data: ", com.example.ideabox.Serializer.ObjectSerializer.deserialize(c.getBlob(ideaIndex)).toString());
                //Log.i("Id: ", Integer.toString(c.getInt(idIndex)));
                //put into ideaList
                Idea idea = (Idea) ObjectSerializer.deserialize(c.getBlob(ideaIndex));
                if (idea.getCategory().equals("Unlisted")){
                    uIdeaList.add(idea);
                }else if (idea.getCategory().equals("For Fun")) {
                    fIdeaList.add(idea);
                }
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
                Log.i("Data: ", ObjectSerializer.deserialize(c.getBlob(ideaIndex)).toString());
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

}
