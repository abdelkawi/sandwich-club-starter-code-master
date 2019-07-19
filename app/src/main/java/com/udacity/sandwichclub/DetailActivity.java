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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mIngredientsTv;
    private ImageView mIngredientsIv;
    private TextView mAlsoKnownAsTv;
    private TextView mDescriptionTv;
    private TextView mPlaceOfOriginTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //init ui views
        mIngredientsIv = findViewById(R.id.image_iv);
        mIngredientsTv = findViewById(R.id.ingredients_tv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mPlaceOfOriginTv = findViewById(R.id.origin_tv);

        // check incoming data is valid
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // get the list selected pos
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        // retrieve the data from resources
        String[] sandwiches = this.getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.wtf("Error", e.toString());
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        // add text info to be displayed
        populateUI(sandwich);

        // load the image into the image view
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsIv);
        // update the screen title name
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mIngredientsTv.setText(sandwich.getIngredients().toString());
        mDescriptionTv.setText(sandwich.getDescription());
        if (sandwich.getAlsoKnownAs().size() > 0) {
            mAlsoKnownAsTv.setText(sandwich.getAlsoKnownAs().toString());
        } else {
            mAlsoKnownAsTv.setText("Not Available");
        }
        mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());
    }
}
