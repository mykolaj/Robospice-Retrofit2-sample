package ua.techguardians.robospice.sample.retrofit2.datamodels;


import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 3/27/16.
 */
public class SingleMessageJson {
    @SerializedName("userId")
    private long mUserId;
    @SerializedName("id")
    private long mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("body")
    private String mBody;

    /**
     * This field is not present in a server's response. Instead it gets set by a custom converter
     * {@link ua.techguardians.robospice.sample.retrofit2.services.ApiService.CustomCacheSaver}
     */
    private String mTimestamp;

    public long getUserId() {
        return mUserId;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("[userId: \"%d\", id: \"%d\", title: \"%s\", body: \"%s\", \ntimestamp: \"%s\"]",
                getUserId(), getId(), getTitle(), getBody(), getTimestamp());
    }
}
