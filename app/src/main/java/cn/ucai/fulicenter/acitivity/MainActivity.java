package cn.ucai.fulicenter.acitivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.fragment.CategoryFragment;
import cn.ucai.fulicenter.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;

public class MainActivity extends BaseActivity {

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

    int index;
    int currentIndex;
    RadioButton[] rbs;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mFragments[0] = mNewGoodsFragment;
        mFragments[1] = mBoutiqueFragment;
        mFragments[2] = mCategoryFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .add(R.id.fragment_container,mCategoryFragment)
                .hide(mBoutiqueFragment)//隐藏
                .hide(mCategoryFragment)
                .show(mNewGoodsFragment)
                .commit();
    }
 @Override
    protected void initView() {
        rbs = new RadioButton[5];
        rbs[0] = xp;
        rbs[1] = jx;
        rbs[2] = fl;
        rbs[3] = gwc;
        rbs[4] = me;
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

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
        setFragment();
    }

    private void setFragment() {
        if (index != currentIndex){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mFragments[currentIndex]);
        if (!mFragments[index].isAdded()){//取反
            ft.add(R.id.fragment_container,mFragments[index]);
        }
            ft.show(mFragments[index]).commit();//显示选中的页面，隐藏之前的页面
        }
        setRadioButtonStatus();
        currentIndex = index;//从新赋值
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
    public void onBackPressed(){
        finish();
    }
}
