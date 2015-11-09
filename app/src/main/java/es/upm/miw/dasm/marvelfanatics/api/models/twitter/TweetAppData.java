package es.upm.miw.dasm.marvelfanatics.api.models.twitter;

import android.os.Parcel;
import android.os.Parcelable;

public class TweetAppData implements Parcelable {
    public static final String DEFAULT_HASHTAG = "#MarvelManiacs";

    private String hashtag;
    private String comicName;

    public TweetAppData(String hashtag, String comicName) {
        this.hashtag = hashtag;
        this.comicName = comicName;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hashtag);
        dest.writeString(this.comicName);
    }

    protected TweetAppData(Parcel in) {
        this.hashtag = in.readString();
        this.comicName = in.readString();
    }

    public static final Parcelable.Creator<TweetAppData> CREATOR = new Parcelable.Creator<TweetAppData>() {
        public TweetAppData createFromParcel(Parcel source) {
            return new TweetAppData(source);
        }

        public TweetAppData[] newArray(int size) {
            return new TweetAppData[size];
        }
    };
}
