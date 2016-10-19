package cn.ucai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.SpaceItemDecoration;
import cn.ucai.fulicenter.acitivity.MainActivity;
import cn.ucai.fulicenter.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;

public class BoutiqueFragment extends Fragment {
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    LinearLayoutManager llm;
    MainActivity mContext;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mAdapter = new BoutiqueAdapter(mContext,mList);
        initView();
        initData();//数据抓取
        return layout;
    }

    private void initData() {
        downloadBoutique(I.ACTION_DOWNLOAD);
    }

    private void downloadBoutique(final int action) {
        NetDao.downloadBoutique(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                srl.setRefreshing(false);//不在刷新
                tvRefresh.setVisibility(View.GONE);//提示不可见
                mAdapter.setMore(true);
                L.e("result"+result);
                if (result != null && result.length>0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN){
                        mAdapter.initData(list);
                    }else {
                        mAdapter.addData(list);
                    }
                    if (list.size()<I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                    }
                }else {
                    mAdapter.setMore(false);
                }
           }
        @Override
        public void onError(String error) {
            srl.setRefreshing(false);
            tvRefresh.setVisibility(View.GONE);
            mAdapter.setMore(false);
            CommonUtils.showLongToast(error);
            L.e("error"+error);
        }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private void initView() {
        /*下拉刷新小圆圈的颜色*/
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(mContext);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);//修复图片大小
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(12));//设置边距
    }
}
