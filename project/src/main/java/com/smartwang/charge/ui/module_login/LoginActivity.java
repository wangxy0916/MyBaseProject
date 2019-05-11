package com.smartwang.charge.ui.module_login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartwang.charge.config.Constant;
import com.smartwang.charge.utils.SharedPreferencesUtil;
import com.smartwang.charge.utils.ToastUtil;
import com.smartwang.project.R;
import com.smartwang.charge.base.BaseActivity;
import com.smartwang.charge.ui.activity.HtmlActivity;
import com.smartwang.charge.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_login_login)
    Button btnLoginLogin;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.tv_login_protocol)
    TextView tvLoginProtocol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }


    private void initView(){
        getRelativeNavbar().setVisibility(View.GONE);
        SharedPreferencesUtil.putBoolean(Constant.SHARE_ISFIRST, false);
    }

    @OnClick({R.id.btn_login_login, R.id.tv_login_forget, R.id.tv_login_register, R.id.tv_login_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                SharedPreferencesUtil.putBoolean(Constant.SHARE_ISLOGIN, true);
                startActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_login_forget:
                break;
            case R.id.tv_login_register:
                break;
            case R.id.tv_login_protocol:
                Bundle bundle = new Bundle();
                bundle.putString(HtmlActivity.PARAM_URL, "https://www.baidu.com/");
                bundle.putString(HtmlActivity.PARAM_TITLE, "协议");
                bundle.putInt(HtmlActivity.PARAM_MODE, 1);
                startActivity(HtmlActivity.class, bundle);
                startActivity(HtmlActivity.class);
                break;
        }
    }
}
