package wangmin.message.mgr_web.web.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import wangmin.message.mgr_web.web.entity.type.CommonResponseCode;

/**
 * Created by wm on 2016/11/28.
 */
@ApiModel("http请求回复结构")
public class CommonResponseBody {
    @ApiModelProperty(value = "回复码", required = true)
    private int code;
    @ApiModelProperty(value = "回复错误信息", required = false)
    private String message;
    @ApiModelProperty(value = "回复的数据", required = false)
    private Object data;

    public CommonResponseBody() {
        this.code = CommonResponseCode.ok.getValue();
        this.message = "";
        this.data = null;
    }
    public CommonResponseBody(CommonResponseCode resultCode) {
        this.code = resultCode.getValue();
        this.message = "";
        this.data = null;
    }
    public CommonResponseBody(CommonResponseCode resultCode, String message) {
        this.code = resultCode.getValue();
        this.message = message;
        this.data = null;
    }
    public CommonResponseBody(CommonResponseCode resultCode, String message, Object data) {
        this.code = resultCode.getValue();
        this.message = message;
        this.data = data;
    }

    public boolean isOk() {
        return CommonResponseCode.ok.getValue() == code;
    }


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
