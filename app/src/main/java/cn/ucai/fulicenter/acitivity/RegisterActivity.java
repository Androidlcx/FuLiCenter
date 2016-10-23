package cn.ucai.fulicenter.acitivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.View.DisplayUtils;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.net.OkHttpUtils;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class RegisterActivity extends BaseActivity {
private static final String TAG = RegisterActivity.class.getSimpleName();
    @Bind(R.id.et_re_username)
    EditText etReUsername;
    @Bind(R.id.et_nick)
    EditText etNick;
    @Bind(R.id.et_re_password)
    EditText etRePassword;
    @Bind(R.id.et_re_conformpassword)
    EditText etReConformpassword;
    @Bind(R.id.btn_register)
    Button btnRegister;

    String username;//trim:去掉头尾的空格
    String nick;
    String passwrod;

    RegisterActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext  = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "用户注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_register)
    public void checkedInput() {
        //判断非空等
        username = etReUsername.getText().toString().trim();//trim:去掉头尾的空格
        nick = etNick.getText().toString().trim();
        passwrod = etRePassword.getText().toString().trim();
        String confirmpassword = etReConformpassword.getText().toString().trim();
            if (TextUtils.isEmpty(username)){
                CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
                etReUsername.requestFocus();
                return;
            }else if (!username.matches("[a-zA-Z]\\w{5,15}")){
                CommonUtils.showShortToast(R.string.illegal_user_name);
                etReUsername.requestFocus();
                return;
            }else if (TextUtils.isEmpty(nick)){
                CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
                etNick.requestFocus();
                return;
            }else if (TextUtils.isEmpty(passwrod)){
                CommonUtils.showShortToast(R.string.password_connot_be_empty);
                etRePassword.requestFocus();
                return;
            }else if (TextUtils.isEmpty(confirmpassword)){
                CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
                etReConformpassword.requestFocus();
                return;
            }else if (!passwrod.equals(confirmpassword)){
                CommonUtils.showShortToast(R.string.two_input_password);
                etReConformpassword.requestFocus();
                return;
            }
        register();
    }
//注册的方法
    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.register));
        pd.show();
        NetDao.register(mContext, username, nick, passwrod, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if (result == null){
                    CommonUtils.showLongToast(R.string.register_fail);
                }else{
                    if (result.isRetMsg()){
                        CommonUtils.showLongToast(R.string.register_success);
                        MFGT.finish(mContext);
                    }else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        etReUsername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showLongToast(error);
                L.e(TAG,"register error ="+error);
            }
        });
    }
}
