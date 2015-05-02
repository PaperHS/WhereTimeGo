package com.paper.wheretimego.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.Calendar;

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
public class DateUtils {
    /**
     * 设置为午夜时间
     * @param timestamp
     * @return
     */

    public static long setMidNight(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.SECOND,0);
        return  calendar.getTimeInMillis();
    }

    public static String formatTime(long timestamp){
//        SimpleDateFormat formatter = new SimpleDateFormat("HH小时mm分钟ss秒");
        StringBuffer sb = new StringBuffer();
        int h = (int)timestamp/3600;
        sb.append(h);
        sb.append("小时");
        int m = (int)(timestamp-h*3600)/60;
        sb.append(m);
        sb.append("分钟");
        sb.append(timestamp%60);
        sb.append("秒");
        return sb.toString();

    }
    public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
// canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
