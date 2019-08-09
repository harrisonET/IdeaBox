package com.example.ideabox.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideabox.Model.Idea;
import com.example.ideabox.R;
import java.util.ArrayList;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {
    ArrayList<Idea>ideaList;

    public IdeaAdapter( ArrayList<Idea>list){
        ideaList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Idea idea = ideaList.get(position);
        holder.text.setText(idea.toString());
    }

    @Override
    public int getItemCount() {
        return ideaList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text;

        public ViewHolder(View item){
            super(item);
            text = item.findViewById(R.id.text);
        }
    }

}
