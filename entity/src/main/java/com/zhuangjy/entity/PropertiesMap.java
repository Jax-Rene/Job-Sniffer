package com.zhuangjy.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by johnny on 16/4/13.
 */

@Entity
@Table(name = "config")
@Proxy(lazy = false)
public class PropertiesMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "area")
    private String area;
    @Column(name = "company_type")
    private String companyType;
    @Column(name = "finance_stage")
    private String financeStage;
    @Column(name = "education")
    private String education;
    @Column(name = "time")
    private String time;
    @Transient
    private String job;
    @Transient
    private String userName;
    @Transient
    private String passWord;


    public PropertiesMap(String area, String companyType, String financeStage, String education, String time, String job) {
        this.area = area;
        this.companyType = companyType;
        this.financeStage = financeStage;
        this.education = education;
        this.time = time;
        this.job = job;
    }

    public PropertiesMap() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getFinanceStage() {
        return financeStage;
    }

    public void setFinanceStage(String financeStage) {
        this.financeStage = financeStage;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompanyType() {
        return companyType;
    }
}
