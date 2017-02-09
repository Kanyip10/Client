package com.html5killer.model;


public class Quiz {

    private String name;
    private String[] question;
    private String[] choice1;
    private String[] choice2;
    private String[] choice3;
    private String[] choice4;
    private String[] answer;

    public void setName(String name) {
        this.name = name;
    }

    public void setQuestion(String[] question) {
        this.question = question;
    }

    public void setChoice1(String[]choice) {
        choice1 = choice;
    }

    public void setChoice2(String[]choice) {
        choice2 = choice;
    }
    public void setChoice3(String[]choice) {
        choice3 = choice;
    }
    public void setChoice4(String[]choice) {
        choice4 = choice;
    }

    public void setAnswer(String[] answer) {this.answer = answer;}

    public String getName() {
        return name;
    }

    public String getQuestion(int a) {
        String aQuestion = question[a];
        return aQuestion;
    }

    public String getChoice1(int a) {
        String aChoice = choice1[0];
        return aChoice;
    }

    public String getChoice2(int a) {
        String aChoice = choice1[1];
        return aChoice;
    }

    public String getChoice3(int a) {
        String aChoice = choice1[2];
        return aChoice;
    }

    public String getChoice4(int a) {
        String aChoice = choice1[3];
        return aChoice;
    }

    public String getChoice5(int a) {
        String aChoice = choice2[0];
        return aChoice;
    }
    public String getChoice6(int a) {
        String aChoice = choice2[1];
        return aChoice;
    }
    public String getChoice7(int a) {
        String aChoice = choice2[2];
        return aChoice;
    }
    public String getChoice8(int a) {
        String aChoice = choice2[3];
        return aChoice;
    }

    public String getChoice9(int a) {
        String aChoice = choice3[0];
        return aChoice;
    }
    public String getChoice10(int a) {
        String aChoice = choice3[1];
        return aChoice;
    }
    public String getChoice11(int a) {
        String aChoice = choice3[2];
        return aChoice;
    }
    public String getChoice12(int a) {
        String aChoice = choice3[3];
        return aChoice;
    }

    public String getChoice13(int a) {
        String aChoice = choice4[0];
        return aChoice;
    }
    public String getChoice14(int a) {
        String aChoice = choice4[1];
        return aChoice;
    }
    public String getChoice15(int a) {
        String aChoice = choice4[2];
        return aChoice;
    }
    public String getChoice16(int a) {
        String aChoice = choice4[3];
        return aChoice;
    }
    public String getAnswer(int a) {
        String anAnswer = answer[a];
        return anAnswer;
    }

}
