package com.example.softmills.phlog.ui.notification.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Business;
import com.example.softmills.phlog.base.commonmodel.Campaign;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.base.widgets.CustomTextView;
import com.example.softmills.phlog.ui.brand.view.BrandCampaignsActivity;
import com.example.softmills.phlog.ui.brand.view.BrandInnerActivity;
import com.example.softmills.phlog.ui.campaigns.inner.ui.CampaignInnerActivity;
import com.example.softmills.phlog.ui.commentimage.view.ImageCommentActivity;
import com.example.softmills.phlog.ui.notification.model.NotificationList;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivity;

import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_ALBUM;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_BRAND;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_CAMPAIGN;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_IMAGE;
import static com.example.softmills.phlog.Utiltes.Constants.ENTITY_PROFILE;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    public static final int itemType_NOTIFICATION_HEAD = 66;
    private Context context;
    private List<NotificationList> notificationItemList;

    public NotificationAdapter(List<NotificationList> notificationItemList) {
        this.notificationItemList = notificationItemList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new NotificationViewHolder(layoutInflater.inflate(R.layout.view_holder_notification, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        NotificationList notificationItem = notificationItemList.get(i);
        if (notificationViewHolder.notificationTitle != null)
            notificationViewHolder.notificationTitle.setText(notificationItemList.get(i).message);

        if (notificationItem.date != null)
            notificationViewHolder.notificationDate.setText(notificationItem.date);


        switch (notificationItemList.get(i).entityId) {
            case ENTITY_PROFILE: {
                setSeparatorState(notificationViewHolder, notificationItemList.get(i), false);
                bindItemPhotoGrapher(notificationViewHolder, notificationItemList.get(i).photographer);
                break;
            }
            case ENTITY_CAMPAIGN: {
                bindItemCampaign(notificationViewHolder, notificationItemList.get(i).campaign);
                break;
            }
            case ENTITY_ALBUM: {

                break;
            }
            case ENTITY_IMAGE: {
                bindItemPhoto(notificationViewHolder, notificationItemList.get(i).photographer, notificationItemList.get(i).photo);
                break;
            }
            case ENTITY_BRAND: {
                bindItemBrand(notificationViewHolder, notificationItemList.get(i).brand);
                break;
            }

            case itemType_NOTIFICATION_HEAD: {
                setSeparatorState(notificationViewHolder, notificationItemList.get(i), true);
                break;
            }
        }

        if (notificationItemList.get(i).isRead != null && !notificationItemList.get(i).isRead) {
            notificationViewHolder.notificationContainer.setBackgroundColor(context.getResources().getColor(R.color.transparent));

        } else {
            notificationViewHolder.notificationContainer.setBackgroundColor(context.getResources().getColor(R.color.grayEDF0F3));
        }

    }

    private void setSeparatorState(@NonNull NotificationViewHolder notificationViewHolder, NotificationList notificationList, boolean state) {
        if (state) {
            notificationViewHolder.notificationContainer.setVisibility(View.GONE);
            notificationViewHolder.notificationSeparatorView.setVisibility(View.VISIBLE);
            notificationViewHolder.separatorTitle.setText(notificationList.message);

        } else {
            notificationViewHolder.notificationContainer.setVisibility(View.VISIBLE);
            notificationViewHolder.notificationSeparatorView.setVisibility(View.GONE);

        }

    }


    private void bindItemPhotoGrapher(NotificationViewHolder notificationViewHolder, Photographer photographer) {
        notificationViewHolder.notificationImg.setBackground(context.getResources().getDrawable(R.drawable.circle_primary));
        String url = photographer != null ? photographer.imageProfile : null;
        GlideApp.with(context).load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
//                .error(R.drawable.default_error_img)
                .into(notificationViewHolder.notificationImg);

        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            if (photographer != null) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(photographer.id));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.photographer_not_available, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void bindItemCampaign(NotificationViewHolder notificationViewHolder, Campaign campaign) {

        notificationViewHolder.notificationImg.setBackground(context.getResources().getDrawable(R.drawable.circle_green));
        String url = "";
        if (campaign != null)
            if (campaign.business != null)
                url = campaign.business.thumbnail;
        GlideApp.with(context).load(url)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .into(notificationViewHolder.notificationImg);

        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            if (campaign != null) {
                Intent intent = new Intent(context, CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID, String.valueOf(campaign.id));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.campaign_not_available, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindItemBrand(NotificationViewHolder notificationViewHolder, Business business) {

        notificationViewHolder.notificationImg
                .setBackground(context.getResources().getDrawable(R.drawable.circle_brwon));

        String url = business != null ? business.thumbnail : null;
        GlideApp.with(context).load(url)
                .placeholder(R.drawable.default_place_holder)
                .error(R.drawable.default_error_img)
                .apply(RequestOptions.circleCropTransform())
                .into(notificationViewHolder.notificationImg);

        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            if (business != null) {
                Intent intent = new Intent(context, BrandInnerActivity.class);
                intent.putExtra(BrandInnerActivity.BRAND_ID, business.id);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, R.string.brand_not_available, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindItemPhoto(NotificationViewHolder notificationViewHolder, Photographer photographer, BaseImage image) {

        notificationViewHolder.notificationImg.setBackground(context.getResources().getDrawable(R.drawable.circle_red));

        if (photographer != null) {
            GlideApp.with(context).load(photographer.imageProfile)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(notificationViewHolder.notificationImg);
        } else {
            GlideApp.with(context).load("draft")
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(notificationViewHolder.notificationImg);
        }


        notificationViewHolder.notificationContainer.setOnClickListener(v -> {
            if (image != null) {
                Intent intent = new Intent(context, ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.NOTIFICATION_IMAGE_ID, image.id);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, context.getString(R.string.photo_not_available), Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return notificationItemList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        View notificationSeparatorView;
        LinearLayout notificationContainer;
        ImageView notificationImg;
        CustomTextView notificationTitle, notificationDate, separatorTitle;

        NotificationViewHolder(View view) {
            super(view);
            notificationImg = view.findViewById(R.id.notification_img);
            notificationTitle = view.findViewById(R.id.notification_title);
            notificationDate = view.findViewById(R.id.notification_time);
            notificationContainer = view.findViewById(R.id.notification_container);
            separatorTitle = view.findViewById(R.id.notification_separator_title);
            notificationSeparatorView = view.findViewById(R.id.notification_separator_view);
        }
    }
}
