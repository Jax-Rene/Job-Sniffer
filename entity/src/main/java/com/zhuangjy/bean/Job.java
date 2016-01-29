package com.zhuangjy.bean;

/**
 * Created by zhuangjy on 2016/1/8.
 */
public class Job {
    String jobName;
    String companyCity;
    String companyName;
    Float workYear;
    Float salary;
    String education;
    String financeStage;
    String industryField;
    Float companySize;


    public Job(String jobName, String companyCity, String companyName, Float workYear, Float salary, String education, String financeStage, String industryField, Float companySize) {
        this.jobName = jobName;
        this.companyCity = companyCity;
        this.companyName = companyName;
        this.workYear = workYear;
        this.salary = salary;
        this.education = education;
        this.financeStage = financeStage;
        this.industryField = industryField;
        this.companySize = companySize;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyCity() {
        return companyCity;
    }

    public void setCompanyCity(String companyCity) {
        this.companyCity = companyCity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getFinanceStage() {
        return financeStage;
    }

    public void setFinanceStage(String financeStage) {
        this.financeStage = financeStage;
    }

    public String getIndustryField() {
        return industryField;
    }

    public void setIndustryField(String industryField) {
        this.industryField = industryField;
    }

    public Float getCompanySize() {
        return companySize;
    }

    public void setCompanySize(Float companySize) {
        this.companySize = companySize;
    }

    @Override
    public String toString(){
        return jobName + " " + companyCity + " " + companyName + " " + workYear + " " + salary + " " + education + " " +
                financeStage + " " + industryField + " " + companySize + "\r\n";
    }
}
