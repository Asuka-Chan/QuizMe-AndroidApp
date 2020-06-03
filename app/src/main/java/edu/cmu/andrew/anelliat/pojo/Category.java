/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO is used to perform the
 * conversion of JSON to Java Object
 * using the Jackson library
 */
package edu.cmu.andrew.anelliat.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Category {

    //This annotation is used to identify the name of the
    //property we want to map in the JSON response
    @JsonProperty("categories")
    private List<String> categories;

    /**
     * Default constructor of Category Class
     */
    public Category() {
    }

    /**
     * Getter for the categories pojo
     * @return List<String>
     */
    public List<String> getCategories() {
        return categories;
    }

    /**
     * Setter for the list of categories
     * @param categories
     */
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
