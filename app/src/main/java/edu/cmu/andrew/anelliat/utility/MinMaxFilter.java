/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This class acts as a filter for the user input
 * UI element on the Options activity page.
 * This class has been sourced from :
 * https://capdroidandroid.wordpress.com/2016/04/07/set-minimum-maximum-value-in-edittext-android/
 */
package edu.cmu.andrew.anelliat.utility;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxFilter implements InputFilter {
    private int mIntMin , mIntMax ;

    /**
     * Parameterized constructor for MinMaxFilter class
     * with integers are parameters
     * @param minValue
     * @param maxValue
     */
    public MinMaxFilter ( int minValue , int maxValue) {
        this . mIntMin = minValue ;
        this . mIntMax = maxValue ;
    }

    /**
     * Parameterized constructor for MinMaxFilter class
     * with Strings as parameters
     * @param minValue
     * @param maxValue
     */
    public MinMaxFilter (String minValue , String maxValue) {
        this . mIntMin = Integer. parseInt (minValue) ;
        this . mIntMax = Integer. parseInt (maxValue) ;
    }

    /**
     * Method to filter out the input by determining
     * if the input falls within the selected range
     * @param source
     * @param start
     * @param end
     * @param dest
     * @param dstart
     * @param dend
     * @return CharSequence
     */
    @Override
    public CharSequence filter (CharSequence source , int start , int end , Spanned dest ,int dstart , int dend) {
        try {
            int input = Integer. parseInt (dest.toString() + source.toString()) ;
            if (isInRange( mIntMin , mIntMax , input))
                return null;
        } catch (NumberFormatException e) {
            e.printStackTrace() ;
        }
        return "" ;
    }

    /**
     * Method to determine if given values are within the range
     * of a and b
     * @param a - minimum value
     * @param b - maximum value
     * @param c - user input
     * @return  true - if within the range
     *          false - if outside the range
     */
    private boolean isInRange ( int a , int b , int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a ;
    }
}