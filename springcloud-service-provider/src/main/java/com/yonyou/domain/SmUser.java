package com.yonyou.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * @TableName SM_USER
 */
@TableName(value ="SM_USER")
public class SmUser implements Serializable {
    /**
     * 
     */
    @TableId
    private String cuserid;

    /**
     * 
     */
    private String userpassword;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String usercode;

    /**
     * 
     */
    private String usernode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getCuserid() {
        return cuserid;
    }

    /**
     * 
     */
    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    /**
     * 
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * 
     */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    /**
     * 
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     */
    public String getUsercode() {
        return usercode;
    }

    /**
     * 
     */
    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    /**
     * 
     */
    public String getUsernode() {
        return usernode;
    }

    /**
     * 
     */
    public void setUsernode(String usernode) {
        this.usernode = usernode;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SmUser other = (SmUser) that;
        return (this.getCuserid() == null ? other.getCuserid() == null : this.getCuserid().equals(other.getCuserid()))
            && (this.getUserpassword() == null ? other.getUserpassword() == null : this.getUserpassword().equals(other.getUserpassword()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getUsercode() == null ? other.getUsercode() == null : this.getUsercode().equals(other.getUsercode()))
            && (this.getUsernode() == null ? other.getUsernode() == null : this.getUsernode().equals(other.getUsernode()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCuserid() == null) ? 0 : getCuserid().hashCode());
        result = prime * result + ((getUserpassword() == null) ? 0 : getUserpassword().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUsercode() == null) ? 0 : getUsercode().hashCode());
        result = prime * result + ((getUsernode() == null) ? 0 : getUsernode().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cuserid=").append(cuserid);
        sb.append(", userpassword=").append(userpassword);
        sb.append(", username=").append(username);
        sb.append(", usercode=").append(usercode);
        sb.append(", usernode=").append(usernode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}