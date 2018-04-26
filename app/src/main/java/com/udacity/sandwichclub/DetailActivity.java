package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            Log.e(TAG, "JsonUtils Parsing error: " + e.getMessage());
            closeOnError(e.getMessage());
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void closeOnError(String e) {
        finish();
        Toast.makeText(this, "JsonUtils Parsing error: " + e, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView originTv = findViewById(R.id.origin_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientTv = findViewById(R.id.ingredients_tv);

        List alsoKnownLst = sandwich.getAlsoKnownAs();
        List ingredientLst = sandwich.getIngredients();
        String alsoKnownTxt = "";
        String ingredientTxt = "";

        if (alsoKnownLst.size()>0){
            alsoKnownTxt = alsoKnownLst.get(0).toString();
            for (int i = 1; i < alsoKnownLst.size(); i++){
                alsoKnownTxt = alsoKnownTxt + ", " + alsoKnownLst.get(i).toString();
            }
        }

        if (ingredientLst.size()>0){
            ingredientTxt = ingredientLst.get(0).toString();
            for (int i = 1; i < ingredientLst.size(); i++){
                ingredientTxt = ingredientTxt + ", " + ingredientLst.get(i).toString();
            }
        }

        originTv.setText(sandwich.getPlaceOfOrigin());
        alsoKnownTv.setText(alsoKnownTxt);
        descriptionTv.setText(sandwich.getDescription());
        ingredientTv.setText(ingredientTxt);

    }
}
