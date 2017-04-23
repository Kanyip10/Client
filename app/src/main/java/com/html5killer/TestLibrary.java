package com.html5killer;


import android.media.Image;
import android.widget.ImageView;

/**
 * Created by KanYip on 2/2/17.
 */

public class TestLibrary {

    private int[] mQuestionImages = new int[]{
            R.drawable.image_q1,
            R.drawable.image_q2,
            R.drawable.image_q3,
            R.drawable.image_q4
    };

    private String mQuestions [] = {
            "What is the Headings tag used for \'Hello 1\'?",
            "What is the tag used for creating the above html?",
            "What is the corresponding xx and yy?",
            "What is the corresponding xx and yy?"

    };


    private String mChoices[][] = {
            {"<H1>", "<H2>", "<H6>", "<H7>"},
            {"<img>", "<image>", "<photo>", "<canvas>"},
            {"<xx> = <td>, <yy> = <th>", "<xx> = <th>, <yy> = <td>", "<xx> = <tr>, <yy> = <th>", "<xx> = <th>, <yy> = <tr>"},
            {"<xx> = <ul>, <yy> = <ol>", "<xx> = <ol>, <yy> = <ul>", "<xx> = <li>, <yy> = <ol>", "<xx> = <ol>, <yy> = <li>"}

    };



    private String mCorrectAnswers[] = {"<H6>", "<img>", "<xx> = <th>, <yy> = <td>", "<xx> = <ul>, <yy> = <ol>"};

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

