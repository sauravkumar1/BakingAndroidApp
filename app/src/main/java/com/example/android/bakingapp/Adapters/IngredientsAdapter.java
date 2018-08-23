package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Data.IngredientData;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private ArrayList<IngredientData> mRecipeIngredients;
    private Context mContext;

    public IngredientsAdapter(ArrayList<IngredientData> ingredientData) {
        mRecipeIngredients = ingredientData;
    }


    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredients_layout;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeIngredients.size();
    }


    class IngredientsViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_ingredient)    TextView ingredientData;


        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //ingredientData = (TextView) itemView.findViewById(R.id.tv_ingredient);
            ingredientData.setShadowLayer(20, 10, 10, Color.BLACK);
        }

        void bind(int listIndex) {
              IngredientData data = mRecipeIngredients.get(listIndex);
              String dataString = data.Quantity + " " + data.Measure + " of " + data.Ingredient;

            ingredientData.setText(dataString);

        }


    }
}
