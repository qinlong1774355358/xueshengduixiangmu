package com.wyfx.aw.network.vo;

/**
 * 控制命令
 */
public class CmdUtil {

    /***********************************************************************************
     ******************************** 蜜罐暗网节点服务 *********************************
     ***********************************************************************************/
    public static final int SELECT_NETWORK=1;//获取节点硬件和网络属性

    public static final int SELECT_NODE_SERVER=2;//查询蜜罐节点服务

    public static final int START_NODE_SERVER=3;//启动蜜罐暗网节点服务

    public static final int STOP_NODE_SERVER=4;//关闭蜜罐暗网节点服务

    public static final int SWITCH_NODE_SERVER=5;//切换蜜罐暗网节点服务

    /***********************************************************************************
    ******************************** 蜜罐web管理服务 *********************************
    ***********************************************************************************/
    public static final int SELECT_WEB_SERVER=6;//查询蜜罐web管理服务

    public static final int START_WEB_SERVER=7;//启动蜜罐web服务

    public static final int STOP_WEB_SERVER=8;//关闭蜜罐web服务


    /***********************************************************************************
     ******************************** 蜜罐流量获取服务 *********************************
     ***********************************************************************************/
    public static final int SELECT_FLOW_SERVER=9;//获取蜜罐流量服务信息

    public static final int START_FLOW_SERVER=10;//启动蜜罐流量服务

    public static final int STOP_FLOW_SERVER=11;//关闭蜜罐流量服务



    /***********************************************************************************
     ******************************** 蜜罐攻击管理 *********************************
     ***********************************************************************************/
    public static final int SELECT_ATTACK_INFO=12;//攻击服务信息查询
    public static final int START_ATTACK=13;//启动攻击服务
    public static final int STOP_ATTACK=14;//关闭攻击服务
    public static final int SETTING_ATTACK_WAY=15;//设置攻击方式


    /***********************************************************************************
     ************************************** 文件管理 ***********************************
     ***********************************************************************************/
    public static final int FILE_UPLOAD=16;//文件上传命令
    public static final int FILE_DOWN=17;//文件下载命令
    public static final int FILE_DELETE=18;//文件删除命令
    public static final int SELECT_FILE_LIST=19;//获取选中目录文件列表命令


    /***********************************************************************************
     *******************************蜜罐服务器部署服务管理 *****************************
     ***********************************************************************************/
    public static final int SELECT_DEPLOY_SERVER=20;//服务挂载查询

    /***********************************************************************************
     ************************************** 日志管理 ***********************************
     ***********************************************************************************/
    public static final int FLOW_LOG=21;//流量分析日志获取
    public static final int INFO_LOG=22;//情报融合分析日志获取
    //public static final int SELECT_FILE_LIST=19;//获取选中目录文件列表命令

    /***********************************************************************************
     ************************************** 中继节点 ***********************************
     ***********************************************************************************/
    public static final int SELECT_NODE_INFO=23;//获取节点信息
    public static final int START_NODE=24;//开启节点
    public static final int STOP_NODE=25;//关闭节点
    public static final int RESTART_NODE=26;//重启节点

    /***********************************************************************************
     ********************************** 蜜罐流量回传 ***********************************
     ***********************************************************************************/
    public static final int GET_FLOW_FILE=27;//流量回传命令


    /***********************************************************************************
     ********************************** 流量分析处理 ***********************************
     ***********************************************************************************/
    public static final int FLOW_FILE_UPLOAD=28;//上传流量文件
    public static final int START_FLOW=29;//启动流量分析
    public static final int STOP_FLOW=30;//暂停流量分析
    public static final int END_FLOW=31;//结束流量分析

    /***********************************************************************************
     ********************************** 基础服务 ***************************************
     ***********************************************************************************/
    public static final int CONTROL_SERVICE_START=32;//控制服务在线状态修改：
    public static final int CONTROL_SERVICE_STOP=33;//控制服务离线状态修改：
    public static final int CONTROL_START=34;//控制在线状态修改
    public static final int CONTROL_STOP=35;//控制离线状态修改
   /***********************************************************************************
     ********************************** 基本属性 ***************************************
     ***********************************************************************************/
    public static final int SERVER_HARDWARE=36;//服务器硬件与网络属性
    public static final int SERVER_STATE=37;//服务器所部署服务
    public static final int SERVER_UPDATE=38;//蜜罐服务器修改

    /***********************************************************************************
     ********************************** 运行日志 ***************************************
     ***********************************************************************************/
    public static final int LOGGING=39;//日志信息命令



}