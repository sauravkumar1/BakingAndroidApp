package com.example.android.bakingapp.Fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.Data.StepsData;
import com.example.android.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepVideoFragment  extends Fragment
{
    private String mDescription;
    private String mVideoUrl;
    private ArrayList<StepsData> mAllSteps;
    private int mIndex;
    private long mPosition;
    private  View mRootView;

    @Nullable    @BindView(R.id.tv_step_description)    TextView description;
    @BindView(R.id.playerView)    SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;

    private Context mContext;
    private  boolean mIsTwoPane;
    @Nullable    @BindView(R.id.iv_next)    ImageView mNextStep;
    @Nullable    @BindView(R.id.iv_back)    ImageView mPreviousStep;


    OnFragmentStepClickListener mCallback;


    public interface OnFragmentStepClickListener {
        void onFragmentStepSelected(int position);
    }
    // Mandatory empty constructor
    public  RecipeStepVideoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext= container.getContext();
        mVideoUrl = getArguments().getString("VIDEOURL");
        mDescription = getArguments().getString("DESCRIPTION");
        final String allSteps = getArguments().getString("STEPS");
        mAllSteps = StepsData.stringToArray(allSteps);
        mIndex = getArguments().getInt("INDEX");
        mIsTwoPane = getArguments().getBoolean("ISTWOPANE");
        final View rootView = inflater.inflate(R.layout.fragment_recipe_video_layout, container, false);
        ButterKnife.bind(this, rootView);
        String tag  = rootView.getTag().toString();
        if (mVideoUrl.contains("https")) {

            if(savedInstanceState !=null && savedInstanceState.containsKey("POSITION"))
            {
                mPosition = savedInstanceState.getLong("POSITION");
            }
            initializePlayer(Uri.parse(mVideoUrl), rootView);
        }
        else
        {
            mExoPlayer=null;
            mPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.novideo));
        }
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !mIsTwoPane)
        {
            return    rootView;
        }

        initialiseText(rootView);
        if(!mIsTwoPane)
        {
            if(mIndex==0)
        mPreviousStep.setVisibility(View.INVISIBLE);
        if(mIndex==mAllSteps.size()-1)
            mNextStep.setVisibility(View.INVISIBLE);


            mCallback =(OnFragmentStepClickListener)getActivity();
                mNextStep.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    int lastIndex = mAllSteps.size() - 1;
                    if (mIndex < lastIndex) {
                        if (mExoPlayer != null) {
                            mExoPlayer.stop();
                        }
                        mIndex++;
                        mCallback.onFragmentStepSelected(mIndex);
                        if (mIndex == lastIndex) {
                            mNextStep.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "This is the Last step of the recipe", Toast.LENGTH_SHORT).show();

                        }
                        mDescription = mAllSteps.get(mIndex).Description;
                        mVideoUrl = mAllSteps.get(mIndex).VideoURL;
                        if (mVideoUrl.contains("https")) {
                            initializePlayer(Uri.parse(mVideoUrl), rootView);
                        } else {
                            mExoPlayer = null;
                            mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
                            mPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.novideo));
                        }
                        initialiseText(rootView);
                        mPreviousStep.setVisibility(View.VISIBLE);
                    }

                }
            });
            mPreviousStep.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if (mIndex > 0) {
                        if (mExoPlayer != null) {
                            mExoPlayer.stop();
                        }
                        mIndex--;
                        mCallback.onFragmentStepSelected(mIndex);
                        if (mIndex == 0) {
                            mPreviousStep.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "This is the First step of the recipe", Toast.LENGTH_SHORT).show();

                        }
                        mDescription = mAllSteps.get(mIndex).Description;
                        mVideoUrl = mAllSteps.get(mIndex).VideoURL;
                        if (mVideoUrl.contains("https")) {
                            initializePlayer(Uri.parse(mVideoUrl), rootView);
                        } else {

                            mExoPlayer = null;
                            mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
                            mPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.novideo));
                        }
                        initialiseText(rootView);
                        mNextStep.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        mRootView = rootView;
        return    rootView;
    }

    private void initialiseText(View view )
    {
        description.setText(mDescription );
        description.setShadowLayer(30, 2, 2, Color.GRAY);
        description.setMovementMethod(new ScrollingMovementMethod());
    }

    private void initializePlayer(Uri mediaUri, View view) {

            mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            mPlayerView.setForeground(null);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(mContext, "Recipes");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    mContext, userAgent), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(mPosition);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);



    }



    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        if(mVideoUrl !=null)
        initializePlayer( Uri.parse(mVideoUrl),mRootView);

    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mExoPlayer!=null) {
            mPosition = mExoPlayer.getCurrentPosition();

        }
        outState.putLong("POSITION", mPosition);

    }


    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer= null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer!=null) {
       //     mPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer =null;
        }
    }



}
