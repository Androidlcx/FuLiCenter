package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * 精选页面的Adapter
 */
public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    //创建一个集合
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context Context, ArrayList<CartBean> list) {
        mContext = Context;
        mList = list;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CartViewHolder holder = new CartViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_catr, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        //数据填充
             final CartBean cartBean = mList.get(position);
             GoodsDetailsBean goods = cartBean.getGoods();
             if (goods != null){
                 ImageLoader.downloadImg(mContext,holder.ivCartThumb,goods.getGoodsThumb());
                 holder.tvCartGoodName.setText(goods.getGoodsName());
                 holder.tvCartPrice.setText(goods.getCurrencyPrice());
             }
             holder.ivCartCount.setText("("+cartBean.getCount()+")");
             holder.cbCartSelected.setChecked(cartBean.isChecked());
             holder.cbCartSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                      cartBean.setChecked(b);
                      //发送通知
                      mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                  }
              });
        holder.ivCartAdd.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        mList = list;
        notifyDataSetChanged();
    }
    class CartViewHolder extends ViewHolder{
        @Bind(R.id.cb_cart_selected)
        CheckBox cbCartSelected;
        @Bind(R.id.iv_cart_thumb)
        ImageView ivCartThumb;
        @Bind(R.id.tv_cart_good_name)
        TextView tvCartGoodName;
        @Bind(R.id.iv_cart_add)
        ImageView ivCartAdd;
        @Bind(R.id.iv_cart_count)
        TextView ivCartCount;
        @Bind(R.id.iv_cart_del)
        ImageView ivCartDel;
        @Bind(R.id.tv_cart_price)
        TextView tvCartPrice;
        @Bind(R.id.layout_cart_detail)
        RelativeLayout LayoutCartDetail;
        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick({R.id.iv_cart_thumb,R.id.tv_cart_good_name,R.id.tv_cart_price})
        public void gotoDetail(){
            final int position = (int) ivCartAdd.getTag();
            CartBean cart = mList.get(position);
            MFGT.gotoGoodsDetailsActivity(mContext,cart.getGoodsId());
        }
        @OnClick(R.id.iv_cart_add)
        public void addCart(){
            final int position = (int) ivCartAdd.getTag();
            CartBean cart = mList.get(position);
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() + 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    mList.get(position).setCount(mList.get(position).getCount() + 1);
                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                    ivCartCount.setText("("+(mList.get(position).getCount())+")");
                }

                @Override
                public void onError(String error) {

                }
            });

        }
        //购物车里商品数量的减少和删除
        @OnClick(R.id.iv_cart_del)
        public void delCart(){
            final int position = (int) ivCartAdd.getTag();
            CartBean cart = mList.get(position);
            if (cart.getCount() > 1){
            NetDao.updateCart(mContext, cart.getId(), cart.getCount() - 1, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    mList.get(position).setCount(mList.get(position).getCount() - 1);
                    mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                    ivCartCount.setText("("+(mList.get(position).getCount())+")");
                }

                @Override
                public void onError(String error) {

                }
            });
            }else{
                    NetDao.deleteCart(mContext, cart.getId(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()){
                                mList.remove(position);
                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                notifyDataSetChanged();//刷新一下列表
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
            }
        }
    }
}
