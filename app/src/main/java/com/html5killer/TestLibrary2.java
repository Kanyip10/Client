package com.html5killer;


import android.media.Image;
import android.widget.ImageView;

/**
 * Created by KanYip on 2/2/17.
 */

public class TestLibrary2 {

    private int[] mQuestionImages = new int[]{
            R.drawable.image_t2_q1,
            R.drawable.image_t2_q2,
            R.drawable.image_t2_q3,
            R.drawable.image_t2_q4
    };

    private String mQuestions [] = {
            "Which heading tag is used for \"Hello 6\"?",
            "What is the corresponding xx, yy and zz for creating the result on the right hand side?",
            "Which of the following is the possible way to create a hyper link on the above web page?",
            "Which of the following should be used to include an image on the above web page ?"
    };


    private String mChoices[][] = {
            {"<h1>", "<h2>", "<h5>", "<h6>"},
            {"<xx> = <b>, <yy> = <i>, <zz> = <middle>", "<xx> = <i>, <yy> = <b>, <zz> = <middle>", "<xx> = <bold>, <yy> = <italic>, <zz> = <center>", "<xx> = <b>, <yy> = <i>, <zz> = <center>"},
            {"<a link=\"http://www.google.com\">Click</a>", "<a href=\"http://www.google.com\">Click</a>", "<link href=\"http://www.google.com\">Click</link>", "<hyper link=\"http://www.google.com\">Click</hyper link>"},
            {"<img src=\"image.jpg\" alt=\"Test image\" height=\"250\" width=\"300\">", "<graph src=\"image.jpg\" alt=\"Test image\" height=\"250\" width=\"300\">", "<canvas src=\"image.jpg\" alt=\"Test image\" height=\"250\" width=\"300\">", "<image src=\"image.jpg\" alt=\"Test image\" height=\"250\" width=\"300\">"}
    };



    private String mCorrectAnswers[] = {"<h1>", "<xx> = <b>, <yy> = <i>, <zz> = <center>", "<a href=\"http://www.google.com\">Click</a>", "<img src=\"image.jpg\" alt=\"Test image\" height=\"250\" width=\"300\">"};

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

