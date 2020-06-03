/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class is the quiz Activity class
 * for the Android Application - QuizMe
 *
 * The class displays questions to the user,
 * informs the user of the question category,
 * difficulty level and the current score.
 * 4 or 2 options (radio buttons) are shown based on the
 * type of question.
 * The user then has the option of going back.
 */
package edu.cmu.andrew.anelliat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cmu.andrew.anelliat.utility.QuestionService;
import edu.cmu.andrew.anelliat.R;
import edu.cmu.andrew.anelliat.pojo.Question;
import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    FloatingActionButton back;
    TextView question;
    TextView category;
    TextView score;
    TextView difficulty;
    TextView error;
    RadioGroup options;
    RadioButton option1;
    RadioButton option2;
    RadioButton option4;
    RadioButton option3;
    Question quest;
    Button next;

    //Current question index
    int index = 0;

    int userScore = 0;
    int totalScore = 0;

    QuestionService questionService = new QuestionService();
    List<Question> questions = new ArrayList<>();

    /**
     * Method to initialize the activity
     * on creation.
     * The Views, Radio buttons and Buttons are initialized
     * to their existing references in this method
     * Button actions and subsequent methods are
     * defined/called.
     *
     * The set of questions to ask the user is
     * retrieved using the QuestionService class.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //Request for questions
        questionService.getQuestions(this);
        //Initialize the UI elements to their existing references
        error = findViewById(R.id.error);
        error.setVisibility(View.INVISIBLE);
        question = findViewById(R.id.question);
        category = findViewById(R.id.category);
        score = findViewById(R.id.score);
        difficulty = findViewById(R.id.difficulty);
        options = findViewById(R.id.options);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);
        next.setEnabled(false);

        //Add listener to back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsPage();
            }
        });

        //Add listener to next question button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAnswer();
            }
        });

        //Add listener to radio button group to enable submit of the question
        // This is added since we do not want users to submit without selecting
        // an answer.
        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                        next.setEnabled(true);
            }
        });

    }

    /**
     * Method to submit the answer for a given question
     * This method adds the scores based on the user response
     * and forwards the control to the FinishActivity class
     * when the user submits the last answer.
     *
     * This method is called when a user selects the
     * Submit & Next button on the UI (next button)
     */
    private void submitAnswer() {
        //Get the selected option and answer
        int selectedId = options.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);

        //Determine if the selected answer is the correct one
        if (quest.getAnswer().equals(radioButton.getText().toString())) {
            //If yes, the add to the user score
            userScore += quest.getScore();
        }
        //clear the selected options on the UI
        options.clearCheck();
        //if the current question index equals the total number of questions
        //then forward user to the FinishActivity
        //Send the userScore and total to the new activity
        if (index == questions.size()) {
            Intent intent = new Intent(this, FinishActivity.class);
            intent.putExtra("userScore", userScore);
            intent.putExtra("total", totalScore);
            startActivity(intent);
        } else {
            // if the current question is the last question,
            // set next button to Finish Quiz
            if (index == questions.size() - 1) {
                next.setText("Finish Quiz");
            }
            //call the ask question method
            askQuestion();
        }
    }

    /**
     * Method to ask a question to the user.
     * This method updates the appropriate
     * UI elements.
     */
    private void askQuestion() {
        //Disable the submit & next button
        //as this is a new question and the user
        //is yet to select an answer
        next.setEnabled(false);
        //Get the next question on the list
        quest = questions.get(index);

        //Set the question, category and difficulty texts
        question.setText(++index + "." + quest.getQuestion());
        category.setText("Category: " + quest.getCategory());
        difficulty.setText("Difficulty: " + quest.getDifficulty().toUpperCase() + " - " + quest.getScore() + "pts");

        //Increase the total score and display the score to the user
        totalScore += quest.getScore();
        score.setText("Score: " + userScore + "/" + totalScore);

        //Get the list of options and set the first two radio buttons
        List<String> optionValues = quest.getOptions();
        option1.setText(optionValues.get(0));
        option2.setText(optionValues.get(1));
        //If the question type is multiple, then set the last two options
        // and make it visible
        if (quest.getType().equalsIgnoreCase("multiple")) {
            option3.setText(optionValues.get(2));
            option4.setText(optionValues.get(3));
            option3.setVisibility(View.VISIBLE);
            option4.setVisibility(View.VISIBLE);
        } else {
            //else make the last two options invisible
            option3.setVisibility(View.INVISIBLE);
            option4.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Method to redirect user to the options page
     */
    private void showOptionsPage() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    /**
     * Method that waits for the background
     * getQuestions to finish. Once done,
     * control is passed to this method.
     *
     * If the @param value is null, an error
     * message will be displayed
     *
     * @param questions - the list of questions to ask the user
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        if (questions != null) {
            askQuestion();
        }else{
            errorMsg();
        }
    }

    /**
     * Method to set the error message
     * on the UI
     */
    public void errorMsg() {
        TextView error = findViewById(R.id.error);
        error.setText("Ooops! Looks like we cannot retrieve questions for you!\nPlease try again!\n\nKindly go back to the Options page");
        error.setVisibility(View.VISIBLE);
    }
}
