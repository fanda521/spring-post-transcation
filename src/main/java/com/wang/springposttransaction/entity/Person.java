package com.wang.springposttransaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author lucksoul 王吉慧
 * @version 1.0
 * @date 2019-12-01 13:51
 */

@TableName(value = "t_person")
public class Person {
    /**
     * id号
     */
    @TableId(value = "c_id",type=IdType.AUTO)
    //使用的是自动增长，和数据库的一样，如果是默认的none，由于自动产生的id过长，
    //会出现类型不匹配的错误，要么修改type，要么修改id为Long类型的
    private Integer fId;
    /**
     * 姓名
     */
    @TableField(value = "c_name")
    private String fName;
    /**
     * 年龄
     */
    @TableField(value = "c_age")
    private  Integer fAge;
    /**
     *性别
     */
    @TableField(value = "c_sex")
    private  String fSex;
    /**
     *生日
     */
    @TableField(value = "c_birthday")
    private LocalDateTime fBirthday;
    /**
     *体重
     */
    @TableField(value = "c_weight")
    private Float fWeigth;
    /**
     *是否单身
     */
    @TableField(value = "c_isSingle")
    private  Boolean fIsSingle;

    public Integer getfId() {
        return fId;
    }
    public Integer getFId(){
        return getfId();
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Integer getfAge() {
        return fAge;
    }

    public void setfAge(Integer fAge) {
        this.fAge = fAge;
    }

    public String getfSex() {
        return fSex;
    }

    public void setfSex(String fSex) {
        this.fSex = fSex;
    }

    public LocalDateTime getfBirthday() {
        return fBirthday;
    }

    public void setfBirthday(LocalDateTime fBirthday) {
        this.fBirthday = fBirthday;
    }

    public Float getfWeigth() {
        return fWeigth;
    }

    public void setfWeigth(Float fWeigth) {
        this.fWeigth = fWeigth;
    }

    public Boolean getfIsSingle() {
        return fIsSingle;
    }

    public void setfIsSingle(Boolean fIsSingle) {
        this.fIsSingle = fIsSingle;
    }

    public Person(){}
    @Override
    public String toString() {
        return "Person{" +
                "fId=" + fId +
                ", fName='" + fName + '\'' +
                ", fAge=" + fAge +
                ", fSex='" + fSex + '\'' +
                ", fBirthday=" + fBirthday +
                ", fWeigth=" + fWeigth +
                ", fIsSingle=" + fIsSingle +
                '}';
    }
}
