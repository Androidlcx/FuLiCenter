package cn.ucai.fulicenter.acitivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
import cn.ucai.fulicenter.dao.UserDao;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.ResultUtils;

public class LoginActivity extends BaseActivity {
private static final String TAG = LoginActivity.class.getSimpleName();
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;

    String username;
    String password;
    LoginActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(mContext,getResources().getString(R.string.login));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_Login, R.id.btn_Register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                checkedInput();//验证非空的方法
                break;
            case R.id.btn_Register:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkedInput() {
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            CommonUtils.showLongToast(R.string.user_name_connot_be_empty);
            etUsername.requestFocus();
            return;
        }else if (TextUtils.isEmpty(password)){
            CommonUtils.showLongToast(R.string.password_connot_be_empty);
            etPassword.requestFocus();
            return;
        }
        login();//验证完成后的调用方法
    }

    private void login() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.logining));
        pd.show();
        NetDao.login(mContext, username, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s,User.class);
                L.e(TAG,"result =" +result);
                if (result == null){
                    CommonUtils.showLongToast(R.string.login_fail);
                }else {
                        if (result.isRetMsg()){
                            User user = (User) result.getRetData();
                            L.e(TAG,"user ="+user);
                            //保存user的数据到数据库里
                            UserDao dao = new UserDao(mContext);
                           boolean isSuccess = dao.saveUser(user);
                            if (isSuccess){
                                SharePrefrenceUtils.getInstance(mContext).saveUser(user.getMuserName());
                                FuLiCenterApplication.setUser(user);
                                MFGT.finish(mContext);
                            }else {
                                CommonUtils.showLongToast(R.string.user_database_error);
                            }
                        }else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER){
                                CommonUtils.showLongToast(R.string.login_fail_unknow_user);
                            }else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD){
                                CommonUtils.showLongToast(R.string.login_fail_error_password);
                            }else {
                                CommonUtils.showLongToast(R.string.login_fail);
                            }
                        }
                    }
                pd.dismiss();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG,"error =" +error);
            }
        });
    }

    //满足条件注册
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_REGISTER){
            String name = data.getStringExtra(I.User.USER_NAME);
            etUsername.setText(name);
        }
    }
}
