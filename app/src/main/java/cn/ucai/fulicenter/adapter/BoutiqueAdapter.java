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
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.FooterViewHolder;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * 精选页面的Adapter
 */
public class BoutiqueAdapter extends Adapter<BoutiqueAdapter.BoutiqueViewHolder> {
    //创建一个集合
    Context mContext;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueAdapter(Context Context, ArrayList<BoutiqueBean> list) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    @Override
    public BoutiqueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BoutiqueViewHolder holder = new BoutiqueViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        return holder;
    }
    @Override
    public void onBindViewHolder(BoutiqueViewHolder holder, int position) {
        //数据填充
             BoutiqueBean boutiqueBean = mList.get(position);
             ImageLoader.downloadImg(mContext,holder.ivBoutiqueImg,boutiqueBean.getImageurl());
             holder.tvBoutiqueTitle.setText(boutiqueBean.getTitle());
             holder.tvBoutiqueName.setText(boutiqueBean.getName());
             holder.tvBoutiqueDescription.setText(boutiqueBean.getDescription());
             holder.layoutBoutiqueItem.setTag(boutiqueBean);//父类id
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList == null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
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
        @OnClick(R.id.layout_boutique_item)
        public void onBoutiqueClick(){
            BoutiqueBean bean = (BoutiqueBean)layoutBoutiqueItem.getTag();
            MFGT.gotoBoutiqueChildActivity(mContext,bean);
        }
    }
}
