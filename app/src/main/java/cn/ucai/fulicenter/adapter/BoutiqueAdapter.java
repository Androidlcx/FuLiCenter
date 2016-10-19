package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;

/**
 * 精选页面的Adapter
 */
public class BoutiqueAdapter extends Adapter {
    //创建一个集合
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context Context, ArrayList<BoutiqueBean> list) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodsAdapter.FooterViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //数据填充
         if (holder instanceof  GoodsAdapter.FooterViewHolder){
             ((GoodsAdapter.FooterViewHolder)holder).tvFooter.setText(getFooterString());
         }
         if (holder instanceof BoutiqueViewHolder){
             BoutiqueBean boutiqueBean = mList.get(position);
             ImageLoader.downloadImg(mContext,((BoutiqueViewHolder)holder).ivBoutiqueImg,boutiqueBean.getImageurl());
             ((BoutiqueViewHolder)holder).tvBoutiqueTitle.setText(boutiqueBean.getTitle());
             ((BoutiqueViewHolder)holder).tvBoutiqueName.setText(boutiqueBean.getName());
             ((BoutiqueViewHolder)holder).tvBoutiqueDescription.setText(boutiqueBean.getDescription());
         }
    }

    private int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
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

    class BoutiqueViewHolder  extends ViewHolder{
        @Bind(R.id.ivBoutiqueImg)
        ImageView ivBoutiqueImg;
        @Bind(R.id.tvBoutiqueTitle)
        TextView tvBoutiqueTitle;
        @Bind(R.id.tvBoutiqueName)
        TextView tvBoutiqueName;
        @Bind(R.id.tvBoutiqueDescription)
        TextView tvBoutiqueDescription;
        @Bind(R.id.layout_boutique_item)
        RelativeLayout layoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
