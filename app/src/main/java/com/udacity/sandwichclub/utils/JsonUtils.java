package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * This method parses JSON string and returns a Sandwich object
     * which will be used in the Detail Activity.
     *
     * @param json JSON string
     *
     * @return Sandwich object for sandwich details
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {

        /* Keys to access detailJson JSONObject */
        final String NAME_OBJECT = "name";
        final String PLACE_OF_ORIGIN_STRING = "placeOfOrigin";
        final String DESCRIPTION_STRING = "description";
        final String IMAGE_URL_STRING = "image";
        final String INGREDIENT_ARRAY = "ingredients";

        /* Keys to access nameJson JSONObject */
        final String MAIN_NAME_STRING = "mainName";
        final String ALSO_KNOWN_AS_ARRAY = "alsoKnownAs";

        // List of known names
        List<String> alsoKnownAs;

        // List of ingredient
        List<String> ingredients;


        // Parse the json String and construct a Sandwich object
        JSONObject detailJson = new JSONObject(json);

        /* Exit if sandwich has no name */
        if (!detailJson.getJSONObject(NAME_OBJECT).has(MAIN_NAME_STRING)){
            return null;
        }

        // get name object and main name
        JSONObject nameJson = detailJson.getJSONObject(NAME_OBJECT);
        String mainName = nameJson.getString(MAIN_NAME_STRING);

        // get place of origin
        String placeOfOrigin = detailJson.getString(PLACE_OF_ORIGIN_STRING);

        // get description
        String description = detailJson.getString(DESCRIPTION_STRING);

        // get image URL string
        String image = detailJson.getString(IMAGE_URL_STRING);

        // get ingredient list
        JSONArray ingredientArray = detailJson.getJSONArray(INGREDIENT_ARRAY);
        ingredients = new ArrayList<>();
        if (ingredientArray.length()>0){
            for (int i = 0; i < ingredientArray.length(); i++){
                ingredients.add(ingredientArray.getString(i));
            }
        }

        // get list of known names
        JSONArray alsoKnownAsArray = nameJson.getJSONArray(ALSO_KNOWN_AS_ARRAY);
        alsoKnownAs = new ArrayList<>();
        if (alsoKnownAsArray.length()>0){
            for (int i = 0; i < alsoKnownAsArray.length();i++){
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
        }

        // Creating sandwich object using the parsed JSON
        Sandwich parsedSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        // Return the Sandwich object
        return parsedSandwich;
    }
}
