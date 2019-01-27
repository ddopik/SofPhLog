package com.example.softmills.phlog.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.album.view.ImageCommentActivityView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class ImageCommentActivityImpl implements ImageCommentActivityPresenter {

    private static final String SAVED = "Saved";
    private String TAG = ImageCommentActivityImpl.class.getSimpleName();
    private Context context;
    private ImageCommentActivityView imageCommentActivityView;

    public ImageCommentActivityImpl(Context context, ImageCommentActivityView imageCommentActivityView) {
        this.context = context;
        this.imageCommentActivityView = imageCommentActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getImageComments(String imageId, String page) {
        imageCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.getImageComments(imageId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data.comments);
                    imageCommentActivityView.viewAddCommentProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewAddCommentProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void submitComment(String imageId, String comment) {
        imageCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.submitImageComment(imageId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumImgCommentResponse -> {
                    imageCommentActivityView.viewMessage("Comment Submitted");
//                    imageCommentActivityView.viewPhotoComment(albumImgCommentResponse.data.comments);
                    imageCommentActivityView.viewAddCommentProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    imageCommentActivityView.viewAddCommentProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(String imageId) {
        imageCommentActivityView.viewAddCommentProgress(true);
        BaseNetworkApi.likePhoto(imageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    imageCommentActivityView.viewAddCommentProgress(false);
                    imageCommentActivityView.viewMessage("Like");
                }, throwable -> {
                    imageCommentActivityView.viewAddCommentProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public Observable<Boolean> savePhoto(BaseImage image) {
        return BaseNetworkApi.savePhoto(image.id)
                .map(savePhotoResponse -> savePhotoResponse != null && savePhotoResponse.getMsg().equals(SAVED));
    }

    @Override
    public Observable<Boolean> unSavePhoto(BaseImage image) {
        return BaseNetworkApi.unSavePhoto(image.id)
                .map(savePhotoResponse -> savePhotoResponse != null && savePhotoResponse.getMsg().equals(SAVED));
    }
}
