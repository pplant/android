package com.github.mobile.widget;

import com.github.mobile.R;

import com.github.mobile.ui.user.HomeActivity;
import com.github.mobile.widget.ui.NewsWidgetSettingsActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * WidgetProvider for the News Widget
 *
 * @author peter
 *
 */
public class NewsWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews rv = buildLayout(context,appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews buildLayout(Context context, int appWidgetId){
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.news_appwidget_layout);

        //Set up remote adapter
        Intent adapterIntent = new Intent(context, NewsWidgetService.class);
        adapterIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        adapterIntent.setData(Uri.parse(adapterIntent.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setRemoteAdapter(appWidgetId,R.id.news_list, adapterIntent);

        //Set up intent which is going to fired if the preferences icon is clicked
        Intent prefIntent = new Intent(context, NewsWidgetSettingsActivity.class);
        prefIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        prefIntent.setData(Uri.withAppendedPath(Uri.parse("abc" + "://widget/id/"), String.valueOf(appWidgetId)));
        PendingIntent pendingPrefIntent = PendingIntent.getActivity(context, 0, prefIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widget_preferences_icon, pendingPrefIntent);

        //Set up intent which is going to fired if app icon is clicked
        Intent iconIntent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIconIntent = PendingIntent.getActivity(context, 0, iconIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.widget_app_icon, pendingIconIntent);

        return rv;
    }
}
