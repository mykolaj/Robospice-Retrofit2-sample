package ua.techguardians.robospicetest.datamodels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 3/27/16.
 */
public class SingleMessageJson {
    @JsonProperty("userId")
    private long mUserId;
    @JsonProperty("id")
    private long mId;
    @JsonProperty("title")
    private String mTitle;
    @JsonProperty("body")
    private String mBody;

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

    @Override
    public String toString() {
        return String.format("[userId: \"%d\", id: \"%d\", title: \"%s\", body: \"%s\"]",
                getUserId(), getId(), getTitle(), getBody());
    }
}
