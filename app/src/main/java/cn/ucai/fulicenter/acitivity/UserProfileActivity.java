package cn.ucai.fulicenter.acitivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.DisplayUtils;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.dao.SharePrefrenceUtils;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.ImageLoader;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.utils.ResultUtils;

public class UserProfileActivity extends BaseActivity {

    @Bind(R.id.im_personal_data_userAvatar)
    ImageView imPersonalDataUserAvatar;
    @Bind(R.id.tv_personal_data_username)
    TextView tvPersonalDataUsername;
    @Bind(R.id.tv_personal_data_nick)
    TextView tvPersonalDataNick;

    UserProfileActivity mContext;
    User user = null;
    OnSetAvatarListener mOnSetAvatarListener;
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
        if (user == null){
            finish();
            return;
        }
        showInfo();
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.rl_UserNameAvatar, R.id.rl_UserName, R.id.rl_Nick, R.id.btn_personal_data_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_UserNameAvatar:
                mOnSetAvatarListener = new OnSetAvatarListener(mContext,R.id.layout_upload_avatar,
                        user.getMuserName(),I.AVATAR_TYPE_USER_PATH);
                break;
            case R.id.rl_UserName:
                CommonUtils.showLongToast(R.string.user_name_connot_be_modify);
                break;
            case R.id.rl_Nick:
                MFGT.gotoUpdateNick(mContext);
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

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }
//更新昵称提示

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("onActivityResult,requestCode="+requestCode+",resultCode="+resultCode);
        if (resultCode != RESULT_OK){
            return;
        }
        mOnSetAvatarListener.setAvatar(requestCode,data,imPersonalDataUserAvatar);
        if (requestCode == I.REQUEST_CODE_NICK){
            CommonUtils.showLongToast(R.string.update_user_nick_success);
        }
        if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO){
                updateAvatar();
        }
    }
//更新图片的方法
    private void updateAvatar() {
        File file = new File(OnSetAvatarListener.getAvatarPath(mContext,
                user.getMavatarPath()+"/"+user.getMuserName()
                +I.AVATAR_SUFFIX_JPG));
        L.e("file="+file.exists());
        L.e("file="+file.getAbsolutePath());
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.update_user_avatar));
        pd.show();
        NetDao.updateAvatar(mContext, user.getMuserName(), file, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                L.e("s="+s);
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e("result ="+result);
                if (result == null){
                       CommonUtils.showLongToast(R.string.update_usre_avatar_fail);
                }else {
                    User u = (User) result.getRetData();
                    if (result.isRetMsg()){
                        FuLiCenterApplication.setUser(u);
                        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(u),mContext,imPersonalDataUserAvatar);
                        CommonUtils.showLongToast(R.string.update_usre_avatar_success);
                    }else {
                        CommonUtils.showLongToast(R.string.update_usre_avatar_fail);
                    }
                }
                pd.dismiss();
            }
            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_usre_avatar_fail);
                L.e("error="+error);
            }
        });
    }

    private void showInfo(){
        user = FuLiCenterApplication.getUser();
        if (user != null){
            //数据的加载
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,imPersonalDataUserAvatar);
            tvPersonalDataUsername.setText(user.getMuserName());
            tvPersonalDataNick.setText(user.getMuserNick());
        }
    }
}
