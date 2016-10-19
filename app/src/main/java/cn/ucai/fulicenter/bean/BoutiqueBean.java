package cn.ucai.fulicenter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class BoutiqueBean implements Parcelable{
    private int id;
    private String title;
    private String description;
    private String name;
    private String imageurl;

    protected BoutiqueBean(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        name = in.readString();
        imageurl = in.readString();
    }

    public static final Creator<BoutiqueBean> CREATOR = new Creator<BoutiqueBean>() {
        @Override
        public BoutiqueBean createFromParcel(Parcel in) {
            return new BoutiqueBean(in);
        }

        @Override
        public BoutiqueBean[] newArray(int size) {
            return new BoutiqueBean[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public BoutiqueBean() {
    }

    @Override
    public String toString() {
        return "BoutiqueBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(imageurl);
    }
}
