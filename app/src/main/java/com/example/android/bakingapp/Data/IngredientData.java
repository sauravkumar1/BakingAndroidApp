package com.example.android.bakingapp.Data;


import android.util.Log;

import java.util.ArrayList;

public class IngredientData {


    public  String Quantity;
    public  String Measure;
    public  String Ingredient;



    public IngredientData(String quantity, String measure, String ingredient)
    {
        Quantity = quantity;
        Measure  = measure;
        Ingredient = ingredient;
    }

    public static String arrayToString(ArrayList<IngredientData> ingredientData){
        String result = "";
        try {
            for (int i = 0; i < ingredientData.size(); i++) {
                result += ingredientData.get(i).Quantity + "comma"+ ingredientData.get(i).Measure +  "comma"+ ingredientData.get(i).Ingredient ;
                if (i < ingredientData.size() - 1) {
                    result += "ingredientComma";
                }
            }
        }catch (NullPointerException e){
            return "";
        }
        return result;
    }


    public static ArrayList<IngredientData> stringToArray(String string){

        ArrayList<IngredientData> res = new ArrayList<IngredientData>();
              if(string == null) return  res;
        String[] elements = string.split("ingredientComma");

        for (String element : elements) {
            String[] item = element.split("comma");
            try{
                res.add(new IngredientData(item[0], item[1],item[2]));
            }catch (IndexOutOfBoundsException e){
                Log.d("StepsData",e.toString());
            }
        }
        return res;
    }


}
