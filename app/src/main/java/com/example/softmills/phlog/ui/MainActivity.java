package com.example.softmills.phlog.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.campaigns.CampaignsFragment;
import com.example.softmills.phlog.ui.home.view.HomeFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.PhotoGraphedProfileFragment;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private BottomAppBar bottomNavigation;
    private Button homeBrn, campaignBtn, notificationBtn, myProfileBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initPresenter();
        initListener();
        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new HomeFragment()).commit(); //default Screen
    }

    @Override
    public void initView() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        homeBrn = findViewById(R.id.navigation_home);
        campaignBtn = findViewById(R.id.navigation_missions);
        myProfileBtn = findViewById(R.id.navigation_profile);
        notificationBtn = findViewById(R.id.navigation_notification);

    }

    @Override
    public void initPresenter() {
        bottomNavigation.setOnClickListener(this);
        homeBrn.setOnClickListener(this);
        campaignBtn.setOnClickListener(this);
        myProfileBtn.setOnClickListener(this);
        notificationBtn.setOnClickListener(this);
    }

    private void initListener() {


    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void onClick(View v) {
        clearSelected();
        int homeBrnImg = R.drawable.ic_tab_home;
        int campaignBtnImg = R.drawable.ic_tab_missions;
        int notificationBtnImg = R.drawable.ic_tab_notificatin;
        int myProfileBtnImg = R.drawable.ic_tab_profile;

        switch (v.getId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new HomeFragment()).commit();
                homeBrn.setTextColor(getResources().getColor(R.color.text_input_color));
                homeBrn.setCompoundDrawablesWithIntrinsicBounds(0, homeBrnImg, 0, 0);
                homeBrn.setCompoundDrawablePadding(8);
                break;
            case R.id.navigation_missions:
                getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new CampaignsFragment()).commit();
                campaignBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                campaignBtn.setCompoundDrawablesWithIntrinsicBounds(0, campaignBtnImg, 0, 0);
                campaignBtn.setCompoundDrawablePadding(8);
                break;
            case R.id.navigation_notification:
                notificationBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                notificationBtn.setCompoundDrawablesWithIntrinsicBounds(0, notificationBtnImg, 0, 0);
                notificationBtn.setCompoundDrawablePadding(8);
                break;
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new PhotoGraphedProfileFragment()).commit();
                myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(0, myProfileBtnImg, 0, 0);
                myProfileBtn.setCompoundDrawablePadding(8);
                break;
            default:
        }
    }

    private void clearSelected() {

        int homeBrnImg_off = R.drawable.ic_tab_home_off;
        int campaignBtnImg_off = R.drawable.ic_tab_home_off;
        int notificationBtnImg_off = R.drawable.ic_tab_notificatin_off;
        int myProfileBtnImg_off = R.drawable.ic_tab_profile_off;

        homeBrn.setTextColor(getResources().getColor(R.color.gray677078));
        homeBrn.setCompoundDrawablesWithIntrinsicBounds(0, homeBrnImg_off, 0, 0);
        homeBrn.setCompoundDrawablePadding(8);

        campaignBtn.setTextColor(getResources().getColor(R.color.gray677078));
        campaignBtn.setCompoundDrawablesWithIntrinsicBounds(0, campaignBtnImg_off, 0, 0);
        campaignBtn.setCompoundDrawablePadding(8);

        notificationBtn.setTextColor(getResources().getColor(R.color.gray677078));
        notificationBtn.setCompoundDrawablesWithIntrinsicBounds(0, notificationBtnImg_off, 0, 0);
        notificationBtn.setCompoundDrawablePadding(8);

        myProfileBtn.setTextColor(getResources().getColor(R.color.gray677078));
        myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(0, myProfileBtnImg_off, 0, 0);
        myProfileBtn.setCompoundDrawablePadding(8);


//        homeBrn.setFocusableInTouchMode(false);
//        campaignBtn.setFocusableInTouchMode(false);
//        notificationBtn.setFocusableInTouchMode(false);
//        myProfileBtn.setFocusableInTouchMode(false);

    }
}
