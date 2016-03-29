package com.zhuangjy.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by johnny on 16/3/1.
 */
@Entity
@Table(name = "area_analysis")
@Proxy(lazy = false)
public class AreaAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="area")
    private String area;
    @Column(name="count")
    private Long count;
    @Column(name="avg_salary")
    private Float avgSalary;
    @Column(name="industry_field")
    private String industryField;
    @Column(name="job_type_salary")
    private String jobTypeSalary;
    @Column(name="job_type_count")
    private String jobTypeCount;
    @Column(name="job_detail_count")
    private String jobDetailCount;
    @Column(name="job_detail_salary")
    private String jobDetailSalary;
    @Column(name="finance_stage")
    private String financeStage;

    public AreaAnalysis(String area, Long count, Float avgSalary, String industryField, String jobTypeSalary, String jobTypeCount, String jobDetailCount, String jobDetailSalary, String financeStage) {
        this.area = area;
        this.count = count;
        this.avgSalary = avgSalary;
        this.industryField = industryField;
        this.jobTypeSalary = jobTypeSalary;
        this.jobTypeCount = jobTypeCount;
        this.jobDetailCount = jobDetailCount;
        this.jobDetailSalary = jobDetailSalary;
        this.financeStage = financeStage;
    }

    public AreaAnalysis() {
    }

    public AreaAnalysis(String area){
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getIndustryField() {
        return industryField;
    }

    public void setIndustryField(String industryField) {
        this.industryField = industryField;
    }

    public String getJobTypeSalary() {
        return jobTypeSalary;
    }

    public void setJobTypeSalary(String jobTypeSalary) {
        this.jobTypeSalary = jobTypeSalary;
    }

    public String getJobTypeCount() {
        return jobTypeCount;
    }

    public void setJobTypeCount(String jobTypeCount) {
        this.jobTypeCount = jobTypeCount;
    }

    public String getJobDetailCount() {
        return jobDetailCount;
    }

    public void setJobDetailCount(String jobDetailCount) {
        this.jobDetailCount = jobDetailCount;
    }

    public String getJobDetailSalary() {
        return jobDetailSalary;
    }

    public void setJobDetailSalary(String jobDetailSalary) {
        this.jobDetailSalary = jobDetailSalary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinanceStage() {
        return financeStage;
    }

    public void setFinanceStage(String financeStage) {
        this.financeStage = financeStage;
    }
}
