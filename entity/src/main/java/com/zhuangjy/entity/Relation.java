package com.zhuangjy.entity;

/**
 * Created by johnny on 16/4/6.
 */
public class Relation  {
    private Float salary;
    private String education;
    private String finance;
    private Float workYear;
    private String industry;
    private Integer companySize;

    public Relation(Float salary, String education, String finance, Float workYear, String industry, Integer companySize) {
        this.salary = salary;
        this.education = education;
        this.finance = finance;
        this.workYear = workYear;
        this.industry = industry;
        this.companySize = companySize;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Float getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Float workYear) {
        this.workYear = workYear;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Integer getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Integer companySize) {
        this.companySize = companySize;
    }
}
