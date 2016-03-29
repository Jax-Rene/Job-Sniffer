package com.zhuangjy.common;


import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import java.util.*;

/**
 * Created by johnny on 16/3/27.
 */
public enum JobEnum {
    JAVA("Java", 1, "Java"),
    PYTHON("Python", 1, "Python"),
    PHP("PHP", 1, "PHP"),
    DOTNET(".NET", 1, ".NET"),
    C("C/C++/C#", 1, "C"),
    VB("VB", 1, "VB"),
    Delphi("Delphi", 1, "Delphi"),
    Perl("Perl", 1, "Perl"),
    Ruby("Ruby", 1, "Ruby"),
    Hadoop("Hadoop", 1, "Hadoop"),
    NodeJs("Node.js", 1, "Node.js"),
    DM("数据挖掘", 1, "数据挖掘"),
    NLP("自然语言处理", 1, "自然语言处理"),
    SEARCH_ALGORITHM("搜索算法", 1, "搜索"),
    AR("精准推荐", 1, "推荐"),
    WHOLE_STACK("全栈工程师", 1, "全栈"),
    GO("Go", 1, "Go"),
    ASP("ASP", 1, "ASP"),
    SHELL("Shell", 1, "Shell"),
    SERVER_OTHER("后端开发其他", 1, "后端"),

    ANDROID("Android", 2, "Android"),
    IOS("IOS", 2, "IOS"),
    WP("WP", 2, "WP"),
    MOBINE_OTHER("移动开发其他", 2, "移动"),

    WEB_FONT("web前端", 3, "web前端"),
    FLASH("Flash", 3, "Flash"),
    html5("html5", 3, "html5"),
    JavaScript("JavaScript", 3, "JavaScript"),
    U3D("U3D", 3, "U3D"),
    COCOS2D("COCOS2D-X", 3, "COCOS2D-X"),
    FONT_OTHER("前端开发其他", 3, "前端"),

    TEST_ENG("测试工程师", 4, "测试工程师"),
    TEST_AUTO("自动化测试", 4, "自动化测试"),
    FUNC_TEST("功能测试", 4, "功能测试"),
    PERFORMTEST("性能测试", 4, "性能测试"),
    TEST_DEV("测试开发", 4, "测试开发"),
    GAME_TEST("游戏测试", 4, "游戏测试"),
    WHITE_TEST("白盒测试", 4, "白盒测试"),
    GREEN_TEST("灰盒测试", 4, "灰盒测试"),
    BLACK_TEST("黑盒测试", 4, "黑盒测试"),
    PHONE_TEST("手机测试", 4, "手机测试"),
    HW_TEST("硬件测试", 4, "硬件测试"),
    TEST_MANER("测试经理", 4, "测试经理"),
    TEST_OTHER("测试其他", 4, "测试其他"),

    MAINTANCE("运维工程师", 5, "运维"),
    MAINTANCE_DEV("运维开发工程师", 5, "运维开发"),
    NET_ENG("网络工程师", 5, "网络工程师"),
    SYS_ENG("系统工程师", 5, "系统工程师"),
    IT_SUPPORT("IT支持", 5, "IT支持"),
    IDC("IDC", 5, "IDC"),
    CDN("CDN", 5, "CDN"),
    F5("F5", 5, "F5"),
    VIRUS("病毒分析", 5, "病毒分析"),
    WEB_SAFE("WEB安全", 5, "WEB安全"),
    NET_SAFE("网络安全", 5, "网络安全"),
    SYS_SAFE("系统安全", 5, "系统安全"),
    MAINTANCE_MANGER("运维经理", 5, "运维经理"),
    MAINTANCE_OTHER("运维其他", 5, "运维其他"),

    MYSQL("MySQL", 6, "MySQL"),
    SQLServer("SQLServer", 6, "SQLServer"),
    Oracle("Oracle", 6, "Oracle"),
    DB2("DB2", 6, "DB2"),
    MongoDB("MongoDB", 6, "MongoDB"),
    ETL("ETL", 6, "ETL"),
    Hive("Hive", 6, "Hive"),
    数据仓库("数据仓库", 6, "数据仓库"),
    DBA其它("DBA其他", 6, "DBA其他"),

    PROJECT_MANGER("项目经理", 7, "项目经理"),
    PROJECT_ASSISTANT("项目助理", 7, "项目助理"),
    PROJECT_OTHER("项目类其他", 7, "项目"),

    EMBEDDED("嵌入式", 8, "嵌入式"),
    AUTO("自动化", 8, "自动化"),
    SINGLECHIP("单片机", 8, "单片机"),
    CIRCUIT_DESIGN("电路设计", 8, "电路设计"),
    LCD("驱动开发", 8, "驱动开发"),
    SYSTEM_INHERITANCE("系统集成", 8, "系统集成"),
    FPGA("FPGA开发", 8, "FPGA"),
    DSP("DSP开发", 8, "DSP"),
    ARM("ARM开发", 8, "ARM"),
    PCB("PCB工艺", 8, "PCB"),
    MOLD_DESIGN("模具设计", 8, "模具设计"),
    HEAT_CONDUCTION("热传导", 8, "热传导"),
    MATERIAL_ENG("材料工程师", 8, "材料"),
    LEAN_ENG("精益工程师", 8, "精益"),
    RADIO_ENG("射频工程师", 8, "射频"),
    HARDWARE("硬件开发其他", 8, "硬件"),

