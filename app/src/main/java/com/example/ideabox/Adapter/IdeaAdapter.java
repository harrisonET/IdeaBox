package com.example.ideabox.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideabox.KanbanFragment.DoingFragment;
import com.example.ideabox.KanbanFragment.ForFunFragment;
import com.example.ideabox.KanbanFragment.UnlistedFragment;
import com.example.ideabox.MainActivity;
import com.example.ideabox.Model.Idea;
import com.example.ideabox.R;
import com.example.ideabox.Serializer.ObjectSerializer;

import java.util.ArrayList;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {
    ArrayList<Idea>ideaList;
    Context context;

    public IdeaAdapter( Context context, ArrayList<Idea>list){
        this.context = context;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Idea idea = ideaList.get(position);
        holder.text.setText(idea.toString());

        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_edit: break;
                            case R.id.menu_delete:
                                deleteIdea(idea);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    public void deleteIdea(Idea idea){
        String sql = "DELETE FROM Idea WHERE name=?";
        SQLiteStatement statement = MainActivity.eventsDB.compileStatement(sql);
        statement.bindString(1, idea.getName());
        statement.execute();
        ideaList.remove(idea);

        switch (idea.getCategory()){
            case "Unlisted":
                ((UnlistedFragment) MainActivity.unlistedF).refreshAdapter();
                break;
            case "For Fun":
                ((ForFunFragment) MainActivity.forFunF).refreshAdapter();
                break;
            case "Doing":
                ((DoingFragment) MainActivity.doingF).refreshAdapter();
                break;
                default:Log.i("IDEAADAPTER","ERROR NOT FOUND");
        }
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
        public ImageButton imgMenu;

        public ViewHolder(View item){
            super(item);
            text = item.findViewById(R.id.text);
            imgMenu = item.findViewById(R.id.btnMenu);
        }
    }

}
