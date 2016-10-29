package cn.ucai.fulicenter.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PaySuccessActivity;
import com.pingplusplus.libone.PaymentHandler;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.DisplayUtils;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;

public class OrderActivity extends BaseActivity implements PaymentHandler{

    @Bind(R.id.ed_order_name)
    EditText edOrderName;
    @Bind(R.id.ed_order_phone)
    EditText edOrderPhone;
    @Bind(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @Bind(R.id.ed_order_street)
    EditText edOrderStreet;
    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;

    OrderActivity mContext;
    User user = null;
    String cartIds = "";
    ArrayList<CartBean> mList = null;
    String[] ids = new String[]{};
    int rankPrice = 0 ;

    private static String URL = "http://218.244.151.190/demo/charge";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mContext = this;
        mList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        // 设置要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "cnp", "bfb"});
        //设置是否支持外卡支付， true：支持， false：不支持， 默认不支持外卡
        //提交数据的格式，默认格式为json
        //PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";
        //是否开启日志
        PingppLog.DEBUG = true;
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getString(R.string.confirm_order));
    }

    @Override
    protected void initData() {
        cartIds = getIntent().getStringExtra(I.Cart.ID);
        user = FuLiCenterApplication.getUser();
        L.e("orderActivity cartIds =" +cartIds);
        if (cartIds == null || cartIds.equals("")
                || user == null){
            finish();
        }
        ids = cartIds.split(",");
        getOrderList();//获取购物车订单
    }

    private void getOrderList() {
        NetDao.downloadCart(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] s) {
                ArrayList<CartBean> list = ConvertUtils.array2List(s);
                if (list == null || list.size() == 0){
                    finish();
                }else {
                    mList.addAll(list);
                    sumPrice();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
//计算价格
    private void sumPrice() {
        rankPrice = 0 ;
        if (mList != null && mList.size() >0){
            for (CartBean c : mList){
                L.e("c.id=" +c.getId());
                for (String id : ids) {
                    L.e("order.id=" +id);
                    if (id.equals(String.valueOf(c.getId()))) {
                        rankPrice += getPrice(c.getGoods().getRankPrice()) * c.getCount();
                    }
                }
            }
        }
        tvOrderPrice.setText("合计：￥" + Double.valueOf(rankPrice));
    }
    private int getPrice(String price){
        price = price.substring(price.indexOf("￥") +1);
        return Integer.valueOf(price);
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_order_buy)
    public void onClick() {
        String receiveName = edOrderName.getText().toString();
        if (TextUtils.isEmpty(receiveName)){
            edOrderName.setError("收货人姓名不能为空！");
            edOrderName.requestFocus();
            return;
        }
        String mobile = edOrderPhone.getText().toString();
        if (TextUtils.isEmpty(mobile)){
            edOrderPhone.setError("手机号码不能为空！");
            edOrderPhone.requestFocus();
            return;
        }
        if (!mobile.matches("[\\d]{11}")){
            edOrderPhone.setError("手机号码格式错误！");
            edOrderPhone.requestFocus();
            return;
        }
        String area = spinOrderProvince.getSelectedItem().toString();
        if (TextUtils.isEmpty(area)){
            Toast.makeText(OrderActivity.this, "收货地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address = edOrderStreet.getText().toString();
        if (TextUtils.isEmpty(address)){
            edOrderStreet.setError("街道地址不能为空！");
            edOrderStreet.requestFocus();
            return;
        }
        gotoStatements();//结算提交方法
    }

    private void gotoStatements() {
        L.e("OrderActivity rankPrice= "+rankPrice);
        //产生订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss")
                .format(new Date());
        //构建账单json对象
        JSONObject bill = new JSONObject();
        //自定义的额外信息，选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1","extra1");
            extras.put("extra2","extra2");
        }catch (JSONException e){
            e.printStackTrace();
        }
           try {
               bill.put("order_no",orderNo);
               bill.put("amount",rankPrice*100);
               bill.put("extras",extras);
           }catch (JSONException e){
               e.printStackTrace();
           }
        //壹收款:创建支付通道的对话框
        PingppOne.showPaymentChannels(getSupportFragmentManager(),bill.toString(),URL,this);
    }
//处理支付的回调
    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            // result：支付结果信息
            // code：支付结果码
            //-2:用户自定义错误
            //-1：失败
            // 0：取消
            // 1：成功
            // 2:应用内快捷支付支付结果
   L.e("code="+data.getExtras().getInt("code"));
            if (data.getExtras().getInt("code") != 2) {
                PingppLog.d(data.getExtras().getString("result") + "  " + data.getExtras().getInt("code"));
            } else {
                String result = data.getStringExtra("result");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    if (resultJson.has("error")) {
                        result = resultJson.optJSONObject("error").toString();
                    } else if (resultJson.has("success")) {
                        result = resultJson.optJSONObject("success").toString();
                    }
                   L.e("result::" + result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //支付成功与否的后续逻辑处理方法
            int resultCode = data.getExtras().getInt("code");
            switch (resultCode){
                case 1:
                    PaySuccess();//删除已经支付成功的界面调用
                    CommonUtils.showLongToast(R.string.pingpp_title_activity_pay_sucessed);
                    break;
                case -1:
                    CommonUtils.showLongToast(R.string.pingpp_pay_failed);
                    finish();
                    break;
            }
        }
    }
//删除已经支付成功的界面
    private void PaySuccess() {
        for (String id : ids){
            NetDao.deleteCart(mContext,Integer.valueOf(id), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    L.e("result=="+result);
                }

                @Override
                public void onError(String error) {

                }
            });
        }
        finish();//关掉当前页面
    }
}
