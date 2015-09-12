package com.playground;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by penelope on 9/8/15.
 */
public class GameHelper {
    public String getUserInput(String prompt) {
    System.out.print(prompt + " ");
    try {
        BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
        inputLine = is.readLine();
        if (inputLine.length() == 0) return null;)
        catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
    }
}
