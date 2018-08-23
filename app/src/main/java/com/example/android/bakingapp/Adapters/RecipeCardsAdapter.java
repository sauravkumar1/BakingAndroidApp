package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.Data.IngredientData;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Data.RecipeData;
import com.example.android.bakingapp.Activity.SingleRecipeActivity;
import com.example.android.bakingapp.Data.StepsData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeCardsAdapter extends RecyclerView.Adapter<RecipeCardsAdapter.RecipeCardsViewHolder> {



    private ArrayList<RecipeData> recipesDataList;
    private Context mContext;


    public RecipeCardsAdapter(ArrayList<RecipeData> recipesDataInfo) {
        recipesDataList = recipesDataInfo;
    }


    @Override
    public RecipeCardsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipes_list;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeCardsViewHolder viewHolder = new RecipeCardsViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecipeCardsViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return recipesDataList.size();
    }



    private void ExplicitIntent(View view, ArrayList<IngredientData> ingredients, ArrayList<StepsData> steps,String recipeName)
    {

        Class destinationClass = SingleRecipeActivity.class;
        Intent intentToStartDetailActivity = new Intent(mContext, destinationClass);

        intentToStartDetailActivity.putExtra("INGREDIENTS", IngredientData.arrayToString(ingredients));
        intentToStartDetailActivity.putExtra("STEPS", StepsData.arrayToString(steps));
        intentToStartDetailActivity.putExtra("RECIPENAME",recipeName );
        view.getContext().startActivity(intentToStartDetailActivity);

    }



    class RecipeCardsViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)    TextView recipeName;



        public RecipeCardsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           // recipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            recipeName.setShadowLayer(40, 20, 20, Color.BLACK);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            recipeName.setText(recipesDataList.get(listIndex).Name);
        }


        @Override
        public void onClick(View v) {
            Log.d("some error", "error occured");
            int clickedPosition = getAdapterPosition();
            ExplicitIntent(v,recipesDataList.get(clickedPosition).Ingredients,recipesDataList.get(clickedPosition).Steps,recipeName.getText().toString());



        }
    }
}
