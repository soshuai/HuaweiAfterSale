package cherish.cn.huaweiaftersale.okhttp.bean;


import cherish.cn.huaweiaftersale.bean.BaseEntity;

public class TokenBean extends BaseEntity {
	private String code;
	private String data;
	private boolean success;

//	public TokenBean(String code, String data, boolean success) {
//		this.code = code;
//		this.data = data;
//		this.success = success;
//	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
