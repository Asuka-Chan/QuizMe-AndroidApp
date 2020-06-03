/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class is the final Activity class
 * for the Android Application - QuizMe
 *
 * The class shows the final results of the
 * quiz to the user with an appropriate image
 * based on the score.
 * The user then has the option of trying again or
 * going back to the home page.
*/
 package edu.cmu.andrew.anelliat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import edu.cmu.andrew.anelliat.R;

public class FinishActivity extends AppCompatActivity {

    int total = 0;
    int userScore = 0;

    TextView message;
    Button tryAgain;
    Button home;
    ImageView view;

    /**
     * Method to initialize the activity
     * on creation.
     * Here were retrieve the user scores
     * and the total.
     * The Views and Buttons are also initialized
     * to their existing references in this method
     * Button actions and subsequent methods are
     * defined/called.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        //Retrieve the scores from the intent
        userScore = this.getIntent().getIntExtra("userScore", 0);
        total = this.getIntent().getIntExtra("total", 0);

        //Add references to the UI elements
        view = findViewById(R.id.viewImage);
        message = findViewById(R.id.message);
        home = findViewById(R.id.home);
        tryAgain = findViewById(R.id.tryAgain);

        determineImage();

        //Add listener to home button
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomePage();
            }
        });

        //Add listener to try again button
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsPage();
            }
        });


    }

    /**
     * Method to determine which image
     * must be displayed based on userScore.
     * The text in TextView 'message' is also set
     */
    private void determineImage() {
        if (total / 2 < userScore) {
            message.setText("Yay! You scored: " + userScore + "/" + total);
            view.setImageResource(R.drawable.yay);
        } else {
            message.setText("Ooops! You scored: " + userScore + "/" + total);
            view.setImageResource(R.drawable.oh_no);
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
     * Method to redirect user to the home page
     */
    private void showHomePage() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

}
