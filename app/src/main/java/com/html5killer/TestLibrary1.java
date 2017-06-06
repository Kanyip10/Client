package com.html5killer;


import android.media.Image;
import android.widget.ImageView;

/**
 * Created by KanYip on 2/2/17.
 */

public class TestLibrary1 {

    private int[] mQuestionImages = new int[]{
            R.drawable.concept,
            R.drawable.concept,
            R.drawable.concept,
            R.drawable.image_t1_q4
    };

    private String mQuestions [] = {
            "What is HTML stands for?",
            "Which of the following is not the usage of HTML?",
            "Which of the following is the best description of HEAD section?",
            "What is the corresponding xx on the above html?"
    };


    private String mChoices[][] = {
            {"High Trace Markup Language", "High Technology Markup Language ", "HyperText Markup Language", "HighText Markup Language"},
            {"Format a document", "Provide hyperlinks", "Incorporate multimedia components", "Program the behavior of web pages"},
            {"Holds the information content of the Web page", "Styles the Web page", "Is compulsory for generating a Web page", "Contains specifications that affect the whole Web page"},
            {"<xx> = <title>", "<xx> = <head>", "<xx> = <heading>", "<xx> = <h1>"}

    };



    private String mCorrectAnswers[] = {"HyperText Markup Language", "Program the behavior of web pages", "Contains specifications that affect the whole Web page", "<xx> = <title>"};

    public int getQuestionImage(int a) {
        int image = mQuestionImages[a];
        return image;
    }


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

