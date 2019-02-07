package com.example.softmills.phlog.ui.album.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.widgets.CustomRecyclerView;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.base.widgets.PagingController;
import com.example.softmills.phlog.ui.album.model.AlbumGroup;
import com.example.softmills.phlog.ui.album.model.AlbumPreviewResponseData;
import com.example.softmills.phlog.ui.album.presenter.AlbumPreviewActivityPresenter;
import com.example.softmills.phlog.ui.album.presenter.AlbumPreviewActivityPresenterImpl;
import com.example.softmills.phlog.ui.album.view.adapter.AlbumAdapter;
import com.example.softmills.phlog.ui.commentimage.view.ImageCommentActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.ui.album.view.AllAlbumImgActivity.SELECTED_IMG_ID;

/**
 * Created by abdalla_maged on 11/4/2018.
 */
public class AlbumPreviewActivity extends BaseActivity implements AlbumPreviewActivityView {


    public static final String ALBUM_PREVIEW_ID = "album_preview_id";
    private int albumID;
    private List<AlbumGroup> albumGroupList = new ArrayList<>();
    private List<BaseImage> imageList = new ArrayList<>();
    private AlbumAdapter albumAdapter;
    private ImageView albumPreviewImg;
    private ProgressBar albumPreviewProgress;
    private CustomRecyclerView albumRv;
    private CustomTextView albumNameTV, followingNumber, albumToolBarTitle;
    private PagingController pagingController;
    private AlbumPreviewActivityPresenter albumPreviewActivityPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_preview);
        if (getIntent().getIntExtra(ALBUM_PREVIEW_ID, 0) != 0) {
            albumID = getIntent().getIntExtra(ALBUM_PREVIEW_ID, 0);
            initPresenter();
            initView();
            initListener();
        }

    }


    @Override
    public void initView() {
        albumToolBarTitle = findViewById(R.id.toolbar_title);
        albumPreviewImg = findViewById(R.id.album_preview_img);
        albumRv = findViewById(R.id.album_rv);
        albumPreviewProgress = findViewById(R.id.user_profile_progress_bar);
        albumNameTV = findViewById(R.id.album_name_text_view);
        followingNumber = findViewById(R.id.following_number_text_view);
        // Set adapter object.
        albumAdapter = new AlbumAdapter(getBaseContext(), albumGroupList);
        albumRv.setAdapter(albumAdapter);
        albumPreviewActivityPresenter.getSelectedSearchAlbum(albumID, "0");
        albumPreviewActivityPresenter.getAlbumPreviewImages(albumID, 0);


    }

    @Override
    public void initPresenter() {
        albumPreviewActivityPresenter = new AlbumPreviewActivityPresenterImpl(getBaseContext(), this);
    }

    private void initListener() {


        pagingController = new PagingController(albumRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                albumPreviewActivityPresenter.getAlbumPreviewImages(albumID, page);
            }
        };

        albumAdapter.onAlbumImageClicked = albumImg -> {
//            Intent intent = new Intent(this, Image.class);
//            intent.putExtra(AllAlbumImgActivity.ALBUM_ID, albumID);
//            intent.putExtra(AllAlbumImgActivity.ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) imageList);
//            intent.putExtra(SELECTED_IMG_ID, albumImg.id);
//            startActivity(intent);

            Intent intent = new Intent(this, AllAlbumImgActivity.class);
            intent.putExtra(AllAlbumImgActivity.ALBUM_ID, albumID);
            intent.putExtra(AllAlbumImgActivity.ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) imageList);
            intent.putExtra(SELECTED_IMG_ID, albumImg.id);
            startActivity(intent);

        };
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    @Override
    public void viwAlbumPreviewImages(List<BaseImage> baseImageList) {

        if (baseImageList.size() > 0) {

            imageList.addAll(baseImageList);
            for (int i = 0; i < baseImageList.size(); i++) {

                if (albumGroupList.size() == 0) {
                    AlbumGroup albumGroup = new AlbumGroup();
                    albumGroup.albumGroupList.add(baseImageList.get(i));
                    albumGroupList.add(albumGroup);
                } else if (albumGroupList.get(albumGroupList.size() - 1).albumGroupList.size() < 4) {
                    albumGroupList.get(albumGroupList.size() - 1).albumGroupList.add(baseImageList.get(i));
                } else {
                    AlbumGroup albumGroup = new AlbumGroup();
                    albumGroup.albumGroupList.add(baseImageList.get(i));
                    albumGroupList.add(albumGroup);
                }


            }


            albumAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void viewAlbumPreviewProgress(boolean state) {
        if (state) {
            albumPreviewProgress.setVisibility(View.VISIBLE);

        } else {
            albumPreviewProgress.setVisibility(View.GONE);

        }

    }

    @Override
    public void viewAlumPreview(AlbumPreviewResponseData albumPreviewResponseData) {

        albumToolBarTitle.setText(albumPreviewResponseData.name);
        GlideApp.with(this)
                .load(albumPreviewResponseData.preview)
                .placeholder(R.drawable.default_photographer_profile)
                .error(R.drawable.default_photographer_profile)
                .into(albumPreviewImg);
        albumNameTV.setText(albumPreviewResponseData.name);

    }
}

