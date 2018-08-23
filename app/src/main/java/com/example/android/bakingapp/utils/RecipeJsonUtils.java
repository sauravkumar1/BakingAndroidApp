package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.Data.IngredientData;
import com.example.android.bakingapp.Data.RecipeData;
import com.example.android.bakingapp.Data.SingleRecipeData;
import com.example.android.bakingapp.Data.StepsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeJsonUtils {

    public static ArrayList<RecipeData> getRecipiesFromJson(String recipeJson) throws JSONException {
        final String ID = "id";
        final String KEY = "KEY";
        final String NAME = "name";
        final String INGREDIENTS = "ingredients";
        final String STEPS = "steps";


        ArrayList<RecipeData> result = new ArrayList<RecipeData>();

        String recipeJsonStringCleaned = "{\"KEY\"" + ":" + recipeJson + "}";

        JSONObject recipeJsonObj = new JSONObject(recipeJsonStringCleaned);

        JSONArray recipeArray = recipeJsonObj.getJSONArray(KEY);


        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject movieObject = recipeArray.getJSONObject(i);


            JSONArray ingredientsArray = movieObject.getJSONArray(INGREDIENTS);
            JSONArray stepsArray = movieObject.getJSONArray(STEPS);

            String id = movieObject.getString(ID);
            String name = movieObject.getString(NAME);
            ArrayList<IngredientData> ingredientsList = GetIngredients(ingredientsArray);
            ArrayList<StepsData>  stepsList = GetSteps(stepsArray);

            RecipeData mI = new RecipeData(id, name, ingredientsList, stepsList );
            result.add(mI);

        }

        return result;
    }


    public static ArrayList<SingleRecipeData> getRecipiesStringFromJson(String recipeJson) throws JSONException {
        final String KEY = "KEY";
        final String NAME = "name";

        ArrayList<SingleRecipeData> result = new ArrayList<SingleRecipeData>();

        String recipeJsonStringCleaned = "{\"KEY\"" + ":" + recipeJson + "}";

        JSONObject recipeJsonObj = new JSONObject(recipeJsonStringCleaned);

        JSONArray recipeArray = recipeJsonObj.getJSONArray(KEY);


        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject movieObject = recipeArray.getJSONObject(i);
            String jsongString = movieObject.toString();

            String name = movieObject.getString(NAME);
            SingleRecipeData mI = new SingleRecipeData(name,jsongString );
            result.add(mI);

        }

        return result;
    }
    private static ArrayList<IngredientData> GetIngredients(JSONArray ingredientsList) throws JSONException
    {
        final String QUANTITY = "quantity";
        final String MEASURE = "measure";
        final String INGREDIENT = "ingredient";
        ArrayList<IngredientData>  result = new  ArrayList<IngredientData>();

        for (int i = 0; i < ingredientsList.length(); i++) {
            JSONObject ingredientObject = ingredientsList.getJSONObject(i);
            String quantity = ingredientObject.getString(QUANTITY);
            String measure = ingredientObject.getString(MEASURE);
            String ingredient = ingredientObject.getString(INGREDIENT);

            result.add(new IngredientData(quantity, measure,ingredient));

        }

    return  result;

    }

    private static  ArrayList<StepsData> GetSteps(JSONArray stepsList) throws JSONException
    {
        final String ID = "id";
        final String SHORTDESCRIPTION = "shortDescription";
        final String DESCRIPTION = "description";
        final String VIDEOURL = "videoURL";
        ArrayList<StepsData>  result = new  ArrayList<StepsData>();

        for (int i = 0; i < stepsList.length(); i++) {
            JSONObject stepsObject = stepsList.getJSONObject(i);
            String id = stepsObject.getString(ID);
            String shortdesc = stepsObject.getString(SHORTDESCRIPTION);
            String desc = stepsObject.getString(DESCRIPTION);
            String videourl = stepsObject.getString(VIDEOURL);
            if(!videourl.contains("http"))
                videourl = "NOVIDEO";

            result.add(new StepsData(id, shortdesc,desc,videourl));

        }

        return  result;

    }
}


