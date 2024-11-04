package com.demo.springboot.constant;

public class HttpRespCode {

    public enum Common {
        SUCCESS("00", "SUCCESS"),
        FAILURE("99", "系統錯誤");

        private String code;
        private String msg;

        private Common(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
