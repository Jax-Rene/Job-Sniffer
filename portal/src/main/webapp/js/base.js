/**
 * Created by johnny on 16/3/26.
 * 一些常用方法的封装复用
 */

/**
 * 将Map对象遍历插入一个Map数组
 * @param data
 * @param name
 * @param value
 * @returns {Array}
 */
var datasPushMapArray = function (data, name, value) {
    var datas = new Array();
    $.each(data, function (n, v) {
        datas.push({value: v, name: n});
    });
    return datas;
}

/**
 * 将Map对象的所有值对应的所有对象插入一个Map数组 (两层)
 * @param data
 * @param name
 * @param value
 * @returns {Array}
 */
var datasPushMapArrayIter = function (data, name, value) {
    var datas = new Array();
    $.each(data, function (n1, v1) {
        $.each(v1, function (n2, v2) {
            datas.push({value: v2, name: n2});
        })
    })
    return datas;
}


/**
 * 将Map对象遍历插入两个数组
 * @param data
 * @returns {Array}
 */
var datasPushArrays = function (data) {
    var result = new Array();
    var nameData = new Array();
    var valData = new Array();
    $.each(data, function (name, val) {
        nameData.push(name);
        valData.push(val);
    });
    result.push(nameData, valData);
    return result;
}

/**
 * 将Map对象遍历插入数组
 * @param data
 * @param flag  为0插入name 为1插入value
 * @returns {Array}
 */
var datasPushArray = function (data, flag) {
    var result = new Array();
    $.each(data, function (n, v) {
        result.push(flag == 0 ? n : v);
    });
    return result;
}

/**
 * 将Map对象遍历插入数组(两层)
 * @param data
 * @param flag
 * @returns {Array}
 */
var datasPushArrayIter = function (data, flag) {
    var result = new Array();
    $.each(data, function (n1, v1) {
        $.each(v1, function () {
            result.push(flag == 0 ? n : v);
        });
    });
    return result;
}

/**
 * 将Map对象遍历插入数组以及Map(结合前两种)
 * @param data
 * @param name
 * @param value
 * @param flag
 */
var datasPushArrayAndMap = function (data, name, value, flag) {
    var datas = new Array();
    var map = new Array();
    var array = new Array();
    $.each(data, function (n, v) {
        map.push({value: v, name: n});
        array.push(flag == 0 ? n : v);
    });
    datas[0] = array;
    datas[1] = map;
    return datas;
}

/**
 * 将Map对象遍历插入数组以及Map(结合前两种)  —— (两层)
 * @param data
 * @param name
 * @param value
 * @param flag
 */
var datasPushArrayAndMapIter = function (data, name, value, flag) {
    var datas = new Array();
    var map = new Array();
    var array = new Array();
    $.each(data, function (n1, v1) {
        $.each(v1, function (n2, v2) {
            map.push({value: v2, name: n2});
            array.push(flag == 0 ? n2 : v2);
        });
    });
    datas[0] = array;
    datas[1] = map;
    return datas;
}


/**
 * 遍历Map对象两层寻找n2为 targetName的那组数据
 * @param data
 * @param name
 * @param value
 * @param targetName
 * @returns {undefined}
 */
var datasPushValueEqIter = function (data, name, value, targetName) {
    var flag = false;
    var temp = undefined;
    $.each(data, function (name, val) {
        temp = [];
        $.each(val, function (n, v) {
            temp.push({value: v, name: n});
            if (n == targetName)
                flag = true;
        });
        if (flag)
            return false;
    });
    return temp;
}


/**
 *  * 找寻数组最大值\以及下标
 * @param data
 * @returns {*}
 */
var findMax = function (data) {
    var res = [];
    if (data.length != 0) {
        var max = data[0];
        var maxIndex = 0;
        for (var i = 0; i < data.length; i++) {
            if (max < data[i]) {
                max = data[i];
                maxIndex = i;
            }
        }
        res[0] = maxIndex;
        res[1] = max;
    }else{
        res[0] = 0;
        res[1] = 0;
    }
    return res;
}


/**
 * 找寻数组中最小值以及其对应下标
 * @param data
 * @returns {*}
 */
var findMin = function (data) {
    var res = [];
    if (data.length != 0) {
        var min = data[0];
        minIndex = 0;
        for (var i = 0; i < data.length; i++) {
            if (min > data[i]) {
                min = data[i];
                minIndex = i;
            }
        }
        res[0] = minIndex;
        res[1] = min;
    }else{
        res[0] = 0;
        res[0] = 1;
    }
    return res;
}


/**
 * 提取对象数组中某一个共同的属性
 * @param data
 * @param n
 * @returns {Array}
 */
var getProperty = function (data, n) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        res.push(data[i][n]);
    }
    return res;
}


var datasPushObjsArray = function (data,properties) {
    var res = [];
    for(var i=0;i<data.length;i++){
        var col = [];
        for(var j=0;j<properties.length;j++){
            col.push(data[i][properties[j]]);
        }
        res.push(col);
    }
    return res;
}