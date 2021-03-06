package com.example.softmills.phlog.ui.uploadimage.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.commonmodel.Tag;
import com.example.softmills.phlog.base.commonmodel.UploadImageData;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.uploadimage.view.AddTagActivityView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AddTagActivityPresenterImpl implements AddTagActivityPresenter {
    private String TAG = AddTagActivityPresenterImpl.class.getSimpleName();
    private Context context;
    private AddTagActivityView addTagActivityView;

    public AddTagActivityPresenterImpl(Context context, AddTagActivityView addTagActivityView) {

        this.context = context;
        this.addTagActivityView = addTagActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadPhoto(String imagePath, String imageCaption, String location, String draftState, UploadImageData uploadImageData, List<Tag> tagList) {
        addTagActivityView.viewUploadProgress(true);
        HashMap<String, String> tagsSelected = new HashMap<String, String>();
        for (int i = 0; i < tagList.size(); i++) {
            tagsSelected.put("tags[" + i + "]", tagList.get(i).name);
        }

//        switch (uploadImageData.getUploadImageType()) {
//            case NORMAL_IMG: {
//                //user is uploading Normal Photo
//                addTagActivityView.viewUploadProgress(true);
//                BaseNetworkApi.uploadPhotoGrapherPhoto(PrefUtils.getUserToken(context), imageCaption, location, new File(imagePath), tagsSelected, new UploadProgressListener() {
//                    @Override
//                    public void onProgress(long bytesUploaded, long totalBytes) {
//                    }
//                })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(uploadImgResponse -> {
//                            addTagActivityView.viewUploadProgress(false);
//                            addTagActivityView.viewMessage(context.getResources().getString(R.string.photo_uploaded));
//                        }, throwable -> {
//                            ErrorUtils.Companion.setError(context, TAG, throwable);
//                            addTagActivityView.viewUploadProgress(false);
//                        });
//                break;
//            }
//            case CAMPAIGN_IMG: {
//
//                //            --->user Uploading CampaignPhoto
//                addTagActivityView.viewUploadProgress(true);
//                BaseNetworkApi.uploadCampaignPhoto(PrefUtils.getUserToken(context), imageCaption, location, new File(imagePath), tagsSelected, uploadImageData.getImageId())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(uploadImgResponse -> {
//                            addTagActivityView.viewUploadProgress(false);
//                            addTagActivityView.viewMessage(context.getResources().getString(R.string.photo_uploaded));
//                        }, throwable -> {
//                            ErrorUtils.Companion.setError(context, TAG, throwable);
//                            addTagActivityView.viewUploadProgress(false);
//                        });
//                break;
//            }
//        }


    }


    @SuppressLint("CheckResult")
    @Override
    public void getAutoCompleteTags(String key) {
        BaseNetworkApi.getTagsAutoComplete(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(autoCompleteTagResponse -> {
                    addTagActivityView.updateTagsList(autoCompleteTagResponse.data);
                },throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);

                });
    }


}
