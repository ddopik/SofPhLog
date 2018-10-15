package com.example.softmills.phlog.network;

import com.androidnetworking.common.Priority;
import com.example.softmills.phlog.ui.campaigns.inner.model.CampaignInnerPhotosResponse;
import com.example.softmills.phlog.ui.campaigns.inner.model.CampaignInnerResponse;
import com.example.softmills.phlog.ui.campaigns.model.CampaignResponse;
import com.example.softmills.phlog.ui.login.model.LoginResponse;
import com.example.softmills.phlog.ui.login.model.SocialLoginResponse;
import com.example.softmills.phlog.ui.photographerprofile.model.ProfilePhotoGrapherInfoResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.model.PhotoGrapherCampaignResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.model.PhotoGrapherFollowingInResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.model.PhotoGrapherPhotosResponse;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.model.PhotoGrapherSavedPhotosResponse;
import com.example.softmills.phlog.ui.signup.model.AllCountersRepose;
import com.example.softmills.phlog.ui.signup.model.SignUpResponse;
import com.example.softmills.phlog.ui.userprofile.model.UserPhotosResponse;
import com.example.softmills.phlog.ui.userprofile.model.UserProfileResponse;
import com.example.softmills.phlog.ui.welcome.model.WelcomeScreenResponse;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;


/**
 * Created by abdalla-maged on 3/29/18.
 */

public class BaseNetworkApi {


    //Network Status
    public static String STATUS_OK = "200";
    public static String STATUS_IN_VALID_RESPONSE = "401";
    public static String NEW_FACEBOOK_USER_STATUS = "0";
    //
    private static final String BASE_URL = "http://178.128.162.10/public/api/photographer";
    private static final String WELCOME_SLIDES_IMAGES = BASE_URL + "/photographer/init_slider";
    private static final String ALL_COUNTRES = BASE_URL + "/countires";
    private static final String SIGNUP_USER = BASE_URL + "/signup";
    private static final String NORMAL_LOGIN = BASE_URL + "/login";
    private static final String FACEBOOK_LOGIN_URL = BASE_URL + "/signup";
    private static final String USER_PROFILE_URL = BASE_URL + "/get_info_photographer";
    private static final String PHOTOGRAPHER_SAVED_PHOTO_URL = BASE_URL + "/image_photographer";
    private static final String PHOTOGRAPHER_ALL_PHOTO_URL = BASE_URL + "/image_photographer";
    private static final String PHOTOGRAPHER_PROFILE_INFO = BASE_URL + "/get_info_photographer";
    private static final String PHOTOGRAPHER_ALL_CAMPAIGN_URL = BASE_URL + "/get_photographer_campaign";
    private static final String PHOTOGRAPHER_PHOTOGRAPHER_FOLLOWING_IN_URL = BASE_URL + "/get_following";
    private static final String ALL_CAMPAIGN_URL = BASE_URL + "/get_all_campaigns_running";
    private static final String CAMPAIGN_DETAILS_URL = BASE_URL + "/detail_one_campaign";
    private static final String CAMPAIGN_PHOTOS_URL = BASE_URL + "/get_photos_campaign";
    private static final String USER_PROFILE_PHOTOS = BASE_URL + "/image_photographer";


    //Path Parameters
    private static final String PAGER_PATH_PARAMETER = "page";


    //Body Parameters
    private static final String TOKEN_BODY_PARAMETER = "token";


