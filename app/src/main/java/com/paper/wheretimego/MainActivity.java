package com.paper.wheretimego;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseAcitivity {

    @InjectView(R.id.main_recycleview)
    RecyclerView mainRecycleview;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    UsageStatsManager mUsageStatsManager;
    private Calendar mNowCal;
    public Boolean isIdle = true;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        UmengUpdateAgent.update(this);
        init();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mainRecycleview.setAdapter(mainAdapter);
    }

    private void init() {
        mNowCal = Calendar.getInstance();
        Calendar beginCal = Calendar.getInstance();
        beginCal.add(Calendar.DATE, -1);


        mUsageStatsManager = (UsageStatsManager) getSystemService("usagestats");
//        final List<UsageStats> queryUsageStats=mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, DateUtils.setMidNight(mNowCal.getTimeInMillis()), mNowCal.getTimeInMillis());
        List<UsageStats> queryUsageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginCal.getTimeInMillis(), mNowCal.getTimeInMillis());
//        UsageStats stats = queryUsageStats.get(0);
        Collections.sort(queryUsageStats, new UseTimeCompare());
        mainRecycleview.setLayoutManager(new LinearLayoutManager(this));
//        mainRecycleview.setItemAnimator(new DefaultItemAnimator());

        mainRecycleview.setItemAnimator(new RecyclerView.ItemAnimator() {
            List<RecyclerView.ViewHolder> mAnimationAddViewHolders = new ArrayList<RecyclerView.ViewHolder>();
            List<RecyclerView.ViewHolder> mAnimationRemoveViewHolders = new ArrayList<RecyclerView.ViewHolder>();

            @Override
            public void runPendingAnimations() {
                Log.e("hshs", "runPendingAnimations");
                if (!mAnimationAddViewHolders.isEmpty()) {

                    AnimatorSet animator;
                    View target;
                    for (final RecyclerView.ViewHolder viewHolder : mAnimationAddViewHolders) {
                        target = viewHolder.itemView;
                        animator = new AnimatorSet();

                        animator.playTogether(
                                ObjectAnimator.ofFloat(target, "translationX", -target.getMeasuredWidth(), 0.0f),
                                ObjectAnimator.ofFloat(target, "alpha", target.getAlpha(), 1.0f)
                        );

                        animator.setTarget(target);
                        animator.setDuration(100);
                        animator.addListener(new Animator.AnimatorListener() {

                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mAnimationAddViewHolders.remove(viewHolder);
                                if (!isRunning()) {
                                    dispatchAnimationsFinished();
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        animator.start();
                    }
                } else if (!mAnimationRemoveViewHolders.isEmpty()) {
                }
            }

            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                Log.e("hshs", "animateRemove");
                return mAnimationRemoveViewHolders.add(holder);
            }

            @Override
            public boolean animateAdd(RecyclerView.ViewHolder holder) {
                ViewCompat.animate(holder.itemView).translationX(0).translationXBy(-100).setDuration(1000).start();
                Log.e("hshs", "animateAdd");
                return mAnimationAddViewHolders.add(holder);
            }

            @Override
            public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
                Log.e("hshs", "fromx:" + fromX + "   fromy:" + fromY);
                return false;
            }

            @Override
            public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
                Log.e("hshs", "animateChange");
                return false;
            }

            @Override
            public void endAnimation(RecyclerView.ViewHolder item) {
                Log.e("hshs", "endAnimation");
            }

            @Override
            public void endAnimations() {

            }

            @Override
            public boolean isRunning() {
                return false;
            }
        });
        mainAdapter = new MainAdapter(this, queryUsageStats);

        mainRecycleview.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Log.e("hshs", "dx:" + dx + "   dy" + dy);
//                if (Math.abs(dy)<100){
//                    isIdle = true;
//                }else isIdle = false;
//                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//                            isIdle = false;
//                            break;
//
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                            isIdle= true;
//                           mainAdapter.notifyDataSetChanged();
//                            break;
//                        default:
//                            break;
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    private static class UseTimeCompare implements Comparator<UsageStats> {

        @Override
        public int compare(UsageStats a, UsageStats b) {
            return (int) (b.getTotalTimeInForeground() - a.getTotalTimeInForeground());
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}


