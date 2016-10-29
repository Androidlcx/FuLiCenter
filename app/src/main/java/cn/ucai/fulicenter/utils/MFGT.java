package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.acitivity.BoutiqueChildActivity;
import cn.ucai.fulicenter.acitivity.CategoryChildActivity;
import cn.ucai.fulicenter.acitivity.CollectsActivity;
import cn.ucai.fulicenter.acitivity.GoodsDetailActivity;
import cn.ucai.fulicenter.acitivity.LoginActivity;
import cn.ucai.fulicenter.acitivity.MainActivity;
import cn.ucai.fulicenter.acitivity.OrderActivity;
import cn.ucai.fulicenter.acitivity.RegisterActivity;
import cn.ucai.fulicenter.acitivity.UpdateNickActivity;
import cn.ucai.fulicenter.acitivity.UserProfileActivity;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.CategoryChildBean;

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
        L.e("MFGT.intent=" +intent);
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
    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent);
    }
    //登录
    public static void gotoLogin(Activity context){
        Intent intent = new Intent();
        intent.setClass(context,LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_LOGIN);
    }
    //购物车跳转
    public static void gotoLoginFromCatr(Activity context){
        Intent intent = new Intent();
        intent.setClass(context,LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_LOGIN_FROM_CART);
    }
    //注册
    public static void gotoRegister(Activity context){
        Intent intent = new Intent();
        intent.setClass(context,RegisterActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);
    }
    //通用
    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        //切换动画
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    //跳转的方法
    public static void gotoSettings(Activity context){
        startActivity(context, UserProfileActivity.class);
    }
    //更新昵称跳转
    public static void gotoUpdateNick(Activity context){
        startActivityForResult(context,new Intent(context, UpdateNickActivity.class),I.REQUEST_CODE_NICK);
    }
    //跳转到收藏界面的方法
    public static void gotoCollects(Activity context){
        startActivity(context, CollectsActivity.class);
    }
    //购物车商品界面跳转到支付界面
    public static void gotoBuy(Activity context,String cartIds){
           Intent intent = new Intent(context, OrderActivity.class).putExtra(I.Cart.ID,cartIds);
            startActivity(context,intent);
    }
}