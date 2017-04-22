package com.html5killer.utility;

import java.util.ArrayList;
import java.util.Iterator;

public class DifferencesInfo {
    private boolean completed;
    private String datePlayed;
    private int duration;
    private int errors;
    private String imageLoc1;
    private String imageLoc2;
    private ArrayList<DifferencePoint> points;
    private boolean unlocked;

    public DifferencesInfo() {
        this.datePlayed = "";
        this.duration = 0;
        this.errors = 0;
        this.unlocked = false;
        this.completed = false;
        this.points = new ArrayList();
    }

    public String getDatePlayed() {
        return this.datePlayed;
    }

    public void setDatePlayed(String date) {
        this.datePlayed = date;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getErrors() {
        return this.errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getImageLocation1() {
        return this.imageLoc1;
    }

    public void setImageLocation1(String image) {
        this.imageLoc1 = image;
    }

    public String getImageLocation2() {
        return this.imageLoc2;
    }

    public void setImageLocation2(String image) {
        this.imageLoc2 = image;
    }

    public void addPoint(DifferencePoint point) {
        point.setID(this.points.size());
        this.points.add(point);
    }

    public DifferencePoint getPoint(int index) {
        return (DifferencePoint) this.points.get(index);
    }

    public ArrayList<DifferencePoint> getPoints() {
        return this.points;
    }

    public int getPointsCount() {
        return this.points.size();
    }

    public DifferencePoint isPointInRadius(int x, int y, boolean mark) {
        Iterator it = this.points.iterator();
        while (it.hasNext()) {
            DifferencePoint point = (DifferencePoint) it.next();
            if (!point.isDetected() && ((double) (((int) Math.pow((double) Math.abs(x - point.getPosX()), 2.0d)) + ((int) Math.pow((double) Math.abs(y - point.getPosY()), 2.0d)))) < Math.pow((double) point.getRadius(), 2.0d)) {
                point.setDetected(true);
                return point;
            }
        }
        return null;
    }

    public DifferencePoint getNonDetectedPoint(boolean mark) {
        Iterator it = this.points.iterator();
        while (it.hasNext()) {
            DifferencePoint point = (DifferencePoint) it.next();
            if (!point.isDetected()) {
                point.setDetected(mark);
                return point;
            }
        }
        return null;
    }
}
