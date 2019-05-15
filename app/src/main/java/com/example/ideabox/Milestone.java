package com.example.ideabox;

import java.io.Serializable;

public class Milestone implements Serializable {
    private String name;
    private boolean check;

    public Milestone(String name, boolean check) {
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        String str = "[" + name + ",";
        if(check)
            str += "Checked";
        else
            str += "Unchecked";
        str += "]";
        return str;
    }
}
