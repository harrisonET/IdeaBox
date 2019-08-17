package com.example.ideabox.KanbanFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ideabox.Adapter.IdeaAdapter;
import com.example.ideabox.MainActivity;
import com.example.ideabox.R;

public class UnlistedFragment extends Fragment{
/*    ListView listView;
    View view;
    ArrayAdapter<Idea>arrayAdapter;
    int image;
    */

    public RecyclerView recyclerView;
    public IdeaAdapter ideaAdapter;
    View view;


    public UnlistedFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        image = R.drawable.menu;
        arrayAdapter = new ArrayAdapter<Idea>(getActivity(),android.R.layout.simple_list_item_1, MainActivity.uIdeaList);
        //arrayAdapter = new ArrayAdapter<Idea>(getActivity(),image, MainActivity.uIdeaList);
        view = inflater.inflate(R.layout.fragment_unlisted,container,false);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        */
        view = inflater.inflate(R.layout.fragment_unlisted,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ideaAdapter = new IdeaAdapter(getContext(),MainActivity.uIdeaList);
        recyclerView.setAdapter(ideaAdapter);
        return view;
    }

    public void refreshAdapter(){
        ideaAdapter.notifyDataSetChanged();
    }

}
