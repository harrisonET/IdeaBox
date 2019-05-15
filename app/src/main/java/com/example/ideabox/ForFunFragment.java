package com.example.ideabox;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ForFunFragment extends Fragment {
    ListView listView;
    View view;
    ArrayAdapter<Idea> arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        arrayAdapter = new ArrayAdapter<Idea>(getActivity(),android.R.layout.simple_list_item_1, MainActivity.fIdeaList);
        view = inflater.inflate(R.layout.fragment_forfun,container,false);
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        return view;
    }
}
