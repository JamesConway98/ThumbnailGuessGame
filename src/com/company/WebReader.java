package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class WebReader {

    public String returnLine(String containing, String webpage)throws Exception{

        URL url = new URL(webpage);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains(containing)) {
                //System.out.println(inputLine);
                break;
            }
        }
        in.close();
        return inputLine;
    }

    public ArrayList<String> returnLines(String containing, String webpage)throws Exception{

        ArrayList<String> inputLines = new ArrayList<>();

        URL url = new URL(webpage);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {

            //System.out.println(inputLine);  Prints out full html
            if (inputLine.contains(containing)) {
                //System.out.println(inputLine);
                inputLines.add(inputLine);
            }
        }
        in.close();
        return inputLines;
    }
}
