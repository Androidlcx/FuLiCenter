package cn.ucai.fulicenter.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/13.
 */
public class MessageBean implements Serializable{
    /**
     * success : true
     * msg :
     */

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MessageBean() {
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
