package com.example.android.bakingapp.Fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.example.android.bakingapp.Adapters.IngredientsAdapter;
import com.example.android.bakingapp.Data.IngredientData;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Widget.BakingAppUpdateService ;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsFragment extends Fragment {

    @BindView(R.id.rv_recipe_ingredients)    RecyclerView mRecyclerView;
    String mIngredientsDataString;
    LinearLayoutManager mLinearLayoutManager;
    private  boolean mIsTwoPane;

    // Mandatory empty constructor
    public RecipeIngredientsFragment( ) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mIngredientsDataString = getArguments().getString("INGREDIENTS");
        mIsTwoPane = getArguments().getBoolean("ISTWOPANE");

        final View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients_layout, container, false);
        ButterKnife.bind(this, rootView);
        ArrayList<IngredientData>  data = IngredientData.stringToArray(mIngredientsDataString);
        ArrayList<String>  dataForWidget = new ArrayList<>();

        for(int i = 0 ; i < data.size();  i++ )
        {
            String ing  = data.get(i).Quantity + " " +data.get(i).Measure + " of " +data.get(i).Ingredient;
            dataForWidget.add(ing);
        }

       // mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe_ingredients);
        if(mIsTwoPane)
        {
            mLinearLayoutManager =    new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        }
        else
            {
            if (container.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            }
            else {
                mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            }
        }

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        IngredientsAdapter mAdapter = new IngredientsAdapter( data);
        // Set the adapter on the GridView
        mRecyclerView.setAdapter(mAdapter);


        BakingAppUpdateService.startBakingService(getContext(),dataForWidget);
        return    rootView;
    }
}
