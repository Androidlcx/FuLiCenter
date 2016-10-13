package cn.ucai.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class useravatar {
    private String musername;
    private String musernick;
    private int mavatarid;
    private String mavatarpath;
    private String mavatarsuffix;
    private int mavatartype;
    private String mavatarlastupdatetime;

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
}
