package com.paper.wheretimego;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

    public MainAdapter(Context context, List<UsageStats> data) {
        inflater = LayoutInflater.from(context);
        datalist = data;
        this.mContext = context;
        mPakgageManger = context.getPackageManager();
    }

    @Override
    public MainAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainAdapterVH(inflater.inflate(R.layout.item_main, null));
    }

    @Override
    public void onBindViewHolder(MainAdapterVH holder, int position) {
        UsageStats usageStats = datalist.get(position);
        ApplicationInfo info = null;
        try {
            info = mPakgageManger.getApplicationInfo(usageStats.getPackageName(),0);
            holder.mainIcon.setImageDrawable(info.loadIcon(mPakgageManger));
            holder.mainAppName.setText(info.loadLabel(mPakgageManger));
            Bitmap bm = DateUtils.convertDrawable2BitmapByCanvas(info.loadIcon(mPakgageManger));
            Palette palette = Palette.generate(bm);
            holder.mCardview.setCardBackgroundColor(palette.getMutedColor(R.color.primary));
        } catch (PackageManager.NameNotFoundException e) {
            holder.mainIcon.setImageResource(R.mipmap.ic_launcher);
            holder.mainAppName.setText(usageStats.getPackageName() + "已卸载");
        }
        holder.mainUsetime.setText(DateUtils.formatTime(usageStats.getTotalTimeInForeground()/1000));

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
