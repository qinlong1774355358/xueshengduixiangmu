package com.wyfx.aw.utils;

/**
 * 节点接入状态
 */
public enum ConnStateEnum {
    CONN_SUCCESS("接入",0),CONN_CERTIFICA("认证中",1),CONN_ATTACK("攻击中",2),CONN_FAIL("未接入",3);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ConnStateEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (ConnStateEnum c : ConnStateEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
