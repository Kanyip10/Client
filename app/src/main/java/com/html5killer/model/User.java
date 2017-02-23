package com.html5killer.model;


public class User {

    private String name;
    private String email;
    private int level;
    private int experience;
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
