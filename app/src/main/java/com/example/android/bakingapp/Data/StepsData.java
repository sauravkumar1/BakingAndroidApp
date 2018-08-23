package com.example.android.bakingapp.Data;

import android.util.Log;

import java.util.ArrayList;

public class StepsData {


    public  String Id;
    public  String ShortDescription;
    public  String Description;
    public  String VideoURL;


    public StepsData(String id, String shortDescription, String description, String videoURL) {

        Id = id;
        ShortDescription = shortDescription;
        Description = description;
        VideoURL = videoURL;

    }

    public  static String arrayToString(ArrayList<StepsData> steps){
        String result = "";
        try {
            for (int i = 0; i < steps.size(); i++) {
                result += steps.get(i).Id + "comma"+ steps.get(i).ShortDescription +  "comma"+ steps.get(i).Description+ "comma"+ steps.get(i).VideoURL ;
                if (i < steps.size() - 1) {
                    result += "stepsComma";
                }
            }
        }catch (NullPointerException e){
            return "";
        }
        return result;
    }


    public  static ArrayList<StepsData> stringToArray(String string){
        String[] elements = string.split("stepsComma");
        ArrayList<StepsData> res = new ArrayList<>();

        for (String element : elements) {
            String[] item = element.split("comma");
            try{
                res.add(new StepsData(item[0], item[1],item[2],item[3]));
            }catch (IndexOutOfBoundsException e){
                Log.d("StepsData",e.toString());
            }
        }
        return res;
    }

}
