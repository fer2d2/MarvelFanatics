package es.upm.miw.dasm.marvelfanatics.api.models.marvel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Thumbnail implements Parcelable {

    @DatabaseField(id = true, columnName = "path")
    private String path;

    @DatabaseField(columnName = "extension")
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFullUrl() {
        return this.path + "." + this.extension;
    }

    public Thumbnail() {

    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }

    public static Thumbnail parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Thumbnail thumbnail = gson.fromJson(response, Thumbnail.class);
        return thumbnail;
    }

    protected Thumbnail(Parcel in) {
        path = in.readString();
        extension = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(extension);
    }

    public static final Parcelable.Creator<Thumbnail> CREATOR = new Parcelable.Creator<Thumbnail>() {
        @Override
        public Thumbnail createFromParcel(Parcel in) {
            return new Thumbnail(in);
        }

        @Override
        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };
}
