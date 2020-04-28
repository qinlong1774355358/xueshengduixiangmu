package com.wyfx.aw.network.queue;

import com.wyfx.aw.network.vo.Pcmd;

public class Message {
    public Message() {
    }
    public Message(String idStr, int type, Object message) {
        this.idStr = idStr;
        this.type = type;
        this.message = message;
    }

    private int idst;//id标识
    private String idStr;//id标识
    private int type;//命令类型
    private Pcmd request;
    private Object message;//数据

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Pcmd getRequest() {
        return request;
    }

    public void setRequest(Pcmd request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idst=" + idst +
                ", idStr='" + idStr + '\'' +
                ", type=" + type +
                ", message=" + message +
                '}';
    }
}
