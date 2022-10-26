package com.cohelp.server.constant;

/**
 * @author zgy
 * @create 2022-10-23 18:05
 */
public enum TypeEnum{
    USER,ACTIVITY,HELP,HOLE;
    /**
     * 判断type是否是话题类型
     * @author: ZGY
     * @date: 2022-10-23 17:45
     * @param type 待判断的类型参数
     * @return java.lang.Boolean
     */
    public static Boolean isTopic(Integer type){
        if(type == null)
            return false;
        if(type == TypeEnum.USER.ordinal())
            return false;
        else if(type == TypeEnum.ACTIVITY.ordinal()||type ==TypeEnum.HELP.ordinal()||type==TypeEnum.HOLE.ordinal())
            return true;
        else
            return false;
    }
    /**
     * 判断类型是否为用户
     * @author: ZGY
     * @date: 2022-10-23 19:38
     * @param type
     * @return java.lang.Boolean
     */
    public static Boolean isUser(Integer type){
        if(type == TypeEnum.USER.ordinal())
            return true;
        else
            return false;
    }
    /**
     * 判断类型是否为活动
     * @author: ZGY
     * @date: 2022-10-23 19:38
     * @param type
     * @return java.lang.Boolean
     */
    public static Boolean isActivity(Integer type){
        if(type == TypeEnum.ACTIVITY.ordinal())
            return true;
        else
            return false;
    }
    /**
     * 判断类型是否为互助
     * @author: ZGY
     * @date: 2022-10-23 19:38
     * @param type
     * @return java.lang.Boolean
     */
    public static Boolean isHelp(Integer type){
        if(type == TypeEnum.HELP.ordinal())
            return true;
        else
            return false;
    }
    /**
     * 判断类型是否为树洞
     * @author: ZGY
     * @date: 2022-10-23 19:38
     * @param type
     * @return java.lang.Boolean
     */
    public static Boolean isHole(Integer type){
        if(type == TypeEnum.HOLE.ordinal())
            return true;
        else
            return false;
    }
}
