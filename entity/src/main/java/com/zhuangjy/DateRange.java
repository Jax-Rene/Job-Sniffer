package com.zhuangjy;

import java.util.Date;

/**
 * Author:王旗
 * Date:2015/6/10 10:44
 * Description:
 */
public class DateRange {
    
    private Date start;
    private Date end;

    public DateRange(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

}
