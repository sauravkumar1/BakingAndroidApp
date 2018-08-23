package com.example.android.bakingapp.Data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class SingleRecipeData {

    public String RecipeName;
    public String RecipeJsonString;



    public SingleRecipeData(String recipename, String recipeJsonString) {
        RecipeName = recipename;
        RecipeJsonString = recipeJsonString;
    }




}