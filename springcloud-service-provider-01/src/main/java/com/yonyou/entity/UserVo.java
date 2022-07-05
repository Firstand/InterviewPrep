package com.yonyou.entity;

/**
 * @author zhangyu18
 * @date 2022-03-21 18:10
 * @since ncc2005
 */
public class UserVo {

    /**
     * 用户主键
     */
    protected String cUserId;

    /**
     * 密码
     */
    protected String userPassword;

    /**
     * 用户名
     */
    protected String userName;

    /**
     * 编码
     */
    protected String userCode;

    /**
     * 用户备注
     */
    protected String userNode;

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserNode() {
        return userNode;
    }

    public void setUserNode(String userNode) {
        this.userNode = userNode;
    }

    public String getDefaultTableName(){
        return "sm_user";
    }
}
