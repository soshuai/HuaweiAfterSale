package cherish.cn.huaweiaftersale.entity;

import java.util.List;

import cherish.cn.huaweiaftersale.bean.BaseEntity;

/**
 * Created by veryw on 2018/5/21.
 */

public class OrderGetrecordDataEntity extends BaseEntity {
    private List<ImgList> imgList;

    private GetRecordInfo info;

    public void setImgList(List<ImgList> imgList) {
        this.imgList = imgList;
    }

    public List<ImgList> getImgList() {
        return this.imgList;
    }

    public void setInfo(GetRecordInfo info) {
        this.info = info;
    }

    public GetRecordInfo getInfo() {
        return this.info;
    }

    class ImgList {
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
