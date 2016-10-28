package cn.ucai.fulicenter.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.L;

public class OrderActivity extends BaseActivity {

    @Bind(R.id.ed_order_name)
    EditText edOrderName;
    @Bind(R.id.ed_order_phone)
    EditText edOrderPhone;
    @Bind(R.id.spin_order_province)
    Spinner spinOrderProvince;
    @Bind(R.id.ed_order_street)
    EditText edOrderStreet;
    @Bind(R.id.tv_order_price)
    TextView tvOrderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        String cartIds = getIntent().getStringExtra(I.Cart.ID);
        L.e("orderActivity cartIds =" +cartIds);
    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.tv_order_buy)
    public void onClick() {
    }
}
