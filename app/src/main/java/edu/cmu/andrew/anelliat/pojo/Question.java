/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO is used to perform the
 * conversion of JSON to Java Object
 * using the Jackson library
 */
package edu.cmu.andrew.anelliat.pojo;

import java.util.List;

public class Question {

    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String answer;
    private List<String> options;
    private int score;

    /**
     * Default constructor for the Question class
     */
    public Question(){
    }

    /**
     * Parameterized constructor for the
     * Question class
     *
     * @param category
     * @param type
     * @param difficulty
     * @param question
     * @param answer
     * @param options
     * @param score
     */
    public Question(String category, String type, String difficulty, String question, String answer, List<String> options, int score) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.score = score;
    }

    /**
     * Getter for the question variable
     * @return String
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Getter for the score variable
     * @return int
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score variable
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Setter for the question variable
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter for the category variable
     * @return String
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for the category variable
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter for the type variable
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the type variable
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for the difficulty variable
     * @return String
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for the difficulty variable
     * @param difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Getter for the answer variable
     * @return String
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Setter for the answer variable
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Getter for the options variable
     * @return List<String>
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Setter for the options variable
     * @param options
     */
    public void setOptions(List<String> options) {
        this.options = options;
    }



}