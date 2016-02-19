package com.zhuangjy.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangjy on 2016/2/18.
 */
public enum JobType {
    HOU_DUAN(1, "Java,Python,PHP,.NET,C#,C++,C,VB,Delphi,Perl,Ruby,Hadoop,Node.js,数据挖掘,自然语言处理,搜索算法,精准推荐,全栈工程师,Go,ASP,Shell,后端开发其他",
            "Java,Python,PHP,.NET,C#,C++,C,VB,Delphi,Perl,Ruby,Hadoop,Node.js,数据挖掘,自然语言处理,搜索算法,精准推荐,全栈工程师,Go,ASP,Shell,后端开发其他"),
    YI_DONG(2, "Android,IOS,WP,移动开发其他", "Android,IOS,WP,移动开发其他"),
    QIAN_DUAN(3, "web前端,Flash,html5,JavaScript,U3D,COCOS2D-X,前端开发其他", "web前端,Flash,html5,JavaScript,U3D,COCOS2D-X,前端开发"),
    CE_SHI(4, "测试工程师,自动化测试,功能测试,性能测试,测试开发,游戏测试,白盒测试,灰盒测试,黑盒测试,手机测试,硬件测试,测试经理,测试其他",
            "测试工程师,自动化,功能测试,性能测试,测试开发,游戏测试,白盒测试,灰盒测试,黑盒测试,手机测试,硬件测试,测试经理,测试其他"),
    YUN_WEI(5, "运维工程师,运维开发工程师,网络工程师,系统工程师,IT支持,IDC,CDN,F5,系统管理员,病毒分析,WEB安全,网络安全,系统安全,运维经理,运维其他",
            "运维工程师,运维开发工程师,网络工程师,系统工程师,IT支持,IDC,CDN,F5,系统管理员,病毒分析,WEB安全,网络安全,系统安全,运维经理,运维其他"),
    DBA(6, "MySQL,SQLServer,Oracle,DB2,MongoDB,ETL,Hive,数据仓库,DBA其它", "MySQL,SQLServer,Oracle,DB2,MongoDB,ETL,Hive,数据仓库,DBA其它"),
    XIANG_MU(7, "项目经理,项目助理", "项目经理,项目助理"),
    YING_JIAN(8, "嵌入式,自动化,单片机,电路设计,驱动开发,系统集成,FPGA开发,DSP开发,ARM开发,PCB工艺,模具设计 热传导,材料工程师,精益工程师,射频工程师,硬件开发其它",
            "嵌入式,自动化,单片机,电路设计,驱动开发,系统集成,FPGA,DSP,ARM,PCB,模具设计 热传导,材料工程师,精益工程师,射频工程师,硬件开发其它"),
    QI_YE(9, "实施工程师,售前工程师,售后工程师,BI工程师,企业软件其它", "实施工程师,售前工程师,售后工程师,BI,企业软件其它");

    private int index;
    private String type;
    private String keyWord;

    JobType(int index, String type, String keyWord) {
        this.index = index;
        this.type = type;
        this.keyWord = keyWord;
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
}
