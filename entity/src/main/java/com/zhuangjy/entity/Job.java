package com.zhuangjy.entity;

import com.zhuangjy.common.JobType;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;

/**
 * Created by zhuangjy on 2016/1/8.
 */
@Entity
@Table(name = "job")
@Proxy(lazy = false)
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(name= "job_name")
    private String jobName;

    @Column(name = "job_type")
    private Integer jobType;

    @Column(name="company_city")
    private String companyCity;

    @Column(name = "company_name")
    private String companyName;

    @Column(name ="work_year")
    private Float workYear;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "education")
    private String education;

    @Column(name = "finance_stage")
    private String financeStage;

    @Column(name = "industry_field")
    private String industryField;

    @Column(name = "company_size")
    private Float companySize;

    @Column(name ="origin")
    private String origin;

    @Column(name = "time")
    private Date date;


    @Transient
    private String jobTypeName;


    public Job(String jobName, Integer jobType,String companyCity, String companyName, Float workYear, Float salary, String education, String financeStage, String industryField, Float companySize,String origin) {
        this.jobName = jobName;
        this.jobType = jobType;
        this.companyCity = companyCity;
        this.companyName = companyName;
        this.workYear = workYear;
        this.salary = salary;
        this.education = education;
        this.financeStage = financeStage;
        this.industryField = industryField;
        this.companySize = companySize;
        this.origin = origin;
        this.date = new Date(System.currentTimeMillis());
    }

    public String getOrigin(){
        return origin;
    }

    public void setOrigin(String origin){
        this.origin = origin;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
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

    public Job() {
        this.date = new Date(System.currentTimeMillis());
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }



    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", jobType=" + jobType +
                ", companyCity='" + companyCity + '\'' +
                ", companyName='" + companyName + '\'' +
                ", workYear=" + workYear +
                ", salary=" + salary +
                ", education='" + education + '\'' +
                ", financeStage='" + financeStage + '\'' +
                ", industryField='" + industryField + '\'' +
                ", companySize=" + companySize +
                ", origin='" + origin + '\'' +
                ", date=" + date +
                '}';
    }
}