    public static io.reactivex.Observable<WelcomeScreenResponse> getWelcomeSlidesImages() {
        return Rx2AndroidNetworking.get(WELCOME_SLIDES_IMAGES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(WelcomeScreenResponse.class);


    }

    public static io.reactivex.Observable<AllCountersRepose> getAllCounters() {
        return Rx2AndroidNetworking.get(ALL_COUNTRES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AllCountersRepose.class);
    }

    public static io.reactivex.Observable<SignUpResponse> signUpUser(HashMap<String, String> signUpData) {
        return Rx2AndroidNetworking.post(SIGNUP_USER)
                .addBodyParameter("full_name", signUpData.get("full_name"))
                .addBodyParameter("password", signUpData.get("password"))
                .addBodyParameter("email", signUpData.get("email"))

                .addBodyParameter("mobile", signUpData.get("mobile"))
                .addBodyParameter("country_id", signUpData.get("country_id"))

                .addBodyParameter("mobile_os", signUpData.get("mobile_os"))
                .addBodyParameter("mobile_model", signUpData.get("mobile_model"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SignUpResponse.class);
    }

    public static io.reactivex.Observable<LoginResponse> LoginUserNormal(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(NORMAL_LOGIN)
                .addBodyParameter("email", loginData.get("email"))
                .addBodyParameter("password", loginData.get("password"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    public static io.reactivex.Observable<SocialLoginResponse> socialLoginFacebook(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(FACEBOOK_LOGIN_URL)
                .addBodyParameter("facebook_id", loginData.get("facebook_id"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialLoginResponse.class);
    }


    public static io.reactivex.Observable<UserProfileResponse> getUserProfile(String token,String userID) {
        return Rx2AndroidNetworking.post(USER_PROFILE_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("photographer_id ", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UserProfileResponse.class);
    }


    public static io.reactivex.Observable<PhotoGrapherSavedPhotosResponse> getPhotoGrapherSavedPhotos(String token, int pageNumber) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_SAVED_PHOTO_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(pageNumber))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherSavedPhotosResponse.class);
    }


    public static io.reactivex.Observable<PhotoGrapherPhotosResponse> getPhotoGrapherPhotos(String token, int pageNumber) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_ALL_PHOTO_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(pageNumber))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherPhotosResponse.class);
    }

    public static io.reactivex.Observable<PhotoGrapherCampaignResponse> getPhotoGrapherProfileCampaign(String token, int pageNumber) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_ALL_CAMPAIGN_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(pageNumber))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherCampaignResponse.class);
    }

    public static io.reactivex.Observable<ProfilePhotoGrapherInfoResponse> getProfileInfo(String token) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_PROFILE_INFO)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ProfilePhotoGrapherInfoResponse.class);
    }


    public static io.reactivex.Observable<PhotoGrapherFollowingInResponse> getPhotoGrapherProfileFollowingIn(String token,int page) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_PHOTOGRAPHER_FOLLOWING_IN_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_PATH_PARAMETER,String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(PhotoGrapherFollowingInResponse.class);
    }

    public static io.reactivex.Observable<CampaignResponse> getAllCampaign(String token, int page) {
        return Rx2AndroidNetworking.post(ALL_CAMPAIGN_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignResponse.class);
    }

    public static io.reactivex.Observable<CampaignInnerResponse> getCampaignDetails(String token, String campaignID) {
        return Rx2AndroidNetworking.post(CAMPAIGN_DETAILS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("campaign_id", campaignID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignInnerResponse.class);
    }


    public static io.reactivex.Observable<CampaignInnerPhotosResponse> getCampaignInnerPhotos(String token, String campaignID,int page) {
        return Rx2AndroidNetworking.post(CAMPAIGN_PHOTOS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("join_campaign_id", campaignID)
                .addQueryParameter(PAGER_PATH_PARAMETER,String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignInnerPhotosResponse.class);
    }

    public static io.reactivex.Observable<UserPhotosResponse> getUserProfilePhotos(String token, String userID, int page) {
        return Rx2AndroidNetworking.post(USER_PROFILE_PHOTOS)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("photographer_id", userID)
                .addQueryParameter(PAGER_PATH_PARAMETER,String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UserPhotosResponse.class);
    }
//

//    public static io.reactivex.Observable<BaseResponse> makeGetRequest(String lang, String key) {
//        return Rx2AndroidNetworking.get(REQUEST_URL)
//                .addPathParameter("lang", lang)
//                .addQueryParameter("key", String.valueOf(key))
//                .getResponseOnlyFromNetwork()
//                .build()
//                .getObjectObservable(BaseResponse.class);
//    }

//    /**
//     * --Upload Attachment
//     *
//     * @param file -->File src
//     * @message -->Request parameter you can add multible parameter to request  body along with uploaded attachment
//     */
//    public static void complaint(String message, File file, final RequestCallBack requestCallBack) {
//        AndroidNetworking.upload(REQUEST_URL)
//                .addHeaders("Content-Type", "multipart/form-data")
//                .addMultipartParameter("message", message)
//                .addMultipartFile("files[]", file) //todo "files[]" is A key According to back End
//                .setPriority(Priority.HIGH)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            if (success) {
//                                requestCallBack.OnSuccsess();
//                            } else {
//                                requestCallBack.onFailer();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            requestCallBack.onFailer();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        requestCallBack.onFailer();
//                    }
//                });
//    }
//

}