    IMPLEMENT_ENG("实施工程师", 9, "实施"),
    PRE_SALE_ENG("售前工程师", 9, "售前"),
    AFTER_SALE_ENG("售后工程师", 9, "售后"),
    BI("BI工程师", 9, "BI"),
    QA("QA", 9, "QA"),
    IMPLEMENT_OTHER("企业类其他", 9, "企业类其它");


    private String name;
    private Integer typeIndex;
    private String keyWord;

    JobEnum(String name, Integer typeIndex, String keyWord) {
        this.name = name;
        this.typeIndex = typeIndex;
        this.keyWord = keyWord;
    }

    public static List<String> listAllJobs() {
        List<String> list = new ArrayList<>();
        for (JobEnum j : JobEnum.values()) {
            list.add(j.name);
        }
        return list;
    }

    /**
     * 通过NAME 获取对应的TYPE
     * @param name
     * @return
     */
    public static int getTypeByName(String name){
        for(JobEnum j:JobEnum.values()){
            if(j.name.equals(name)){
                return j.typeIndex;
            }
        }
        System.out.println("getTypeByName 出现错误:" + name);
        return -1;
    }


    /**
     * 通过JOBNAME来获取对应的TYPE,匹配最长名称
     * @param jobName
     * @return
     */
    public static int judgeTypeByName(String jobName){
        jobName = jobName.toUpperCase();
        int index = 10;
        int maxLen = 0;
        for(JobEnum j : JobEnum.values()){
            if(jobName.indexOf(j.keyWord.toUpperCase())!=-1){
                //匹配最长
                if(maxLen < j.keyWord.length()){
                    maxLen = j.keyWord.length();
                    index = j.typeIndex;
                }
            }
        }
        return index;
    }

    /**
     * 返回该类型的所有KEY WORDS
     * @param index
     * @return
     */
    public static List<String> listKeyWords(int index){
        List<String> list = new ArrayList<>();
        for(JobEnum j:JobEnum.values()){
            if(j.typeIndex == index){
                list.add(j.keyWord);
            }
        }
        return list;
    }

    public static String getKeyWordsByName(String jobName) {
        jobName = jobName.toUpperCase();
        String keyWords = "其他";
        int maxLen = 0;
        for(JobEnum j : JobEnum.values()){
            if(jobName.indexOf(j.keyWord.toUpperCase())!=-1){
                //匹配最长
                if(maxLen < j.keyWord.length()){
                    maxLen = j.keyWord.length();
                    keyWords = j.keyWord;
                }
            }
        }
        if(keyWords.equals("其他")){
            System.out.println("getKeyWordsByName找不到: " + jobName);
        }
        return keyWords;
    }


    public static String getKeyWordsByName(String jobName,int index) {
        jobName = jobName.toUpperCase();
        String keyWords = null;
        int maxLen = 0;
        for(JobEnum j : JobEnum.values()){
            if(j.typeIndex!=index)
                continue;
            if(jobName.indexOf(j.keyWord.toUpperCase())!=-1){
                //匹配最长
                if(maxLen < j.keyWord.length()){
                    maxLen = j.keyWord.length();
                    keyWords = j.keyWord;
                }
            }
        }
        if(StringUtils.isEmpty(keyWords)){
            return getOther(index);
        }
        return keyWords;
    }

    public static String getJobNameByKeyWords(String keyWord){
        keyWord = keyWord.toUpperCase();
        for(JobEnum j :JobEnum.values()){
            if(j.keyWord.toUpperCase().equals(keyWord)){
                return j.name;
            }
        }
        System.out.println("出现找不到的KeyWords: " + keyWord);
        return null;
    }

    public static List<Integer> listAllTypeIndex(){
        Set<Integer> set = new HashSet<>();
        for(JobEnum j :JobEnum.values()){
            set.add(j.typeIndex);
        }
        return new ArrayList<>(set);
    }

    public static String getOther(int index){
        for (JobEnum j:JobEnum.values()){
            if(j.typeIndex != index)
                continue;
            if(j.name.indexOf("其他")!=-1)
                return j.name;
        }
        return null;
    }


    public static List<String> listJobNames(){
        List<String> list = new ArrayList<>();
        for(JobEnum j:JobEnum.values()){
            list.add(j.name);
        }
        return list;
    }


    public String getName() {
        return name;
    }

    public Integer getTypeIndex() {
        return typeIndex;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public static void main(String[] args) {
        System.out.println(getKeyWordsByName("功能测试".toUpperCase()));
    }
}
