package com.example.ideabox.KanbanFragment;

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

public class DoingFragment extends Fragment {
    RecyclerView recyclerView;
    IdeaAdapter ideaAdapter;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doing,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ideaAdapter = new IdeaAdapter(getContext(),MainActivity.dIdeaList);
        recyclerView.setAdapter(ideaAdapter);
        return view;
    }



}
