package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CartBean;

/**
 * 精选页面的Adapter
 */
public class CartAdapter extends Adapter<CartAdapter.CartViewHolder> {
    //创建一个集合
    Context mContext;
    ArrayList<CartBean> mList;

    public CartAdapter(Context Context, ArrayList<CartBean> list) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(list);
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
             CartBean cartBean = mList.get(position);
//             ImageLoader.downloadImg(mContext,holder.ivBoutiqueImg,cartBean.getImageurl());
//             holder.tvBoutiqueTitle.setText(cartBean.getTitle());
//             holder.tvBoutiqueName.setText(cartBean.getName());
//             holder.tvBoutiqueDescription.setText(cartBean.getDescription());
//             holder.layoutBoutiqueItem.setTag(cartBean);//父类id
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList == null) {
            mList.clear();
        }
        mList.addAll(list);
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

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}