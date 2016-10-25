package cn.ucai.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.acitivity.MainActivity;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

/**
 * Created by Administrator on 2016/10/24.
 */
public class PresonalCenterFragment extends BaseFragment {
    private static final String TAG = PresonalCenterFragment.class.getSimpleName();
    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvUsername)
    TextView tvUsername;

    MainActivity mContext;
    User user = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        L.e(TAG,"user =" +user);
        if (user != null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,ivAvatar);
            tvUsername.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {

    }
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tvSettings,R.id.center_user_info})
    public void gotoSettings() {
        MFGT.gotoSettings(mContext);
    }
}
