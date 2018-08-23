package com.example.android.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.Data.StepsData;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private ArrayList<StepsData> mRecipeSteps;
    private Context mContext;
    private int mCurrentSelectedIndex;
    private  boolean mIsTwoPane;


    public StepsAdapter(ArrayList<StepsData> stepsData, int index, boolean isTwoPane,OnStepClickListener listener) {
        mRecipeSteps = stepsData;
        mCurrentSelectedIndex = index;
        mIsTwoPane = isTwoPane;
        mCallback= listener;
    }

    OnStepClickListener mCallback;


    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.steps_layout;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        StepsViewHolder viewHolder = new StepsViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mRecipeSteps.size();
    }




    class StepsViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener {

        @BindView(R.id.tv_steps)    TextView stepData;

        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           // stepData = (TextView) itemView.findViewById(R.id.tv_steps);
            stepData.setShadowLayer(20, 10, 10, Color.BLACK);
            stepData.setOnClickListener(this);
        }

        void bind(int listIndex) {
            StepsData data = mRecipeSteps.get(listIndex);
            String dataString = data.ShortDescription;
            stepData.setText(dataString);

            if(mIsTwoPane) {
                if (mCurrentSelectedIndex == listIndex)
                    stepData.setBackgroundResource(R.drawable.back_selected_step);
                else {
                    stepData.setBackgroundResource(R.drawable.back);
                }
            }

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            stepData.setBackgroundResource(R.drawable.back_selected_step);
            mCallback.onStepSelected(clickedPosition);

        }
    }
}
