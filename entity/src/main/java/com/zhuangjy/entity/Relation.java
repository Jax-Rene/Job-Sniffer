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

    public Relation(String education, String finance, String industry, Float workYear, Float salary) {
        this.education = education;
        this.finance = finance;
        this.industry = industry;
        this.workYear = workYear;
        this.salary = salary;
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
}
