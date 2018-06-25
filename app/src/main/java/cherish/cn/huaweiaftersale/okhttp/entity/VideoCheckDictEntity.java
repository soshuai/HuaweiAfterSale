package cherish.cn.huaweiaftersale.okhttp.entity;

import java.util.List;

/**
 * Created by veryw on 2018/5/9.
 */

public class VideoCheckDictEntity {

    /**
     * result : 0
     * message : success
     * dataList : [{"name":"d94ced9f4aaf4c25b48b1d205fc6845620180625164823.jpg","url":"upload/20180625/20180625164837HERG1T8Y.jpg"}]
     */

    private int result;
    private String message;
    private List<DataListBean> dataList;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * name : d94ced9f4aaf4c25b48b1d205fc6845620180625164823.jpg
         * url : upload/20180625/20180625164837HERG1T8Y.jpg
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
