package com.example.softmills.phlog.ui.welcome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.bikomobile.circleindicatorpager.CircleIndicatorPager;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.login.view.LoginActivity;
import com.example.softmills.phlog.ui.signup.view.SignUpActivity;
import com.example.softmills.phlog.ui.welcome.model.InitSlider;
import com.example.softmills.phlog.ui.welcome.presenter.WelcomePresenter;
import com.example.softmills.phlog.ui.welcome.presenter.WelcomeScreenImpl;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements WelcomeView {


    private static final String TAG = WelcomeActivity.class.getSimpleName();


    private PagerAdapter WelcomeSlideAdapter;
    private List<InitSlider> urlList = new ArrayList<InitSlider>();
    private WelcomePresenter welcomePresenter;
    private Button signInBtn, signUpBtn;
    private CircleIndicatorPager indicator;
    private ViewPager slidesViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initListener();
        initPresenter();
        welcomePresenter.getWelcomeSlidesImages();
    }

    @Override
    public void initView() {


        signInBtn = findViewById(R.id.sign_in_btn);
        signUpBtn = findViewById(R.id.sign_up_btn);

        InitSlider initSliderDefaultItem = new InitSlider();
        initSliderDefaultItem.image = "";
        urlList.add(initSliderDefaultItem);
        WelcomeSlideAdapter = new WelcomeSlideAdapter(getBaseContext(), urlList);
        slidesViewPager = findViewById(R.id.slides_view_pager);
        slidesViewPager.setAdapter(WelcomeSlideAdapter);
        indicator = findViewById(R.id.circle_indicator_pager);


    }


    @Override
    public void initPresenter() {
        welcomePresenter = new WelcomeScreenImpl(this,this);
    }

    private void initListener() {
        signInBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        signUpBtn.setOnClickListener((view) -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void showWelcomeImageSlider(List<InitSlider> images) {
        if (images.size() >0){
            urlList.clear();
            InitSlider initSliderDefaultItem=new InitSlider();
            urlList.add(initSliderDefaultItem);
            urlList.addAll(images);
            WelcomeSlideAdapter.notifyDataSetChanged();
            indicator.setViewPager(slidesViewPager);
        }

    }


}
