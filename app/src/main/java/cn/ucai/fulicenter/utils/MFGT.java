package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.acitivity.BoutiqueChildActivity;
import cn.ucai.fulicenter.acitivity.CategoryChildActivity;
import cn.ucai.fulicenter.acitivity.GoodsDetailActivity;
import cn.ucai.fulicenter.acitivity.MainActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;

//闪屏的实现封装
public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }
    //加参数的页面跳转
    public static void gotoGoodsDetailsActivity(Context context, int goodsId){
        Intent intent = new Intent();
               intent.setClass(context, GoodsDetailActivity.class);
               intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    //加参数的页面跳转
    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context,BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }
    //加参数的页面跳转
    public static void gotoCategoryChildActivity(Context context, int catId){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        startActivity(context,intent);
    }
}
