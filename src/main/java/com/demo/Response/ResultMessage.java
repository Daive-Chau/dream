
package com.demo.Response;


/**
 * rest请求返回结果
 *
 * @author 31779
 * @Date 2017/5/31
 */
public class ResultMessage {

    // 是否成功
    private final Boolean success;

    // 错误消息
    private final String errMsg;

    // 返回数据
    private final Object data;

    public ResultMessage(Boolean success, String errMsg, Object data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    public ResultMessage(Boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = null;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Object getData() {
        return data;
    }
}
