package com.example.android.bakingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Adapters.StepsAdapter;
import com.example.android.bakingapp.Data.StepsData;
import com.example.android.bakingapp.Fragments.RecipeIngredientsFragment;
import com.example.android.bakingapp.Fragments.RecipeStepVideoFragment;
import com.example.android.bakingapp.Fragments.RecipeStepsFragment;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class   SingleRecipeActivity  extends AppCompatActivity implements  StepsAdapter.OnStepClickListener{

    private String mIngredientsDataString;
    private String mStepsDataString;
    private boolean isTwoPane ;
    private String mRecipeName;
    private int mPosition;



    @BindView(R.id.tb_topdetails)    Toolbar toolbarTop;

    @BindView(R.id.tv_top_title)    TextView mTitle;

    @BindView(R.id.back_icon)    ImageView backArrow;

    @BindView(R.id.top_icon)    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recipe_list_layout);
        ButterKnife.bind(this);
        String tag =findViewById(R.id.parent).getTag().toString();
        if(tag.equals("tablet"))
        isTwoPane = true;
        else
            isTwoPane = false;

     //   Toolbar toolbarTop = (Toolbar) findViewById(R.id.tb_topdetails);
       // TextView mTitle = (TextView) toolbarTop.findViewById(R.id.tv_top_title);
        mTitle.setText("  Baking Time");
       // ImageView icon  = (ImageView) toolbarTop.findViewById(R.id.top_icon);
        icon.setImageResource(R.drawable.stepsicon);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

     //   ImageView backArrow  = (ImageView) toolbarTop.findViewById(R.id.back_icon);
  backArrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          finish();

      }
  });



            Intent intentThatStartedThisActivity = getIntent();

            if (intentThatStartedThisActivity != null) {
                if (intentThatStartedThisActivity.hasExtra("INGREDIENTS")) {
                    mIngredientsDataString = intentThatStartedThisActivity.getStringExtra("INGREDIENTS");

                    if (intentThatStartedThisActivity.hasExtra("STEPS")) {
                        mStepsDataString = intentThatStartedThisActivity.getStringExtra("STEPS");

                    }
                    if (intentThatStartedThisActivity.hasExtra("RECIPENAME")) {
                        mRecipeName = intentThatStartedThisActivity.getStringExtra("RECIPENAME");

                    }

                    mTitle.setText(mRecipeName);

                    startStepsAndVideoFragments();
                }


                if(isTwoPane)
                {
                    onStepSelected(0);
                }

            }
    }


    private void startStepsAndVideoFragments()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("INGREDIENTS", mIngredientsDataString);
        bundle.putString("STEPS", mStepsDataString);
        bundle.putBoolean("ISTWOPANE", isTwoPane);
        RecipeIngredientsFragment ingredientFragment = new RecipeIngredientsFragment();
        ingredientFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.ingredient_container, ingredientFragment)
                .commit();
        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.steps_container, stepsFragment)
                .commit();

    }


    @Override
    public void onResume(){
        super.onResume();
        startStepsAndVideoFragments();
        // put your code here...

    }


    @Override
    public void onStepSelected(int position) {
        ArrayList<StepsData> data = StepsData.stringToArray(mStepsDataString);
        if(isTwoPane) {

            Bundle bundle = new Bundle();
            bundle.putString("DESCRIPTION", data.get(position).Description);
            bundle.putString("VIDEOURL", data.get(position).VideoURL);
            bundle.putString("STEPS", mStepsDataString);
            bundle.putInt("INDEX", position);
            bundle.putBoolean("ISTWOPANE", true);
            bundle.putString("RECIPENAME", mRecipeName);
            RecipeStepVideoFragment stepvideoFragment = new RecipeStepVideoFragment();
            stepvideoFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container, stepvideoFragment)
                    .commit();

            RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
            stepsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.steps_container, stepsFragment)
                    .commit();
        }
        else
        ExplicitIntent( position, data.get(position).Description,data.get(position).VideoURL);
    }

    private void ExplicitIntent(int index, String description, String videoUrl) {

        Class destinationClass = RecipeStepVideoActivity.class;
        Intent intentToStartDetailActivity = new Intent(getApplicationContext(), destinationClass);
        intentToStartDetailActivity.putExtra("DESCRIPTION", description);
        intentToStartDetailActivity.putExtra("VIDEOURL", videoUrl);
        intentToStartDetailActivity.putExtra("ALLSTEPS", mStepsDataString);
        intentToStartDetailActivity.putExtra("INDEX", index);
        intentToStartDetailActivity.putExtra("ISTWOPANE", false);
        intentToStartDetailActivity.putExtra("RECIPENAME",mRecipeName);
        getApplicationContext().startActivity(intentToStartDetailActivity);

    }
}



