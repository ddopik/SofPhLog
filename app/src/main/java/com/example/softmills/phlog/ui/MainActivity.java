package com.example.softmills.phlog.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.Constants.MainActivityRedirectionValue;
import com.example.softmills.phlog.Utiltes.Constants.PopupType;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.app.PhLogApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.fgm.model.FirebaseNotificationData;
import com.example.softmills.phlog.fgm.parse.NotificationParser;
import com.example.softmills.phlog.ui.campaigns.CampaignsFragment;
import com.example.softmills.phlog.ui.earning.view.EarningInnerFragment;
import com.example.softmills.phlog.ui.earning.view.EarningListFragment;
import com.example.softmills.phlog.ui.notification.model.NotificationList;
import com.example.softmills.phlog.ui.notification.view.NotificationFragment;
import com.example.softmills.phlog.ui.photographerprofile.editprofile.view.EditPhotoGrapherProfileFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.PhotoGraphedProfileFragment;
import com.example.softmills.phlog.ui.social.view.SocialFragment;
import com.example.softmills.phlog.ui.splash.view.SplashActivity;
import com.example.softmills.phlog.ui.uploadimage.view.GalleryImageFragment;

import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.CAMPAIGN;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EARNING_INNER;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EARNING_LIST;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EDIT_PROFILE;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.HOME;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.NOTIFICATION;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.PROFILE;
import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.UPLOAD_PHOTO;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView toolBarTitle;
    private BottomAppBar bottomNavigation;
    private Button homeBrn, campaignBtn, notificationBtn, myProfileBtn;
    private FloatingActionButton picImgHomeBtn;
    private Toolbar toolbar;
    private ImageButton backBtn;
    public NavigationManger navigationManger;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.phlog_toolbar);
        super.setSupportActionBar(toolbar);
        navigationManger = new NavigationManger();

        initPresenter();
        initView();
        initListener();

        new Utilities().printHashKey(this);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent.hasExtra(MainActivityRedirectionValue.VALUE)) {
            int redirection = intent.getIntExtra(MainActivityRedirectionValue.VALUE, 0);
            switch (redirection) {
                case MainActivityRedirectionValue.TO_PROFILE:
                    navigationManger.navigate(PROFILE);
                    break;
                case MainActivityRedirectionValue.TO_POPUP:
                    String payload = intent.getStringExtra(MainActivityRedirectionValue.PAYLOAD);
                    NotificationList data = NotificationParser.parse(payload);
                    navigationManger.navigate(HOME);
                    if (data != null && data.popup != PopupType.NONE)
                        showPopup(data);
                    break;
            }
        } else {
            navigationManger.navigate(HOME);
        }
    }

    @Override
    public void initView() {

        toolBarTitle = findViewById(R.id.toolbar_title);
        backBtn = findViewById(R.id.back_btn);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        homeBrn = findViewById(R.id.navigation_home);
        campaignBtn = findViewById(R.id.navigation_missions);
        myProfileBtn = findViewById(R.id.navigation_profile);
        notificationBtn = findViewById(R.id.navigation_notification);
        picImgHomeBtn = findViewById(R.id.pic_img_home_btn);

    }

    @Override
    public void initPresenter() {
        ///set NetWork Call Header
        PhLogApp.initFastAndroidNetworking(PrefUtils.getUserToken(getBaseContext()), "0", " en-us'", getBaseContext());
    }

    private void initListener() {
        bottomNavigation.setOnClickListener(this);
        homeBrn.setOnClickListener(this);
        campaignBtn.setOnClickListener(this);
        myProfileBtn.setOnClickListener(this);
        notificationBtn.setOnClickListener(this);
        picImgHomeBtn.setOnClickListener(this);

        backBtn.setOnClickListener((view) -> {
//            navigate(Constants.NavigationHelper.HOME);
            onBackPressed();
        });
    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.navigation_home:
                navigationManger.navigate(HOME);
                break;
            case R.id.navigation_missions:
                navigationManger.navigate(CAMPAIGN);
                break;
            case R.id.navigation_notification:
                navigationManger.navigate(NOTIFICATION);
                break;
            case R.id.navigation_profile:
                navigationManger.navigate(PROFILE);
                break;
            case R.id.pic_img_home_btn:
                navigationManger.navigate(UPLOAD_PHOTO);
                break;
            default:
        }
    }

    ////////
    public class NavigationManger {
        private String extraData;
        private Constants.NavigationHelper currentTab;

        private void clearSelected() {

            int homeBrnImg_off = R.drawable.ic_social_off;
            int campaignBtnImg_off = R.drawable.ic_campaigns_off;
            int notificationBtnImg_off = R.drawable.ic_notification_off;
            int myProfileBtnImg_off = R.drawable.ic_profile_off;

            homeBrn.setTextColor(getResources().getColor(R.color.gray677078));
            homeBrn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), homeBrnImg_off), null, null);
            homeBrn.setCompoundDrawablePadding(8);

            campaignBtn.setTextColor(getResources().getColor(R.color.gray677078));
            campaignBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), campaignBtnImg_off), null, null);
            campaignBtn.setCompoundDrawablePadding(8);

            notificationBtn.setTextColor(getResources().getColor(R.color.gray677078));
            notificationBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), notificationBtnImg_off), null, null);
            notificationBtn.setCompoundDrawablePadding(8);

            myProfileBtn.setTextColor(getResources().getColor(R.color.gray677078));
            myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg_off), null, null);
            myProfileBtn.setCompoundDrawablePadding(8);


            picImgHomeBtn.setImageResource(R.drawable.btn_upload_img);


        }

        public void navigate(Constants.NavigationHelper navigationHelper) {
            clearSelected();
            int homeBrnImg = R.drawable.ic_social_on;
            int campaignBtnImg = R.drawable.ic_campaigns_on;
            int notificationBtnImg = R.drawable.ic_notification_on;
            int myProfileBtnImg = R.drawable.ic_profile_on;

            backBtn.setVisibility(View.VISIBLE);

            switch (navigationHelper) {
                case HOME: {
                    addFragment(R.id.view_container, new SocialFragment(), SocialFragment.class.getSimpleName(), false);
                    homeBrn.setTextColor(getResources().getColor(R.color.text_input_color));
                    homeBrn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), homeBrnImg), null, null);
                    homeBrn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    currentTab = HOME;
                    break;
                }

                case CAMPAIGN: {
                    addFragment(R.id.view_container, new CampaignsFragment(), CampaignsFragment.class.getSimpleName(), false);
                    campaignBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    campaignBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), campaignBtnImg), null, null);
                    campaignBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText(getResources().getString(R.string.campaigns));
                    backBtn.setVisibility(View.INVISIBLE);
                    currentTab = CAMPAIGN;
                    break;
                }
                case NOTIFICATION: {

                    addFragment(R.id.view_container, new NotificationFragment(), NotificationFragment.class.getSimpleName(), false);
                    notificationBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    notificationBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), notificationBtnImg), null, null);
                    notificationBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.VISIBLE);
                    backBtn.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.notification));
                    currentTab = NOTIFICATION;
                    break;
                }
                case UPLOAD_PHOTO: {

                    UploadImageData imageType = new UploadImageData();
                    imageType.setUploadImageType(Constants.UploadImageTypes.NORMAL_IMG);
                    GalleryImageFragment galleryImageFragment = GalleryImageFragment.getInstance(imageType);
                    addFragment(R.id.view_container, galleryImageFragment, GalleryImageFragment.class.getSimpleName(), false);
                    picImgHomeBtn.setImageResource(R.drawable.ic_upload_photo_selected);
                    toolbar.setVisibility(View.VISIBLE);
                    toolBarTitle.setText("");
                    backBtn.setVisibility(View.INVISIBLE);
                    currentTab = UPLOAD_PHOTO;
                    break;
                }
                case PROFILE: {
                    toolBarTitle.setText(getResources().getString(R.string.profile));
                    toolbar.setVisibility(View.GONE);
                    addFragment(R.id.view_container, new PhotoGraphedProfileFragment(), PhotoGraphedProfileFragment.class.getSimpleName(), false);
                    myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg), null, null);
                    myProfileBtn.setCompoundDrawablePadding(8);
                    currentTab = PROFILE;
                    break;
                }
                case EARNING_LIST: {
                    addFragment(R.id.view_container, new EarningListFragment(), EarningListFragment.class.getSimpleName(), true);
                    myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg), null, null);
                    myProfileBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.profile));
                    currentTab = EARNING_LIST;
                    break;
                }
                case EARNING_INNER: {
                    if (extraData != null) {
                        EarningInnerFragment earningInnerFragment = EarningInnerFragment.getInstance(extraData);
                        addFragment(R.id.view_container, earningInnerFragment, EarningInnerFragment.class.getSimpleName(), true);
                        myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                        myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg), null, null);
                        myProfileBtn.setCompoundDrawablePadding(8);
                        toolbar.setVisibility(View.VISIBLE);
                        toolBarTitle.setText(getResources().getString(R.string.earning_details));
                        extraData = null;
                        currentTab = EARNING_INNER;
                    }

                    break;
                }
                case EDIT_PROFILE: {
                    addFragment(R.id.view_container, EditPhotoGrapherProfileFragment.getInstance(), EditPhotoGrapherProfileFragment.class.getSimpleName(), true);
                    myProfileBtn.setTextColor(getResources().getColor(R.color.text_input_color));
                    myProfileBtn.setCompoundDrawablesWithIntrinsicBounds(null, AppCompatResources.getDrawable(getBaseContext(), myProfileBtnImg), null, null);
                    myProfileBtn.setCompoundDrawablePadding(8);
                    toolbar.setVisibility(View.GONE);
                    toolBarTitle.setText(getResources().getString(R.string.profile));
                    currentTab = EDIT_PROFILE;
                    break;
                }
                case LOGOUT: {
                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                }
            }

        }

        public void setExtraData(String extraData) {
            this.extraData = extraData;
        }

        Constants.NavigationHelper getCurrentTab() {
            return currentTab;
        }

    }


    ///////

    @Override
    public void onBackPressed() {

        switch (navigationManger.getCurrentTab()) {
            case HOME: {
                finish();
                break;
            }
            case CAMPAIGN: {
                navigationManger.navigate(HOME);
                break;
            }
            case NOTIFICATION: {
                navigationManger.navigate(HOME);
                break;
            }
            case PROFILE: {
                navigationManger.navigate(HOME);
                break;
            }
            case EDIT_PROFILE: {
                navigationManger.navigate(PROFILE);
                break;
            }
            case UPLOAD_PHOTO: {
                navigationManger.navigate(HOME);
                break;
            }
            default: {
                super.onBackPressed();
            }


        }


    }
}
