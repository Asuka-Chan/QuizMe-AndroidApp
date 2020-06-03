/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class is the Options Activity code
 * for the Android Application - QuizMe
 * The class shows the options of the
 * quiz that the user can select.
 *
 * The user then has the option of
 * going back to the home page.
 */
package edu.cmu.andrew.anelliat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import edu.cmu.andrew.anelliat.utility.CategoryService;
import edu.cmu.andrew.anelliat.utility.MinMaxFilter;
import edu.cmu.andrew.anelliat.R;

import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FloatingActionButton back;
    Button startQuiz;
    Spinner category;
    Spinner difficulty;
    EditText noOfQuestions;
    CategoryService categories = null;

    String difficultyText = null;
    String categoryText = null;


    /**
     * Method to initialize the activity
     * on creation.
     * The Views and Buttons are initialized
     * to their existing references in this method
     * Button actions and subsequent methods are
     * defined/called.
     *
     * The list of categories to display are
     * retrieved from the CategoryService classs.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_options);
        //Call the getCategories method
        categories = new CategoryService();
        categories.getCategories(this);

        //Initialize the UI elements to their references
        startQuiz = findViewById(R.id.startQuiz);
        back = findViewById(R.id.back);
        difficulty = findViewById(R.id.difficulty);
        noOfQuestions = findViewById(R.id.amount) ;

        //Add an input filter that prevents users from exceeding 50 and going below 1
        noOfQuestions.setFilters( new InputFilter[]{ new MinMaxFilter( "1" , "50" )}) ;
        //Add an on select listener to the difficulty dropdown
        difficulty.setOnItemSelectedListener(this);

        //Add a listener to the back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHomePage();
            }
        });
        //Add a listener to the startQuiz button
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuizActivity();
            }
        });
    }

    /**
     * Method to start the quiz activity.
     * This forwards the user to the QuizActivity.
     *
     * Clicking the startQuiz button calls this method.
     */
    private void startQuizActivity() {
        //Retrieve the user input value
        String amount = noOfQuestions.getText().toString();

        Intent intent = new Intent(this, QuizActivity.class);
        //Set the difficulty, amount and category params to be passed onto the
        //quizActivity
        intent.putExtra("amount", amount);
        intent.putExtra("difficulty", difficultyText);
        intent.putExtra("category", categoryText);

        startActivity(intent);
    }

    /**
     * Method to go back to the home page
     * Clicking the back button calls this method.
     */
    private void showHomePage() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    /**
     * Method to set the category dropdown list.
     * This method waits for the background
     * getCategory to finish. Once done,
     * control is passed to this method.
     * @param categories
     */
    public void setCategoryOptions(List<String> categories) {
        //Get the reference of category spinner
        category = (Spinner) findViewById(R.id.category);
        //If the list is null, set it to an empty list, else set the categories
        List<String> categ = categories == null ? new ArrayList<String>(): categories;
        //Set the values onto the spinner
        ArrayAdapter<String> adp1=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,categories);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adp1);
        //Set a listener on the dropdown
        category.setOnItemSelectedListener(this);
    }

    /**
     * Method to determine the item selected from the dropdown
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //If the selected parent is difficulty, then
        //set the difficultyText to the value selected
        if(parent.equals(difficulty)){
            difficultyText = parent.getItemAtPosition(position).toString();
        }
        //else set the categoryText to the value selected
        else {
            categoryText = parent.getItemAtPosition(position).toString();
        }
    }

    /**
     * Method required for class to be implemented
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }

}
