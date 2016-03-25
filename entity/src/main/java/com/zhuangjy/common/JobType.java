package com.zhuangjy.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangjy on 2016/2/18.
 */
public enum JobType {
    HOU_DUAN(1, "后端开发", "Java,Python,PHP,.NET,C/C++/C#,VB,Delphi,Perl,Ruby,Hadoop,Node.js,数据挖掘,自然语言处理,搜索算法,精准推荐,全栈工程师,Go,ASP,Shell,后端开发其他",
            "Java,Python,PHP,.NET,C,VB,Delphi,Perl,Ruby,Hadoop,Node.js,数据挖掘,自然语言处理,搜索算法,推荐,全栈,Go,ASP,Shell,后端"),
    YI_DONG(2, "移动开发", "Android,IOS,WP,移动开发其他", "Android,IOS,WP,移动"),
    QIAN_DUAN(3, "前端开发", "web前端,Flash,html5,JavaScript,U3D,COCOS2D-X,前端开发其他", "web前端,Flash,html5,JavaScript,U3D,COCOS2D-X,前端"),
    CE_SHI(4, "测试", "测试工程师,自动化测试,功能测试,性能测试,测试开发,游戏测试,白盒测试,灰盒测试,黑盒测试,手机测试,硬件测试,测试经理,测试其他",
            "测试工程师,自动化,功能测试,性能测试,测试开发,游戏测试,白盒测试,灰盒测试,黑盒测试,手机测试,硬件测试,测试经理,测试"),
    YUN_WEI(5, "运维", "运维工程师,运维开发工程师,网络工程师,系统工程师,IT支持,IDC,CDN,F5,系统管理员,病毒分析,WEB安全,网络安全,系统安全,运维经理,运维其他",
            "运维工程师,运维开发工程师,网络工程师,系统工程师,IT支持,IDC,CDN,F5,系统管理员,病毒分析,WEB安全,网络安全,系统安全,运维经理,运维"),
    DBA(6, "DBA", "MySQL,SQLServer,Oracle,DB2,MongoDB,ETL,Hive,数据仓库,DBA其它", "MySQL,SQLServer,Oracle,DB2,MongoDB,ETL,Hive,数据仓库,DBA"),
    XIANG_MU(7, "项目", "项目经理,项目助理,项目类其他", "项目经理,项目助理,项目"),
    YING_JIAN(8, "硬件", "嵌入式,自动化,单片机,电路设计,驱动开发,系统集成,FPGA开发,DSP开发,ARM开发,PCB工艺,模具设计 热传导,材料工程师,精益工程师,射频工程师,硬件开发其它",
            "嵌入式,自动化,单片机,电路设计,驱动开发,系统集成,FPGA,DSP,ARM,PCB,模具设计,热传导,材料工程师,精益工程师,射频工程师,硬件"),
    QI_YE(9, "实施", "实施工程师,售前工程师,售后工程师,BI工程师,QA,实施类其它", "实施工程师,售前工程师,售后工程师,BI,QA,实施"),
    QI_TA(10,"其他","其他","其他");
    private int index;
    private String typeName;
    private String type;
    private String keyWord;

    JobType(int index, String typeName, String type, String keyWord) {
        this.index = index;
        this.typeName = typeName;
        this.type = type;
        this.keyWord = keyWord;
    }


    public static String getJobTypeName(int index){
        for(JobType jobType:JobType.values()){
            if(jobType.getIndex() == index){
                return jobType.getTypeName();
            }
        }
        return null;
    }

    public static List<String> allJobs() {
        List<String> types = new ArrayList<>();
        for (JobType type : JobType.values()) {
            for (String s : type.type.split(","))
                types.add(s);
        }
        return types;
    }

    public static int judgeType(String type) {
        for (JobType jobType : JobType.values()) {
            if (jobType.type.indexOf(type) != -1)
                return jobType.index;
        }
        return 0;
    }

    public static List<String> keyWords() {
        List<String> keyWords = new ArrayList<>();
        for (JobType type : JobType.values()) {
            for (String s : type.keyWord.split(","))
                keyWords.add(s);
        }
        return keyWords;
    }


    public static List<String> keyWords(Integer index){
        List<String> keyWords = new ArrayList<>();
        for(JobType type:JobType.values()){
            if(type.getIndex() != index)
                continue;
            for(String s:type.keyWord.split(","))
                keyWords.add(s);
        }
        return keyWords;
    }

    public static String getKeyWordsByName(String name, int jobType) {
        for (JobType type : JobType.values()) {
            if (jobType == type.index) {
                for (String s : type.keyWord.split(",")) {
                    if (name.indexOf(s) != -1)
                        return s;
                }
            }
        }
        return null;
    }

    public static String getKeyWordsByName(String name) {
        for (JobType type : JobType.values()) {
            for (String s : type.keyWord.split(",")) {
                if (name.toUpperCase().indexOf(s.toUpperCase()) != -1)
                    return s;
            }
        }
        return "其他";
    }

    public static String getJobNameByKeyWords(String key){
        for(JobType type:JobType.values()){
            //对于一个类别的其他选项只会出现在最后一个 并且是类别名称的子集
            if(type.typeName.indexOf(key)!=-1){
                String[] s = type.type.split(",");
                return s[s.length-1];
            }
            if(type.type.indexOf(key)!=-1){
                for(String s:type.type.split(",")){
                    if(s.indexOf(key)!=-1){
                        return s;
                    }
                }
            }
        }
        System.out.println(key);
        return null;
    }



    public static String getTypeName(int type) {
        for (JobType t : JobType.values()) {
            if (t.index == type)
                return t.typeName;
        }
        return null;
    }

    public static List<Integer> getAllTypeIndex(){
        List<Integer> list = new ArrayList<>();
        for(JobType s:JobType.values()){
            list.add(s.getIndex());
        }
        return list;
    }


    public int getIndex() {
        return index;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getType() {
        return type;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public static void main(String[] args) {
        System.out.println(getKeyWordsByName("运维开发工程师"));
    }
}
