package cn.ucai.fulicenter.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.FlowIndicator;
import cn.ucai.fulicenter.View.SlideAutoLoopView;
import cn.ucai.fulicenter.bean.AlbumsBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class GoodsDetailActivity extends BaseActivity {

    @Bind(R.id.backClickArea)
    LinearLayout backClickArea;
    @Bind(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @Bind(R.id.tv_good_name)
    TextView tvGoodName;
    @Bind(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @Bind(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @Bind(R.id.salv)
    SlideAutoLoopView salv;
    @Bind(R.id.indicator)
    FlowIndicator indicator;
    @Bind(R.id.wv_goods_brief)
    WebView wvGoodsBrief;

    int goodsId;
    GoodsDetailActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsId);
        if (goodsId == 0){
            finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);
    }
   @Override
    protected void setListener() {

    }
   @Override
    protected void initData() {
        NetDao.downloadGoodsDetail(mContext, goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details="+result);
                if (result != null){
                    showGoodDetails(result);
                }else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error="+error);
                CommonUtils.showLongToast(error);
            }
        });
    }
//数据填充
    private void showGoodDetails(GoodsDetailsBean details) {
        tvGoodNameEnglish.setText(details.getGoodsEnglishName());
        tvGoodName.setText(details.getGoodsName());
        tvGoodPriceCurrent.setText(details.getCurrencyPrice());
        tvGoodPriceShop.setText(details.getShopPrice());
        //轮播图片以及外部webView
        salv.startPlayLoop(indicator,getAlbumImUrl(details),getAlbumImgCount(details));
        //商品简介webView加载数据
        wvGoodsBrief.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties() != null && details.getProperties().length>0){
            return details.getProperties()[0].getAlbums().length;
            }
        return 0;
    }

    private String[] getAlbumImUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getProperties() != null && details.getProperties().length>0){
            AlbumsBean [] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0 ;i<albums.length;i++){
                urls[i] = albums[i].getImgUrl();
            }
        }
        return  urls;
    }
//顶部返回按钮
    @Override
    protected void initView() {

    }
    @OnClick(R.id.backClickArea)
    public void onBackClick(){
        MFGT.finish(this);
    }
    //系统返回按钮
    public void back(View view){
        MFGT.finish(this);
    }
}
