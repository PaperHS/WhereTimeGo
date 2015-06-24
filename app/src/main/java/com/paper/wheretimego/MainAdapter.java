package com.paper.wheretimego;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paper.wheretimego.utils.DateUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * ,==.              |~~~
 * /  66\             |
 * \c  -_)         |~~~
 * `) (           |
 * /   \       |~~~
 * /   \ \      |
 * ((   /\ \_ |~~~
 * \\  \ `--`|
 * / / /  |~~~
 * ___ (_(___)_|
 * <p/>
 * Created by Paper on 15-4-28 2015.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterVH> {

    LayoutInflater inflater;
    List<UsageStats> datalist;
    Context mContext;
    PackageManager mPakgageManger;
    RecyclerView recyclerView;
    private CompositeSubscription _subscriptions;
    public MainAdapter(Context context, List<UsageStats> data ) {
        inflater = LayoutInflater.from(context);
        datalist = data;
        this.mContext = context;
        mPakgageManger = context.getPackageManager();
//        _subscriptions = new CompositeSubscription();
//        ConnectableObservable<Object> tapEventEmitter = RxBus.getBus().toObserverable().publish();
    }


    @Override
    public MainAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainAdapterVH(inflater.inflate(R.layout.item_main, null));
    }

    @Override
    public void onViewDetachedFromWindow(MainAdapterVH holder) {
        super.onViewDetachedFromWindow(holder);
//        holder.itemView.clearAnimation();
    }

    @Override
    public void onBindViewHolder(final MainAdapterVH holder, int position) {
        holder.itemView.setX(-holder.itemView.getWidth());
        ViewCompat.animate(holder.itemView).setDuration(1000).translationX(0).start();
        UsageStats usageStats = datalist.get(position);
        ApplicationInfo info = null;
        Observable<ApplicationInfo> myObserve =Observable.create(new Observable.OnSubscribe<ApplicationInfo>(){

            @Override
            public void call(Subscriber<? super ApplicationInfo> subscriber) {

            }
        });

        Subscriber<ApplicationInfo> mySub = new Subscriber<ApplicationInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ApplicationInfo info) {

            }
        };
        myObserve.subscribe(new Action1<ApplicationInfo>() {
            @Override
            public void call(ApplicationInfo applicationInfo) {

            }
        });

        if (((MainActivity)mContext).isIdle) {
            try {
                info = mPakgageManger.getApplicationInfo(usageStats.getPackageName(), 0);
                Observable.just(info)

                        .subscribeOn(AndroidSchedulers.mainThread())
                        .map(new Func1<ApplicationInfo, ApplicationInfo>() {
                            @Override
                            public ApplicationInfo call(ApplicationInfo info) {
                                holder.mainIcon.setImageDrawable(info.loadIcon(mPakgageManger));
                                holder.mainAppName.setText(info.loadLabel(mPakgageManger));
                                return info;
                            }
                        })
                        .map(new Func1<ApplicationInfo, Integer>() {
                            @Override
                            public Integer call(ApplicationInfo applicationInfo) {
                                Bitmap bm = DateUtils.convertDrawable2BitmapByCanvas(applicationInfo.loadIcon(mPakgageManger));
                                Palette palette = Palette.generate(bm);
                                return palette.getMutedColor(R.color.primary);
                            }
                        })

                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer i) {


                                holder.mCardview.setCardBackgroundColor(i);
                            }
                        });
            } catch (PackageManager.NameNotFoundException e) {
                holder.mainIcon.setImageResource(R.mipmap.ic_launcher);
                holder.mainAppName.setText(usageStats.getPackageName() + "已卸载");
            }
            holder.mainUsetime.setText(DateUtils.formatTime(usageStats.getTotalTimeInForeground() / 1000));
        }
    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class MainAdapterVH extends RecyclerView.ViewHolder {
        @InjectView(R.id.main_icon)
        ImageView mainIcon;
        @InjectView(R.id.main_app_name)
        TextView mainAppName;
        @InjectView(R.id.main_usetime)
        TextView mainUsetime;
        @InjectView(R.id.main_cardview)
        CardView mCardview;
        public MainAdapterVH(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);

        }
    }

}
