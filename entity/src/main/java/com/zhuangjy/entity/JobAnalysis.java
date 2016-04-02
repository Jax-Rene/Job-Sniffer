package com.zhuangjy.entity;

import org.hibernate.annotations.Proxy;
import javax.persistence.*;

/**
 * Created by johnny on 16/3/29.
 */
@Entity
@Table(name = "job_analysis")
@Proxy(lazy = false)
public class JobAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "job_name")
    private String jobName;
    @Column(name = "count")
    private Long count;
    @Column(name="avg_salary")
    private Float avgSalary;
    @Column(name = "education")
    private String education;
    @Column(name = "work_year")
    private String workYear;
    @Column(name = "industry_field")
    private String industryField;
    @Column(name = "finance_stage")
    private String financeStage;
    @Column(name = "company_size")
    private String companySize;
    @Column(name = "area_count")
    private String areaCount;
   @Column(name = "area_salary")
   private String areaSalary;

    public JobAnalysis(String jobName, Long count, Float avgSalary, String education, String workYear, String industryField, String financeStage, String companySize, String areaCount, String areaSalary) {
        this.jobName = jobName;
        this.count = count;
        this.avgSalary = avgSalary;
        this.education = education;
        this.workYear = workYear;
        this.industryField = industryField;
        this.financeStage = financeStage;
        this.companySize = companySize;
        this.areaCount = areaCount;
        this.areaSalary = areaSalary;
    }

    public JobAnalysis(String jobName){
        this.jobName = jobName;
    }

    public JobAnalysis(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Float getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(Float avgSalary) {
        this.avgSalary = avgSalary;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getWorkYear() {
        return workYear;
    }

    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    public String getIndustryField() {
        return industryField;
    }

    public void setIndustryField(String industryField) {
        this.industryField = industryField;
    }

    public String getFinanceStage() {
        return financeStage;
    }

    public void setFinanceStage(String financeStage) {
        this.financeStage = financeStage;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getAreaState() {
        return areaCount;
    }

    public void setAreaState(String areaCount) {
        this.areaCount = areaCount;
    }

    public String getAreaCount() {
        return areaCount;
    }

    public void setAreaCount(String areaCount) {
        this.areaCount = areaCount;
    }

    public String getAreaSalary() {
        return areaSalary;
    }

    public void setAreaSalary(String areaSalary) {
        this.areaSalary = areaSalary;
    }


}
