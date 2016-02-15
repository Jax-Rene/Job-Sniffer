package com.zhuangjy.framework.utils;

import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Author:王旗
 * Date:2015/6/10 12:21
 * Description:
 */
public class MathUtils {

    public static Double avg(List<Double> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0d;
        }
        return new BigDecimal(sum(list) / list.size()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    public static Double sum(List<Double> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0d;
        }
        Double res = new Double(0);
        for (Double d : list) {
            res += d;
        }
        return new BigDecimal(res).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double min(List<Double> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0d;
        }
        Double min = list.get(0);
        for (Double d : list) {
            if (d < min) {
                min = d;
            }
        }
        return min;
    }


    public static Double max(List<Double> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0d;
        }
        Double max = list.get(0);
        for (Double d : list) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public static Double divide(String a, String b, int scale) {
        return new BigDecimal(a).divide(new BigDecimal(b), scale, RoundingMode.HALF_UP).doubleValue();
    }

}
