package com.harvestmoney.microdistribution.controller.advice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 返回成功或失败的响应
 */
public final class ResponseUtil {

    @Data
    @EqualsAndHashCode(callSuper = false)
    private static class SuccessResp<D> extends Response<D> {
        SuccessResp(D data) {
            this.httpCode = 200;
            this.status = "success";
            this.data = data;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private static class ErrorResp extends Response<String> {
        private Integer errorCode;
        private String errorMsg;

        ErrorResp(Integer httpcode, Integer errorCode, String errorMsg, String hintMsg) {
            this.httpCode = httpcode;
            this.status = "fail";
            this.errorCode = errorCode;
            this.errorMsg = errorMsg;
            this.data = hintMsg;
        }
    }

    /**
     * 处理成功后的返回
     * 
     * @param <D>  处理结果数据的类型
     * @param data 处理结果数据
     * @return {@link SuccessResp}
     */
    public static <D> SuccessResp<D> success(D data) {
        return new SuccessResp<>(data);
    }

    /**
     * 处理失败的返回
     * 
     * @param httpcode  Http响应码
     * @param errorCode 系统内自定义的错误代码
     * @param errorMsg  详细错误信息，用于开发者查看
     * @param hintMsg   错误友好提示信息，用于前端呈现，用户查看
     * @return
     */
    public static ErrorResp error(Integer httpcode, Integer errorCode, String errorMsg, String hintMsg) {
        return new ErrorResp(httpcode, errorCode, errorMsg, hintMsg);
    }
}
