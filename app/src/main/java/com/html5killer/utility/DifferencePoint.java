package com.html5killer.utility;

public class DifferencePoint {
    private int id;
    private boolean isDetected;
    private int radius;
    private int f52x;
    private int f53y;

    public DifferencePoint() {
        this.isDetected = false;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getPosX() {
        return this.f52x;
    }

    public void setPosX(String x) {
        this.f52x = Integer.parseInt(x);
    }

    public int getPosY() {
        return this.f53y;
    }

    public void setPosY(String y) {
        this.f53y = Integer.parseInt(y);
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(String radius) {
        this.radius = Integer.parseInt(radius);
    }

    public boolean isDetected() {
        return this.isDetected;
    }

    public void setDetected(boolean detect) {
        this.isDetected = detect;
    }
}
