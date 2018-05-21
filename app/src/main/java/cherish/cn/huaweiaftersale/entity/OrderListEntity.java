package cherish.cn.huaweiaftersale.entity;

import java.util.List;

public class OrderListEntity {
    private int result;

    private String message;

    private List<OrderListDataEntity> dataList;

    public void setResult(int result) {
        this.result = result;
    }

    public int getResult() {
        return this.result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setDataList(List<OrderListDataEntity> dataList) {
        this.dataList = dataList;
    }

    public List<OrderListDataEntity> getDataList() {
        return this.dataList;
    }

}