package com.example.android.bakingapp.Fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;

import com.example.android.bakingapp.Activity.SingleRecipeActivity;

import com.example.android.bakingapp.Adapters.StepsAdapter;

import com.example.android.bakingapp.Data.StepsData;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepsFragment  extends  Fragment
{
        String mStepsDataString;
        private  int mSelectedIndex ;
        private  boolean mIsTwoPane;
    @BindView(R.id.rv_recipe_steps)    RecyclerView mRecyclerView;
        public RecipeStepsFragment( ) {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            mStepsDataString = getArguments().getString("STEPS");
            mSelectedIndex = getArguments().getInt("INDEX");
            mIsTwoPane =  getArguments().getBoolean("ISTWOPANE");

            final View rootView = inflater.inflate(R.layout.fragment_recipe_steps_layout, container, false);

            ButterKnife.bind(this, rootView);
            ArrayList<StepsData> data = StepsData.stringToArray(mStepsDataString);

          //  mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipe_steps);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

            mRecyclerView.setLayoutManager(layoutManager);
            // Create the adapter
            // This adapter takes in the context and an ArrayList of ALL the image resources to display
            StepsAdapter mAdapter = new StepsAdapter( data, mSelectedIndex,mIsTwoPane, (SingleRecipeActivity)getActivity());



            // Set the adapter on the GridView
            mRecyclerView.setAdapter(mAdapter);



            return    rootView;
        }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putLong("INDEX", mSelectedIndex);

    }

    }
