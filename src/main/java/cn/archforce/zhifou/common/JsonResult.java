package cn.archforce.zhifou.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 统一返回结果
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/4/5 19:30
 * @since JDK 1.8
 */
@Getter
@Setter
@ToString
public class JsonResult {

    private Integer code;
    private String msg;
    private Object data;

    public JsonResult() {
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setResultCode(ResultCodeEnum resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }

    public static JsonResult success() {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResultCode(ResultCodeEnum.SUCCESS);
        return jsonResult;
    }

    public static JsonResult success(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResultCode(ResultCodeEnum.SUCCESS);
        jsonResult.setData(data);
        return jsonResult;
    }

    public static JsonResult failure(ResultCodeEnum resultCode) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResultCode(resultCode);
        return jsonResult;
    }

    public static JsonResult failure(ResultCodeEnum resultCode, Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResultCode(resultCode);
        jsonResult.setData(data);
        return jsonResult;
    }

}
