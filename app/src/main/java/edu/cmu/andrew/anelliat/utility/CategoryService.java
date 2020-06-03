/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class performs background actions to
 * retrieve categories from the Web server
 * using a REST request.
 * It also converts the response JSON into
 * the Category POJO which is then passed
 * onto the UI thread.
 */
package edu.cmu.andrew.anelliat.utility;

import android.os.AsyncTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.andrew.anelliat.activity.OptionsActivity;
import edu.cmu.andrew.anelliat.pojo.Category;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CategoryService {

    OptionsActivity optionsActivity = null;

    /**
     * Method to getCategories from the web server.
     * This is done by an asyncTask execution.
     *
     * @param optionsActivity
     */
    public void getCategories(OptionsActivity optionsActivity) {
        this.optionsActivity = optionsActivity;
        //Call the AsyncQuestions class
        new AsyncCategoryOptions().execute("https://lit-reef-60639.herokuapp.com/getCategories");
    }

    /**
     * Helper Class to execute the process heavy method of retrieving categories
     * in the background thread
     */
    private class AsyncCategoryOptions extends AsyncTask<String, Void, List<String>> {

        /**
         * Method to be executed post execution. This method
         * calls the setCategoryOptions method on the UI thread
         *
         * @param categories - The list of string of the
         *                   categories to be passed to the UI
         */
        protected void onPostExecute(List<String> categories) {
            optionsActivity.setCategoryOptions(categories);
        }

        /**
         * Method to perform HTTP GET call using the background thread.
         * This method connects the app with the web server and requests
         * for a list of categories based on user input (see URL)
         *
         * @param strings - strings[0] - contains the URL to be used
         * @return List<String> - the list of questions
         */
        @Override
        protected List<String> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                //Create an HTTP connection using the uri.
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                //return null on unsuccessful connection
                if (conn.getResponseCode() != 200) {
                    // not using msg
                    return null;
                }
                String output = "";
                // things went well so let's read the response
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));
                String response = "";
                while ((output = br.readLine()) != null) {
                    response += output;

                }
                conn.disconnect();
                if (response == null) {
                    return null;
                }
                //Convert the JSON to Category POJO and return categories - List<String>
                return jsonToPojo(response).getCategories();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Method to convert JSON String to Category POJO
         *
         * @param requestString
         * @return Category
         */
        public Category jsonToPojo(String requestString) {
            //Construct an objectMapper object
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(requestString, Category.class);
            } catch (IOException e) {
                e.printStackTrace();
                //if an error occurs return null
                return null;
            }
        }

    }
}
