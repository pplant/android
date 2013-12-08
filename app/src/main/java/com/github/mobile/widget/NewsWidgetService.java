package com.github.mobile.widget;

import java.util.ArrayList;
import java.util.List;

import com.github.mobile.R;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class NewsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new NewsRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}

class NewsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final int mCount = 10;
    private List<String> mWidgetItems = new ArrayList<String>();
    private Context mContext;
    private int mAppWidgetId;

    public NewsRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {
        for (int i = 0; i < mCount; i++) {
            mWidgetItems.add(i + "!");
        }
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        // TODO setLoadingView
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.news_item);
        rv.setTextViewText(R.id.tv_event, mWidgetItems.get(position));

        return rv;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

    }

}
