package com.zhuangjy.entity;


import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by johnny on 16/3/8.
 */
@Entity
@Table(name="origin")
@Proxy(lazy = false)
public class Origin implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="origin")
    private String origin;
    @Column(name="count")
    private Long count;
    @Column(name="salary")
    private Float salary;
    @Column(name="detail_count")
    private String detailCount;

    public Origin(String origin, Long count, Float salary,String detailCount) {
        this.origin = origin;
        this.count = count;
        this.salary = salary;
        this.detailCount = detailCount;
    }

    public Origin() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getDetailCount() {
        return detailCount;
    }

    public void setDetailCount(String detailCount) {
        this.detailCount = detailCount;
    }
}
