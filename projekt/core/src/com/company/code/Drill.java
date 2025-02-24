package com.company.code;

public class Drill {
    private final boolean vertical;
    private final Float x;
    private final Float y;
    private Float diameter;

    public Drill(boolean vertical, Float x, Float y, Float diameter) {
        this.vertical = vertical;
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    public boolean isVertical() {
        return vertical;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public Float getDiameter() {
        return diameter;
    }

    public void setDiameter(Float diameter) {
        this.diameter = diameter;
    }
}
