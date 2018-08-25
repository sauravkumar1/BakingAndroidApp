package com.example.android.bakingapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Data.StepsData;
import com.example.android.bakingapp.Fragments.RecipeStepVideoFragment;
import com.example.android.bakingapp.Fragments.RecipeStepVideoFragment.OnFragmentStepClickListener;

import com.example.android.bakingapp.R;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;


public class RecipeStepVideoActivity extends AppCompatActivity implements OnFragmentStepClickListener  {
    private String mDescription;
    private String mVideoUrl;
    private String mAllSteps;
    private  String mIngredients;
    private int mIndex;
    private String mRecipeName;
    private static  final String  mIndexOnRotation = "IndexOnRotation";


    @Nullable @BindView(R.id.tb_topdetails)    Toolbar toolbarTop;

    @Nullable  @BindView(R.id.tv_top_title)    TextView mTitle;

    @Nullable @BindView(R.id.top_icon)    ImageView icon;

    @Override
    public void onFragmentStepSelected(int position)
    {
        mIndex = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_video_layout);

        ButterKnife.bind(this);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.tb_topdetails);
        if(toolbarTop !=null) {
            TextView mTitle = (TextView) toolbarTop.findViewById(R.id.tv_top_title);
            mTitle.setText("  Baking Time");
            ImageView icon = (ImageView) toolbarTop.findViewById(R.id.top_icon);
            icon.setImageResource(R.drawable.stepsicon);
            setSupportActionBar(toolbarTop);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("DESCRIPTION")) {
                mDescription = intentThatStartedThisActivity.getStringExtra("DESCRIPTION");

                if (intentThatStartedThisActivity.hasExtra("VIDEOURL")) {
                    mVideoUrl = intentThatStartedThisActivity.getStringExtra("VIDEOURL");
                }
                if (intentThatStartedThisActivity.hasExtra("INGREDIENTS")) {
                    mIngredients = intentThatStartedThisActivity.getStringExtra("INGREDIENTS");
                }
                if (intentThatStartedThisActivity.hasExtra("STEPS")) {
                    mAllSteps = intentThatStartedThisActivity.getStringExtra("STEPS");

                }
                if (intentThatStartedThisActivity.hasExtra("RECIPENAME")) {
                    mRecipeName = intentThatStartedThisActivity.getStringExtra("RECIPENAME");

                }
                if (intentThatStartedThisActivity.hasExtra("INDEX")) {
                    mIndex = intentThatStartedThisActivity.getIntExtra("INDEX",0);
                }

                if (savedInstanceState != null) {
                   ArrayList<StepsData> data= StepsData.stringToArray(mAllSteps);
                    mIndex = savedInstanceState.getInt(mIndexOnRotation, 0);
                    mDescription = data.get(mIndex).Description;
                    mVideoUrl =data.get(mIndex).VideoURL;
                }
                if(savedInstanceState == null)
                    StartFragment();

            }
        }

        SharedPreferences.Editor editor = getSharedPreferences("SHARED_DATA", MODE_PRIVATE).edit();
        editor.putString("INGREDIENTS", mIngredients);
        editor.putString("STEPS", mAllSteps);
        editor.putString("RECIPENAME", mRecipeName);
        editor.apply();

        SetToolBar();
    }


    private void StartFragment()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("DESCRIPTION", mDescription);
        bundle.putString("VIDEOURL", mVideoUrl);
        bundle.putString("STEPS", mAllSteps);
        bundle.putInt("INDEX", mIndex);
        RecipeStepVideoFragment stepvideoFragment = new RecipeStepVideoFragment();
        stepvideoFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.video_container, stepvideoFragment)
                .commit();
    }
    private  void  SetToolBar()
    {
        if( getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
           // Toolbar toolbarTop =     (Toolbar) findViewById(R.id.tb_topdetails);
          //  TextView mTitle = (TextView) toolbarTop.findViewById(R.id.tv_top_title);

            mTitle.setText(mRecipeName);
         //   ImageView icon = (ImageView) toolbarTop.findViewById(R.id.top_icon);
            icon.setImageResource(R.drawable.stepsicon);
            setSupportActionBar(toolbarTop);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(outState);
        // Save our own state now
        outState.putInt(mIndexOnRotation, mIndex);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }



}
