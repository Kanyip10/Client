package com.html5killer.model;


public class User {

    private String name;
    private String email;
    private int level;
    private int experience;
    private int experience2;
    private int experience3;
    private int experience4;
    private int experience5;
    private String password;
    private String created_at;
    private String newPassword;
    private String token;
    private int newExp;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLevel() { return level;}

    public void setLevel(int level) { this.level = level;}

    public void setNewExp(int score){this.newExp = score;}

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience2() {
        return experience2;
    }

    public void setExperience2(int experience2) {
        this.experience2 = experience2;
    }

    public int getExperience3() {
        return experience3;
    }

    public void setExperience3(int experience3) {
        this.experience3 = experience3;
    }

    public int getExperience4() {
        return experience4;
    }

    public void setExperience4(int experience4) {
        this.experience4 = experience4;
    }

    public int getExperience5() {
        return experience5;
    }

    public void setExperience6(int experience5) {
        this.experience5 = experience5;
    }

    public String getLevelName(int level){
        String[] levelTitle= {"Beginner","Amateur", "Professional","Expert"};
        String levelName;
        switch(level){
            case 1 : levelName = levelTitle[0];
                break;
            case 2 : levelName = levelTitle[1];
                break;
            case 3 : levelName = levelTitle[2];
                break;
            case 4 : levelName = levelTitle[3];
                break;
            default : levelName = levelTitle[3];
        }
        return levelName;
    }

    public String toString(){
        return "Level: " + getLevelName(getLevel()) + "   Experience: " + getExperience() +"/100 ";

    }

    public char checkIdentity(String id){
        char firstWord = id.charAt(0);
        return firstWord;
    }
}
