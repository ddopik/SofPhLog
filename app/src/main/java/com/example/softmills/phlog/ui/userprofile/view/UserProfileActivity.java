package com.example.softmills.phlog.ui.userprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity;
import com.example.softmills.phlog.ui.commentimage.view.ImageCommentActivity;
import com.example.softmills.phlog.ui.userprofile.presenter.UserProfilePresenter;
import com.example.softmills.phlog.ui.userprofile.presenter.UserProfilePresenterImpl;
import com.o_bdreldin.loadingbutton.LoadingButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.PhotosListType.USER_PROFILE_PHOTOS_LIST;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.ALL_ALBUM_IMAGES;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_NAME;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.LIST_TYPE;
import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;


public class UserProfileActivity extends BaseActivity implements UserProfileActivityView {


    private static final int ALBUM_LIST_REQUEST_CODE = 324;
    public static String USER_ID = "user_id";
    public static String USER_TYPE = "user_type";

    private String userID;

    private Toolbar userProfileToolBar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout userProfileCollapsingToolbarLayout;
    private Photographer currentPhotographer;
    private TextView userProfileLevel, userProfileUserName, userProfileFullName, userProfilePhotosCount, userProfileFolloweresCount, userProfileFollowingCount, userProfileToolbarTitle, userProfileFollowTitle;
    private RatingBar userProfileRating;
    private ImageView userProfileImg, coverImage;
    private CustomRecyclerView userProfilePhotosRv;
    private UserProfilePhotosAdapter userProfilePhotosAdapter;
    private UserProfilePresenter userProfilePresenter;
    private List<BaseImage> userPhotoList = new ArrayList<>();
    private ProgressBar userProfilePhotosProgressBar;
    private TextView placeHolder;
    private LoadingButton followUserBtn;
    private PagingController pagingController;
    private ImageButton backBtn;
    private String nextPageUrl = "1";
    private boolean isLoading;
    private NestedScrollView nestedScrollView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initPresenter();
        initView();
        initListener();

    }


    @Override
    public void initPresenter() {
        userProfilePresenter = new UserProfilePresenterImpl(getBaseContext(), this);
    }


    @Override
    public void initView() {

        if (getIntent().getStringExtra(USER_ID) != null) {

            this.userID = getIntent().getStringExtra(USER_ID);


            mAppBarLayout = findViewById(R.id.user_profile_appBar);
            userProfileCollapsingToolbarLayout = findViewById(R.id.user_profile_collapsing_layout);
            userProfileToolBar = findViewById(R.id.user_profile_toolbar);
            userProfileToolbarTitle = findViewById(R.id.user_profile_toolbar_title);
            userProfileFollowTitle = findViewById(R.id.tool_bar_follow_user);
            backBtn = findViewById(R.id.back_btn);
            userProfileLevel = findViewById(R.id.user_profile_level);
            userProfileRating = findViewById(R.id.profile_rating);
            userProfileImg = findViewById(R.id.user_profile_img);
            userProfileFullName = findViewById(R.id.user_profile_full_name);
            userProfileUserName = findViewById(R.id.user_profile_username);
            userProfilePhotosCount = findViewById(R.id.photos_val);
            userProfileFolloweresCount = findViewById(R.id.followers_val);
            userProfileFollowingCount = findViewById(R.id.following_val);
            userProfilePhotosRv = findViewById(R.id.user_profile_photos);
            userProfilePhotosProgressBar = findViewById(R.id.user_profile_photos_progress_bar);
            followUserBtn = findViewById(R.id.follow_user_btn);
            userProfilePhotosAdapter = new UserProfilePhotosAdapter(this, userPhotoList);

            userProfilePhotosRv.setAdapter(userProfilePhotosAdapter);
            userProfilePresenter.getUserProfileData(userID);
            coverImage = findViewById(R.id.user_cover_img);
            placeHolder = findViewById(R.id.place_holder);
            userProfilePresenter.getUserPhotos(userID, Integer.parseInt(nextPageUrl));

        }
    }


    private void initListener() {



        ////// initial block works by forcing then next Api for Each ScrollTop
        // cause recycler listener won't work until mainView ported with items
        userProfilePhotosRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) userProfilePhotosRv.getLayoutManager();

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (firstVisibleItemPosition == 0) {
                        if (nextPageUrl != null) {
                            Log.e(UserProfileActivity.class.getSimpleName(), "Page--->" + nextPageUrl);
                            userProfilePresenter.getUserPhotos(userID, Integer.parseInt(nextPageUrl));
                        }

                    }
                }
            }
        });

        ////////////////


        pagingController = new PagingController(userProfilePhotosRv) {
            @Override
            protected void loadMoreItems() {
                userProfilePresenter.getUserPhotos(userID, Integer.parseInt(nextPageUrl));
            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }


        };

        followUserBtn.setOnClickListener(v -> {
            followUser();

        });
        userProfileFollowTitle.setOnClickListener(v -> {
            followUser();
        });

        userProfilePhotosAdapter.photoAction = image -> {
            Intent intent = new Intent(this, AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, image.id);
            intent.putExtra(LIST_NAME, image.photographer.userName);
            intent.putExtra(LIST_TYPE, USER_PROFILE_PHOTOS_LIST);
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) userPhotoList);
            startActivityForResult(intent, ALBUM_LIST_REQUEST_CODE);
        };


        userProfileCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.black));
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
                    userProfileToolBar.setVisibility(View.VISIBLE);
                } else if (isShow) {
                    isShow = false;
                    userProfileToolBar.setVisibility(View.GONE);


                }
            }
        });


        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void viewUserData(Photographer photographer) {

        currentPhotographer = photographer;

        if (photographer.userName != null) {
            userProfileUserName.setText(String.format("@%1$s", photographer.userName));

        }

        if (photographer.fullName != null) {
            userProfileFullName.setText(photographer.fullName);
            userProfileToolbarTitle.setText(photographer.fullName);
        }

        if (photographer.level == null || photographer.level.isEmpty())
            userProfileLevel.setVisibility(View.INVISIBLE);
        else
            userProfileLevel.setText(photographer.level);

        if (photographer.photosCount != null)
            userProfilePhotosCount.setText(String.valueOf(photographer.photosCount));
        if (photographer.followersCount != null)
            userProfileFolloweresCount.setText(String.valueOf(photographer.followersCount));
        if (photographer.followingCount != null)
            userProfileFollowingCount.setText(String.valueOf(photographer.followingCount));

        if (photographer.isFollow) {
            followUserBtn.setText(getResources().getString(R.string.un_follow));
            userProfileFollowTitle.setText(getResources().getString(R.string.un_follow));
        } else {
            followUserBtn.setText(getResources().getString(R.string.follow));
            userProfileFollowTitle.setText(getResources().getString(R.string.follow));
        }


        GlideApp.with(userProfileImg)
                .load(photographer.imageProfile)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(userProfileImg);

        Glide.with(this)
                .load(photographer.imageCover)
                .apply(RequestOptions.placeholderOf(R.drawable.default_user_profile))
                .into(coverImage);

        userProfileRating.setRating(photographer.rate);



    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    @Override
    public void viewUserPhotos(List<BaseImage> userPhotoList) {
        this.userPhotoList.addAll(userPhotoList);
        userProfilePhotosAdapter.notifyDataSetChanged();


        if (this.userPhotoList.isEmpty())
            placeHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void viewUserFollowingState(boolean state) {
        followUserBtn.setLoading(false);
        if (state) {
            followUserBtn.setText(getResources().getString(R.string.un_follow));
            userProfileFollowTitle.setText(getResources().getString(R.string.un_follow));
            currentPhotographer.isFollow = true;
            if (currentPhotographer.followersCount != null)
                currentPhotographer.followersCount++;
        } else {
            followUserBtn.setText(getResources().getString(R.string.follow));
            userProfileFollowTitle.setText(getResources().getString(R.string.follow));
            currentPhotographer.isFollow = false;
            if (currentPhotographer.followersCount != null)
                currentPhotographer.followersCount--;
        }
        if (currentPhotographer.followersCount != null)
            userProfileFolloweresCount.setText(String.valueOf(currentPhotographer.followersCount));
    }

    @Override
    public void viewUserPhotosProgress(boolean state) {
        isLoading = state;
        if (state) {

            userProfilePhotosProgressBar.setVisibility(View.VISIBLE);

        } else {
            userProfilePhotosProgressBar.setVisibility(View.GONE);

        }
    }

    private void followUser() {
        followUserBtn.setLoading(true);
        if (currentPhotographer.isFollow) {
            userProfilePresenter.unFollowUser(String.valueOf(currentPhotographer.id));
        } else {
            userProfilePresenter.followUser(String.valueOf(currentPhotographer.id));
        }
    }

    @Override
    public void showMessage(String msg) {
        super.showToast(msg);
    }

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }

    private void setExpandEnabled(boolean enabled) {
        mAppBarLayout.setExpanded(enabled, false);
        mAppBarLayout.setActivated(enabled);
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) userProfileCollapsingToolbarLayout.getLayoutParams();
        if (enabled)
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        else
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        userProfileCollapsingToolbarLayout.setLayoutParams(params);
    }

    @Override
    public void showNotFoundDialog() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.photographer_not_available)
                .setPositiveButton(R.string.go_back, (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ALBUM_LIST_REQUEST_CODE && resultCode == RESULT_OK) {
            List<BaseImage> imageList = data.getParcelableArrayListExtra(AllAlbumImgActivity.ALL_ALBUM_IMAGES);
            if (imageList != null) {
                if (!imageList.isEmpty()) {
                    userPhotoList.clear();
                    userPhotoList.addAll(imageList);
                    userProfilePhotosAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
