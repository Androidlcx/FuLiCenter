package cn.ucai.fulicenter.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class useravatar implements Parcelable{
    private String musername;
    private String musernick;
    private int mavatarid;
    private String mavatarpath;
    private String mavatarsuffix;
    private int mavatartype;
    private String mavatarlastupdatetime;

    protected useravatar(Parcel in) {
        musername = in.readString();
        musernick = in.readString();
        mavatarid = in.readInt();
        mavatarpath = in.readString();
        mavatarsuffix = in.readString();
        mavatartype = in.readInt();
        mavatarlastupdatetime = in.readString();
    }

    public static final Creator<useravatar> CREATOR = new Creator<useravatar>() {
        @Override
        public useravatar createFromParcel(Parcel in) {
            return new useravatar(in);
        }

        @Override
        public useravatar[] newArray(int size) {
            return new useravatar[size];
        }
    };

    public String getMusername() {
        return musername;
    }

    public void setMusername(String musername) {
        this.musername = musername;
    }

    public String getMusernick() {
        return musernick;
    }

    public void setMusernick(String musernick) {
        this.musernick = musernick;
    }

    public int getMavatarid() {
        return mavatarid;
    }

    public void setMavatarid(int mavatarid) {
        this.mavatarid = mavatarid;
    }

    public String getMavatarpath() {
        return mavatarpath;
    }

    public void setMavatarpath(String mavatarpath) {
        this.mavatarpath = mavatarpath;
    }

    public String getMavatarsuffix() {
        return mavatarsuffix;
    }

    public void setMavatarsuffix(String mavatarsuffix) {
        this.mavatarsuffix = mavatarsuffix;
    }

    public int getMavatartype() {
        return mavatartype;
    }

    public void setMavatartype(int mavatartype) {
        this.mavatartype = mavatartype;
    }

    public String getMavatarlastupdatetime() {
        return mavatarlastupdatetime;
    }

    public void setMavatarlastupdatetime(String mavatarlastupdatetime) {
        this.mavatarlastupdatetime = mavatarlastupdatetime;
    }

    public useravatar() {
    }

    @Override
    public String toString() {
        return "useravatar{" +
                "musername='" + musername + '\'' +
                ", musernick='" + musernick + '\'' +
                ", mavatarid=" + mavatarid +
                ", mavatarpath='" + mavatarpath + '\'' +
                ", mavatarsuffix='" + mavatarsuffix + '\'' +
                ", mavatartype=" + mavatartype +
                ", mavatarlastupdatetime='" + mavatarlastupdatetime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musername);
        dest.writeString(musernick);
        dest.writeInt(mavatarid);
        dest.writeString(mavatarpath);
        dest.writeString(mavatarsuffix);
        dest.writeInt(mavatartype);
        dest.writeString(mavatarlastupdatetime);
    }
}
