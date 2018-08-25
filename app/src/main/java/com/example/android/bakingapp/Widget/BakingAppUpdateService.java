package com.example.android.bakingapp.Widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class BakingAppUpdateService  extends IntentService {



    public BakingAppUpdateService() {
        super("BakingAppUpdateService");
    }

    public static void startBakingService(Context context, String fromActivityIngredientsList,String steps, String recipeName) {
        Intent intent = new Intent(context, BakingAppUpdateService.class);
        intent.putExtra("INGREDIENTS",fromActivityIngredientsList);
        intent.putExtra("STEPS",steps);
        intent.putExtra("RECIPENAME",recipeName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String fromActivityIngredientsList = intent.getExtras().getString("INGREDIENTS");
            String steps = intent.getExtras().getString("STEPS");
            String recipeName = intent.getExtras().getString("RECIPENAME");
            handleActionUpdateBakingWidgets(fromActivityIngredientsList,steps, recipeName);
        }
    }



    private void handleActionUpdateBakingWidgets(String fromActivityIngredientsList, String steps, String recipeName) {
        Intent intent = new Intent();
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.putExtra("INGREDIENTS",fromActivityIngredientsList);
        intent.putExtra("SETPS",steps);
        intent.putExtra("RECIPENAME",recipeName);
        sendBroadcast(intent);
    }

}
