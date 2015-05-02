package com.paper.wheretimego;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.main_recycleview)
    RecyclerView mainRecycleview;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
     UsageStatsManager mUsageStatsManager;
    private Calendar mNowCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
       setSupportActionBar(toolbar);

        init();

    }

    private void init() {
        mNowCal = Calendar.getInstance();
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.DATE,-1);


        mUsageStatsManager = (UsageStatsManager) getSystemService("usagestats");
//        final List<UsageStats> queryUsageStats=mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, DateUtils.setMidNight(mNowCal.getTimeInMillis()), mNowCal.getTimeInMillis());
         List<UsageStats> queryUsageStats=mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), mNowCal.getTimeInMillis());
//        UsageStats stats = queryUsageStats.get(0);
        Collections.sort(queryUsageStats,new UseTimeCompare());
        mainRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mainRecycleview.setAdapter(new MainAdapter(this,queryUsageStats));
    }



    private static class UseTimeCompare implements Comparator<UsageStats> {

        @Override
        public int compare(UsageStats a, UsageStats b) {
            return (int)(b.getTotalTimeInForeground() - a.getTotalTimeInForeground());
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}

