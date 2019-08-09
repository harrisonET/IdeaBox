package com.example.ideabox.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Idea implements Serializable{
    private String name;
    private String description;
    private List<Milestone>milestoneList;
    private String category;
    private static final long serialVersionUID =  7765409787296362742L;

    public Idea(String name, String description, List<Milestone> milestoneList) {
        this.name = name;
        this.description = description;
        this.category = "Unlisted";
        this.milestoneList = milestoneList;
    }

    public Idea(String name, String description, String category, List<Milestone> milestoneList) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.milestoneList = milestoneList;
    }

    public Idea(String name, String description) {
        this.name = name;
        this.description = description;
        this.milestoneList = new ArrayList<Milestone>();
     }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Milestone> getMilestoneList() {
        return milestoneList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMilestoneList(List<Milestone> milestoneList) {
        this.milestoneList = milestoneList;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        String cat;
        if (category == null)
            cat = "Unlisted";
        else
            cat = category;

        String str = "Name= " + name + '\n' +
                "Description=" + description + '\n' +
                "Category= " + cat + '\n';

        for(Milestone m:milestoneList) {
            str += m;
            str += '\n';
        }
        return str;
    }

}
