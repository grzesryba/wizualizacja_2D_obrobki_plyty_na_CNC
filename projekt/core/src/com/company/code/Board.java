package com.company.code;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private final LinkedList<Drill> drills = new LinkedList<>();
    private final List<Saw> lines = new ArrayList<>();
    private final List<Frez> frezLines = new ArrayList<>();
    private int height = 0;
    private int width = 0;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public LinkedList<Drill> getDrills() {
        return drills;
    }

    public List<Saw> getLines() {
        return lines;
    }

    public List<Frez> getFrezLines() {
        return frezLines;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
