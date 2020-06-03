/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class performs background actions to
 * retrieve questions from the Web server
 * using a REST request.
 * It also converts the response JSON into
 * the Questions POJO which is then passed
 * onto the UI thread.
 */
package edu.cmu.andrew.anelliat.utility;

import android.os.AsyncTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.andrew.anelliat.activity.QuizActivity;
import edu.cmu.andrew.anelliat.pojo.Question;
import edu.cmu.andrew.anelliat.pojo.Questions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class QuestionService {

    QuizActivity quizActivity = null;

    /**
     * Method to getQuestions from the web server.
     * This is done by an asyncTask execution.
     *
     * @param quizActivity
     */
    public void getQuestions(QuizActivity quizActivity) {
        this.quizActivity = quizActivity;
        //Retrieve the amount, category and difficulty parameters from the quizActivity intent
        String amount = quizActivity.getIntent().getStringExtra("amount");
        String category = quizActivity.getIntent().getStringExtra("category");
        String difficulty = quizActivity.getIntent().getStringExtra("difficulty");
        //Create the URL
        String url = "https://lit-reef-60639.herokuapp.com/getQuestions?amount=" + amount + "&category=" + category + "&difficulty=" + difficulty;
        //Call the AsyncQuestions class
        new AsyncQuestions().execute(url);
    }

    /**
     * Helper Class to execute the process heavy method of retrieving questions
     * in the background thread
     */
    private class AsyncQuestions extends AsyncTask<String, Void, List<Question>> {

        /**
         * Method to be executed post execution. This method
         * calls the setQuestions method on the UI thread
         *
         * @param questions - The list of questions to be
         *                  passed to the UI
         */
        protected void onPostExecute(List<Question> questions) {
            quizActivity.setQuestions(questions);
        }

        /**
         * Method to perform HTTP GET call using the background thread.
         * This method connects the app with the web server and requests
         * for a list of questions based on user input (see URL)
         *
         * @param strings - strings[0] - contains the URL to be used
         * @return List<Question> - the list of questions
         */
        @Override
        protected List<Question> doInBackground(String... strings) {
            try {
                //Retrieve the URL
                URL url = new URL(strings[0]);
                //Convert to URI to help create a HTTP encoded URL (for special characters like @, ! etc)
                URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                //Create an HTTP connection using the uri.
                HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
                conn.connect();
                String output = "";
                //return null on unsuccessful connection
                if (conn.getResponseCode() != 200) {
                    return null;
                }
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String response = "";
                while ((output = br.readLine()) != null) {
                    response += output;

                }
                conn.disconnect();
                //If response is null return null
                if (response == null) {
                    return null;
                }
                //Convert the JSON to Questions POJO and return List<Question>
                return jsonToPojo(response).getQuestions();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                //return null on any error
                return null;
            }
        }

        /**
         * Method to convert JSON String to Questions POJO
         *
         * @param requestString
         * @return Questions
         */
        public Questions jsonToPojo(String requestString) {
            //Construct an objectMapper object
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(requestString, Questions.class);
            } catch (IOException e) {
                e.printStackTrace();
                //if an error occurs return null
                return null;
            }
        }

    }
}
