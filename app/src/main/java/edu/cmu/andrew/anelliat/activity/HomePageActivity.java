/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class is the first Activity class
 * for the Android Application - QuizMe
 *
 * The class shows the Home page of the
 * quiz to the user.
 * The user then has the option of going forward.
 */
package edu.cmu.andrew.anelliat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import edu.cmu.andrew.anelliat.R;

public class HomePageActivity extends AppCompatActivity {

    private Button button;


    /**
     * Method to initialize the activity
     * on creation.
     * The Buttons are also initialized
     * to their existing references in this method
     * Button actions and subsequent methods are
     * defined/called.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add the existing UI refernece to the button
        //and add a listner
        button = findViewById(R.id.begin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    openQuizOptionsPage();
            }
        });
    }

    /**
     * Method to direct user to the OptionsActivity
     */
    private void openQuizOptionsPage() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

}
