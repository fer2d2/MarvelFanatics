package es.upm.miw.dasm.marvelfanatics.api.models.marvel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Comic implements Parcelable {

    /**
     * Values only present in local DB
     */
    @DatabaseField(columnName = "read")
    private boolean read;

    @DatabaseField(columnName = "favourite")
    private boolean favourite;

    /**
     * Values from web service
     */
    @DatabaseField(id = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "title")
    private String title;
    @DatabaseField(columnName = "description")
    private String description;
    @DatabaseField(columnName = "isbn")
    private String isbn;
    @DatabaseField(columnName = "page_count")
    private int pageCount;
    @DatabaseField(columnName = "resource_uri")
    private String resourceURI;
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "thumbnail")
    private Thumbnail thumbnail;
    @DatabaseField(columnName = "upc")
    private String upc;
    @DatabaseField(columnName = "diamond_code")
    private String diamondCode;
    @DatabaseField(columnName = "ean")
    private String ean;
    @DatabaseField(columnName = "issn")
    private String issn;
    @DatabaseField(columnName = "format")
    private String format;

    public Comic() {

    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public void setDiamondCode(String diamondCode) {
        this.diamondCode = diamondCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static Comic parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Comic comic = gson.fromJson(response, Comic.class);
        return comic;
    }

    @Override
    public String toString() {
        return "Comic{" +
                "read=" + read +
                ", favourite=" + favourite +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pageCount=" + pageCount +
                ", resourceURI='" + resourceURI + '\'' +
                ", thumbnail=" + thumbnail +
                ", upc='" + upc + '\'' +
                ", diamondCode='" + diamondCode + '\'' +
                ", ean='" + ean + '\'' +
                ", issn='" + issn + '\'' +
                ", format='" + format + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.isbn);
        dest.writeInt(this.pageCount);
        dest.writeString(this.resourceURI);
        dest.writeParcelable(this.thumbnail, 0);
        dest.writeString(this.isbn);
        dest.writeString(this.upc);
        dest.writeString(this.diamondCode);
        dest.writeString(this.ean);
        dest.writeString(this.issn);
        dest.writeString(this.format);
    }

    protected Comic(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.isbn = in.readString();
        this.pageCount = in.readInt();
        this.resourceURI = in.readString();
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.isbn = in.readString();
        this.upc = in.readString();
        this.diamondCode = in.readString();
        this.ean = in.readString();
        this.issn = in.readString();
        this.format = in.readString();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        public Comic createFromParcel(Parcel source) {
            return new Comic(source);
        }

        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
}
