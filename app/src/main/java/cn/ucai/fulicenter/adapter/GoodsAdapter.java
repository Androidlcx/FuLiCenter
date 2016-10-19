package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.FooterViewHolder;
import cn.ucai.fulicenter.acitivity.GoodsDetailActivity;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/17.
 */
public class GoodsAdapter extends Adapter {
    Context mContext;
    List<NewGoodsBean> mList;
    boolean isMore;
    private  int footString;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public GoodsAdapter(Context Context, List<NewGoodsBean> List) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(List);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定一个布局文件
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //填充数据
        if (getItemViewType(position) == I.TYPE_FOOTER){
                FooterViewHolder vh = (FooterViewHolder) holder;
                vh.tvFooter.setText(getFootString());
        }else {
            GoodsViewHolder vh = (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            //设置图片
            ImageLoader.downloadImg(mContext,vh.ivGoodsThumb,goods.getGoodsThumb());
            vh.tvGoodsName.setText(goods.getGoodsName());//商品名字
            vh.tvGoodsPrice.setText(goods.getCurrencyPrice());//商品价钱
            vh.layoutGoods.setTag(goods.getGoodsId());//页面跳转
        }
    }

    private int getFootString() {
        return isMore?R.string.load_more : R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();//刷新
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

     class GoodsViewHolder extends ViewHolder {
        @Bind(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @Bind(R.id.layout_goods)
        LinearLayout layoutGoods;

        GoodsViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
        //点击跳转页面
        @OnClick(R.id.layout_goods)
        public void onGoodsItemCick(){
            int goodsId = (int) layoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext,goodsId);
//            mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
//                    .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));
        }
    }
}
