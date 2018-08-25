package com.example.android.bakingapp.Activity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.bakingapp.Adapters.RecipeCardsAdapter;
import com.example.android.bakingapp.Data.RecipeData;
import com.example.android.bakingapp.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.example.android.bakingapp.utils.RecipeJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
{
    private RecipeCardsAdapter recipeAdapter;

    @BindView(R.id.tb_topdetails)    Toolbar toolbarTop;

   @BindView(R.id.tv_top_title)    TextView mTitle;
    @BindView(R.id.top_icon)    ImageView icon;
    @BindView(R.id.rv_recipe_cards)    RecyclerView mRecipesRecyclerView;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getIdlingResource();


        Toolbar toolbarTop = (Toolbar) findViewById(R.id.tb_topdetails);
       TextView mTitle = (TextView) toolbarTop.findViewById(R.id.tv_top_title);
       mTitle.setText("  Baking Time");

      ImageView icon  = (ImageView) toolbarTop.findViewById(R.id.top_icon);
       icon.setImageResource(R.drawable.bakingtime);
        setSupportActionBar(toolbarTop);

       if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
       {
           LinearLayoutManager layoutManagerRecipes = new GridLayoutManager(MainActivity.this, 2);
           mRecipesRecyclerView.setLayoutManager(layoutManagerRecipes);
       }
       else {
           LinearLayoutManager layoutManagerRecipes = new LinearLayoutManager(this);
           mRecipesRecyclerView.setLayoutManager(layoutManagerRecipes);
       }
        mRecipesRecyclerView.setHasFixedSize(true);
        String completeUrl= getResources().getString(R.string.recipe_url);

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        new FetchRecipiesTask().execute(completeUrl);


    }


    private  void PopulateRecipes(ArrayList<RecipeData> recipesList)
    {
       recipeAdapter = new RecipeCardsAdapter(recipesList);
       mRecipesRecyclerView.setAdapter(recipeAdapter);
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }

    }



    public class FetchRecipiesTask extends AsyncTask<String, Void, ArrayList<RecipeData>> {
        public FetchRecipiesTask() {

        }

        @Override
        protected ArrayList<RecipeData> doInBackground(String... param) {

            if (param == null) return null;
            String recipeUrl = param[0];

            URL movieURl = NetworkUtils.buildUrl(recipeUrl);

            try {
                String recipiesResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieURl);
                ArrayList<RecipeData> simpleJsonRecipiesData  = RecipeJsonUtils
                        .getRecipiesFromJson(recipiesResponse);
                return simpleJsonRecipiesData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<RecipeData> recipesData) {
            if (recipesData != null) {
                super.onPostExecute(recipesData);
                PopulateRecipes(recipesData);
            }

        }


    }
}
