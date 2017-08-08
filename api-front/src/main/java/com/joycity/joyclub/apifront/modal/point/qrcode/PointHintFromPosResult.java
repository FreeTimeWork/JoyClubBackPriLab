package com.joycity.joyclub.apifront.modal.point.qrcode;

/**
 * Created by Administrator on 2017/8/6.
 * 从pos后台获取积分抵现提示的结果
 */
public class PointHintFromPosResult {
    private Integer code;
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String message;
        private DataList dataList;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DataList getDataList() {
            return dataList;
        }

        public void setDataList(DataList dataList) {
            this.dataList = dataList;
        }
    }

    public static class DataList {
        private Integer retcode;
        private String scogiftinfo;
        private String retnote;

        public Integer getRetcode() {
            return retcode;
        }

        public void setRetcode(Integer retcode) {
            this.retcode = retcode;
        }

        public String getScogiftinfo() {
            return scogiftinfo;
        }

        public void setScogiftinfo(String scogiftinfo) {
            this.scogiftinfo = scogiftinfo;
        }

        public String getRetnote() {
            return retnote;
        }

        public void setRetnote(String retnote) {
            this.retnote = retnote;
        }
    }
}
