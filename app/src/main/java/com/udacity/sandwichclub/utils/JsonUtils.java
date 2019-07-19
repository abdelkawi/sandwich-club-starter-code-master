package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich(); // new object to be parsed into
        JSONObject jsonObject = new JSONObject(json); // json object that hold the data from res
        sandwich.setImage(jsonObject.getString("image")); // get the food image link
        sandwich.setMainName(jsonObject.getJSONObject("name").getString("mainName")); // get the main name form name object
        List<String> alsoKnownAsList = new ArrayList<>(); // to collect multiple strings
        List<String> ingredientsList = new ArrayList<>();
        for(int i=0;i<jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs").length();i++)
        alsoKnownAsList.add(jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs").getString(i)); //add each string to list
        for(int i=0;i<jsonObject.getJSONArray("ingredients").length();i++)
            ingredientsList.add(jsonObject.getJSONArray("ingredients").getString(i));//add each string to list
        sandwich.setAlsoKnownAs(alsoKnownAsList); // add data to the object to be returned
        sandwich.setDescription(jsonObject.getString("description"));
        sandwich.setIngredients(ingredientsList);
        sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
        return sandwich; // return food object filled with data
    }
}
