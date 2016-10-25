package cn.ucai.fulicenter.acitivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.DisplayUtils;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.MFGT;

public class UserProfileActivity extends BaseActivity {

    @Bind(R.id.im_personal_data_userAvatar)
    ImageView imPersonalDataUserAvatar;
    @Bind(R.id.tv_personal_data_username)
    TextView tvPersonalDataUsername;
    @Bind(R.id.tv_personal_data_nick)
    TextView tvPersonalDataNick;

    UserProfileActivity mContext;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }
//修改标题名
    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.user_profile));
    }
//头像显示
    @Override
    protected void initData() {
        user = FuLiCenterApplication.getUser();
        if (user != null){
            //数据的加载
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,imPersonalDataUserAvatar);
            tvPersonalDataUsername.setText(user.getMuserName());
            tvPersonalDataNick.setText(user.getMuserNick());
        }else{
            finish();
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.rl_UserNameAvatar, R.id.rl_UserName, R.id.rl_Nick, R.id.btn_personal_data_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_UserNameAvatar:
                break;
            case R.id.rl_UserName:
                break;
            case R.id.rl_Nick:
                break;
            case R.id.btn_personal_data_exit:
                exit();//调用退出方法
                break;
        }
    }
//退出方法
    private void exit() {
        if (user != null){
            SharePrefrenceUtils.getInstance(mContext).removeUser();
            FuLiCenterApplication.setUser(null);//之后跳转到另一个页面
            MFGT.gotoLogin(mContext);
        }
        finish();
    }
}
