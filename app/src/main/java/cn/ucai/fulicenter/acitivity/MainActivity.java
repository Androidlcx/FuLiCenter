package cn.ucai.fulicenter.acitivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {


    int index;
    RadioButton[] rbs;
    Fragment[] mFragments;

    NewGoodsFragment mNewGoodsFragment;
    @Bind(R.id.xp)
    RadioButton xp;
    @Bind(R.id.jx)
    RadioButton jx;
    @Bind(R.id.fl)
    RadioButton fl;
    @Bind(R.id.tvcart)
    TextView tvcart;
    @Bind(R.id.gwc)
    RadioButton gwc;
    @Bind(R.id.me)
    RadioButton me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        initView();
        initFragment();
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .show(mNewGoodsFragment)
                .commit();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = xp;
        rbs[1] = jx;
        rbs[2] = fl;
        rbs[3] = gwc;
        rbs[4] = me;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.xp:
                index = 0;
                break;
            case R.id.jx:
                index = 1;
                break;
            case R.id.fl:
                index = 2;
                break;
            case R.id.gwc:
                index = 3;
                break;
            case R.id.me:
                index = 4;
                break;
        }
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        L.e("index=" + index);
        for (int i = 0; i < rbs.length; i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            } else {
                rbs[i].setChecked(false);
            }
        }
    }
}
