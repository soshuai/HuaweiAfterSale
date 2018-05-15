package cherish.cn.huaweiaftersale.okhttp.entity;


/**
 * 基于data的数据实体基类
 *
 * @param <T>
 */
public class BaseApiDataEntity<T> extends BaseApiEntity {
    private T data;

    public BaseApiDataEntity() { super();}

    public BaseApiDataEntity(Integer code, String msg) {
        super(code, msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
