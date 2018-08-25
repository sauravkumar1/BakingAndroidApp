package com.example.android.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.android.bakingapp.Data.IngredientData;
import com.example.android.bakingapp.R;
import static com.example.android.bakingapp.Widget.BakingAppWidgetProvider.ingredientsList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BakingAppWidgetService  extends RemoteViewsService {
    ArrayList<String> remoteViewingredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(),intent);
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context,Intent intent) {
            mContext = context;

        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            ArrayList<IngredientData> data = IngredientData.stringToArray(ingredientsList);
            ArrayList<String>  dataForWidget = new ArrayList<>();

            for(int i = 0 ; i < data.size();  i++ )
            {
                String ing  = data.get(i).Quantity + " " +data.get(i).Measure + " of " +data.get(i).Ingredient;
                dataForWidget.add(ing);
            }
            remoteViewingredientsList = dataForWidget;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return remoteViewingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_single_item);


            views.setTextViewText(R.id.tv_widget_single_item, remoteViewingredientsList.get(position));

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.tv_widget_single_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount()
        {
            return 1;
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }




    }


}

