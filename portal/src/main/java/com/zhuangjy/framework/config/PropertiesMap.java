package com.zhuangjy.framework.config;

/**
 * Created by johnny on 16/4/13.
 */
public class PropertiesMap {
    private String area;
    private String companyType;
    private String financeStage;
    private String education;
    private String userName;
    private String passWord;
    private String time;

    public PropertiesMap(String area, String companyType, String financeStage, String education, String userName, String passWord,String time) {
        this.area = area;
        this.companyType = companyType;
        this.financeStage = financeStage;
        this.education = education;
        this.userName = userName;
        this.passWord = passWord;
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompanyType() {
        return companyType;
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
}
