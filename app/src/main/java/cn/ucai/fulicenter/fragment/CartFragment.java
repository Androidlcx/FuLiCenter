package cn.ucai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.SpaceItemDecoration;
import cn.ucai.fulicenter.acitivity.MainActivity;
import cn.ucai.fulicenter.adapter.CartAdapter;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ConvertUtils;
import cn.ucai.fulicenter.utils.L;

public class CartFragment extends BaseFragment {
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    LinearLayoutManager llm;
    MainActivity mContext;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter = new CartAdapter(mContext,mList);
//        initView();
//        initData();//数据抓取
//        setListener();
        super.onCreateView(inflater,container,savedInstanceState);
        return layout;
    }
    @Override
    protected void setListener() {
        setPullDownListener();
    }
    //下拉刷新
    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                downloadCart();
            }
        });

    }
    @Override
    protected void initData() {
        downloadCart();
    }
    private void downloadCart() {
        User user = FuLiCenterApplication.getUser();
        if (user != null){
            NetDao.downloadCart(mContext,user.getMuserName(), new OkHttpUtils.OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    L.e("result"+result);
                    srl.setRefreshing(false);//不在刷新
                    tvRefresh.setVisibility(View.GONE);//提示不可见
                    if (result != null && result.length>0){
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        mAdapter.initData(list);
                    }
                }
                @Override
                public void onError(String error) {
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    CommonUtils.showLongToast(error);
                    L.e("error"+error);
                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    protected void initView() {
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
