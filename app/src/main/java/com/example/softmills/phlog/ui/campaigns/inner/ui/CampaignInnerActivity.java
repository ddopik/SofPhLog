package com.example.softmills.phlog.ui.campaigns.inner.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.ui.allphotos.view.AllPhotographerPhotosActivity;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenter;
import com.example.softmills.phlog.ui.campaigns.inner.presenter.CampaignInnerPresenterImpl;
import com.example.softmills.phlog.ui.dialog.dialog1.view.UploadCampaignPhotosDialog1Fragment;
import com.example.softmills.phlog.ui.uploadimage.view.UploadImageActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.CampaignStatus.CAMPAIGN_STATUS_APPROVED;
import static com.example.softmills.phlog.Utiltes.Constants.CampaignStatus.CAMPAIGN_STATUS_RUNNING;

/**
 * Created by abdalla_maged on 10/4/2018.
 */
public class
CampaignInnerActivity extends BaseActivity implements CampaignInnerActivityView {


    private final String TAG = CampaignInnerActivity.class.getSimpleName();
    public static String CAMPAIGN_ID = "campaign_id";
    private Toolbar campaignProfileToolBar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout campaignProfileCollapsingToolbarLayout;
    private ImageButton backBtn;
    private FrameLayout uploadButtonContainer;
    private String campaignId;
    private ImageView campaignImg;
    private Button uploadCampaignBtn;
    private TextView campaignTitle, campaignHostedBy, campaignDayLeft, campaignProfileToolbarTitle;
    private TabLayout campaignTabs;
    private ViewPager campaignViewPager;
    private CampaignInnerPresenter campaignInnerPresenter;
//    private OnMissionCampaignDataRecived onMissionCampaignDataRecived;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_innner);
        initPresenter();
        initView();
        initListener();

    }

    @Override
    public void initView() {

        mAppBarLayout = findViewById(R.id.campaign_profile_appBar);
        campaignProfileCollapsingToolbarLayout = findViewById(R.id.campaign_profile_collapsing_layout);
        campaignProfileToolBar = findViewById(R.id.campaign_profile_toolbar);
        campaignProfileToolbarTitle = findViewById(R.id.campaign_profile_toolbar_title);
        ;
        backBtn = findViewById(R.id.back_btn);
        campaignImg = findViewById(R.id.campaign_header_img);
        campaignTitle = findViewById(R.id.campaign_title);
        campaignHostedBy = findViewById(R.id.campaign_hosted_by);
        campaignDayLeft = findViewById(R.id.campaign_header_day_left);
        uploadCampaignBtn = findViewById(R.id.upload_campaign_photo_id);
        campaignTabs = findViewById(R.id.inner_campaign_tabs);
        campaignViewPager = findViewById(R.id.inner_campaign_viewpager);
        if (getIntent().getStringExtra(CAMPAIGN_ID) != null) {
            campaignInnerPresenter.getCampaignDetails(getIntent().getStringExtra(CAMPAIGN_ID));
            campaignId = getIntent().getStringExtra(CAMPAIGN_ID);
        }

        uploadButtonContainer = findViewById(R.id.upload_image_btn_container);

    }

    @Override
    public void initPresenter() {
        campaignInnerPresenter = new CampaignInnerPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {
        uploadCampaignBtn.setOnClickListener(v -> {
//            showPhotoDialog(this, "title", "Message");

            new UploadCampaignPhotosDialog1Fragment.Builder(this)
                    .title(R.string.upload_photo_for_campaign)
                    .message(R.string.select_option)
                    .option0(R.string.select_from_photos)
                    .option1(R.string.upload_new_photo)
                    .cancelable(true)
                    .optionConsumer((uploadCampaignPhotosDialog1Fragment, integer) -> {
                        switch (integer.intValue()) {
                            case 0:
                                Intent i2 = new Intent(this, AllPhotographerPhotosActivity.class);
                                i2.putExtra(AllPhotographerPhotosActivity.CAMPAIGN_ID, String.valueOf(campaignId));
                                startActivity(i2);
                                uploadCampaignPhotosDialog1Fragment.dismiss();
                                break;
                            case 1:
                                UploadImageData uploadImageData = new UploadImageData();
                                uploadImageData.setUploadImageType(Constants.UploadImageTypes.CAMPAIGN_IMG);
                                uploadImageData.setImageId(campaignId);
                                Bundle extras = new Bundle();
                                extras.putParcelable(UploadImageActivity.IMAGE_TYPE, uploadImageData);
                                Intent i1 = new Intent(this, UploadImageActivity.class);
                                i1.putExtras(extras);
                                startActivity(i1);
                                uploadCampaignPhotosDialog1Fragment.dismiss();
                                break;
                        }
                        return null;
                    }).show();
        });


        campaignProfileCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.black));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    campaignProfileToolBar.setVisibility(View.VISIBLE);
                } else if (isShow) {
                    isShow = false;
                    campaignProfileToolBar.setVisibility(View.GONE);
                }
            }
        });
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // required to yo update photo count when user uploads photos through (AllPhotographerPhotosActivity,UploadImageData)
        campaignInnerPresenter.getCampaignDetails(getIntent().getStringExtra(CAMPAIGN_ID));
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setCampaign(Campaign campaign) {
        GlideApp.with(this).load(campaign.imageCover)
                .error(R.drawable.splash_screen_background)
                .into(campaignImg);

        campaignTitle.setText(campaign.titleEn);
        campaignDayLeft.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getBaseContext(), R.drawable.ic_time_white), null, null, null);
        campaignProfileToolbarTitle.setText(campaign.titleEn);

        campaignDayLeft.setText(new StringBuilder().append(campaign.daysLeft).append(" ").append(getString(R.string.days_left_value)).toString());

        campaignHostedBy.setText(getString(R.string.hosted_by_value, campaign.business.fullName));

        // initializing the pager fragment

        InnerCampaignFragmentPagerAdapter innerCampaignFragmentPagerAdapter = new InnerCampaignFragmentPagerAdapter(getSupportFragmentManager()
                , getFragmentPagerFragment(campaign)
                , getFragmentTitles(campaign.status, campaign.photosCount));
        campaignViewPager.setAdapter(innerCampaignFragmentPagerAdapter);
        campaignTabs.setupWithViewPager(campaignViewPager);

        if (campaign.status == CAMPAIGN_STATUS_APPROVED || campaign.status == CAMPAIGN_STATUS_RUNNING) {
            uploadButtonContainer.setVisibility(View.VISIBLE);
        } else {
            uploadButtonContainer.setVisibility(View.GONE);
        }
    }

    private List<Fragment> getFragmentPagerFragment(Campaign campaign) {
        int status = campaign.status;
        List<Fragment> fragmentList = new ArrayList<>();
        CampaignInnerMissionFragment campaignInnerMissionFragment = CampaignInnerMissionFragment.getInstance(campaign);
        fragmentList.add(campaignInnerMissionFragment);
        fragmentList.add(CampaignInnerPhotosFragment.getInstance(campaignId, status != CAMPAIGN_STATUS_APPROVED && status != CAMPAIGN_STATUS_RUNNING));
        return fragmentList;
    }

    private List<String> getFragmentTitles(int status, int photosCount) {
        List<String> fragmentList = new ArrayList<String>();
        fragmentList.add(getResources().getString(R.string.details));
        fragmentList.add(photosCount + " " + getResources().getString(R.string.tab_photos));
        return fragmentList;
    }



    @Override
    public void showNotFoundDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.campaign_not_available)
                .setPositiveButton(R.string.go_back, (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                }).show();
    }
}
