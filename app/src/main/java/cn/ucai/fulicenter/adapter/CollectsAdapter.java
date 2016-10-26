package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.FooterViewHolder;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/17.
 */
public class CollectsAdapter extends Adapter {
    Context mContext;
    List<CollectBean> mList;
    boolean isMore;
    int sortBy = I.SORT_BY_ADDTIME_DESC;//降序

    public boolean isMore() {
        return isMore;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        notifyDataSetChanged();//从新更新页面
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public CollectsAdapter(Context Context, List<CollectBean> List) {
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
            holder = new CollectsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
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
            CollectsViewHolder vh = (CollectsViewHolder) holder;
            CollectBean goods = mList.get(position);
            //设置图片
            ImageLoader.downloadImg(mContext,vh.ivGoodsThumb,goods.getGoodsThumb());
            vh.tvGoodsName.setText(goods.getGoodsName());//商品名字
            vh.layoutGoods.setTag(goods);//页面跳转
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

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();//刷新
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

     class CollectsViewHolder extends ViewHolder {
        @Bind(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.iv_collect_del)
        ImageView ivCollectDel;
        @Bind(R.id.layout_goods)
        RelativeLayout layoutGoods;

         CollectsViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
        //点击跳转页面
        @OnClick(R.id.layout_goods)
        public void onGoodsItemCick(){
            CollectBean goods = (CollectBean) layoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext,goods.getGoodsId());
//            mContext.startActivity(new Intent(mContext, GoodsDetailActivity.class)
//                    .putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId));
        }
         @OnClick(R.id.iv_collect_del)
         public void deleteCollect(){
             final CollectBean goods = (CollectBean) layoutGoods.getTag();
             String username = FuLiCenterApplication.getUser().getMuserName();
             NetDao.deleteCollect(mContext, username, goods.getGoodsId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                 @Override
                 public void onSuccess(MessageBean result) {
                     if (result != null && result.isSuccess()){
                         mList.remove(goods);
                         notifyDataSetChanged();
                     }else {
                         CommonUtils.showLongToast(result != null ? result.getMsg():mContext.getResources().getString(R.string.delete_collect_fail));
                     }
                 }

                 @Override
                 public void onError(String error) {
                     L.e("error =" +error);
                     CommonUtils.showLongToast(mContext.getResources().getString(R.string.delete_collect_fail));
                 }
             });
         }
    }
}
