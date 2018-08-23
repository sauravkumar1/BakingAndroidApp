package com.example.android.bakingapp.Data;

import java.util.ArrayList;

public class RecipeData {

    public  String Id;
    public  String Name;
    public ArrayList<IngredientData> Ingredients;
    public  ArrayList<StepsData>  Steps;



    public RecipeData(String id, String name,ArrayList<IngredientData>  ingredients, ArrayList<StepsData>  steps)
    {
        Id = id;
        Name = name;
        Ingredients= ingredients;
        Steps = steps;
    }
}
