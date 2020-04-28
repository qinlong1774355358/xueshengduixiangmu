package com.wyfx.aw.ws;

public class WsMessage {
    /**
     * 任务类型
     */
    private int type;
    /**
     * 时间参数
     */
    private String param;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
