package cherish.cn.huaweiaftersale.bean;

import java.util.List;

/**
 * Created by veryw on 2018/3/29.
 */

public class CheckVideoActBean {
    public String number;
    public String content;
    public String note;
    public String id;
    public boolean check;
    public List<String> imgList;

    public CheckVideoActBean(String number, String content, String note, boolean check) {
        this.number = number;
        this.content = content;
        this.note = note;
        this.check = check;
    }

    public CheckVideoActBean() {}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CheckVideoActBean{" +
                "number='" + number + '\'' +
                ", content='" + content + '\'' +
                ", note='" + note + '\'' +
                ", check=" + check +
                ", imgList=" + imgList +
                '}';
    }
}
