package com.html5killer;

/**
 * Created by KanYip on 2/2/17.
 */

public class TestLibrary {

    private String mQuestions [] = {
            "What does HTML stand for?",
            "Who is making the Web standards?",
            "Choose the correct HTML element for the largest heading:",
            "What is the correct HTML element for inserting a line break"

    };


    private String mChoices[][] = {
            {"Hyper Text Markup Language", "Hyperlinks and Text Markup Language", "Home Tool Markup Language", "Higher Tool Markup Language"},
            {"Mozilla", "Google", "Microsoft", "The World Wide Web Consortium"},
            {"<head>", "<heading>", "<h6>", "<h1>"},
            {"<break>", "<br>", " <lb>", "<bak>"}

    };



    private String mCorrectAnswers[] = {"Hyper Text Markup Language", "The World Wide Web Consortium", "<h1>", "<br>"};




    public String getQuestion(int a) {
        if (a + 1 <= mQuestions.length) {
            String question = mQuestions[a];
            return question;
        } else {
            return null;
        }
    }


    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }

    public String getChoice4(int a) {
        String choice3 = mChoices[a][3];
        return choice3;
    }

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }

}

