/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO is used to perform the
 * conversion of JSON to Java Object
 * using the Jackson library
 */
package edu.cmu.andrew.anelliat.pojo;

import java.util.ArrayList;
import java.util.List;


public class Questions{

    private List<Question> questions;

    /**
     * Default constructor for Questions class
     */
    public Questions(){
        this.questions = new ArrayList<>();
    }

    /**
     * Parameterized constructor for Questions class
     * @param questions
     */
    public Questions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Getter for the questions variable
     * @return List<Question>
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Setter for the questions variable
     * @param questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
