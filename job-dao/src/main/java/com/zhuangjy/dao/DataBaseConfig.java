package com.zhuangjy.dao;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Author:王旗
 * Date:2014/8/21 14:57
 * Description:
 */
@Configuration
@EnableTransactionManagement
@PropertySource(ignoreResourceNotFound = true, value = {"classpath:database.properties", "file:/etc/job-analysis/database.properties"})
public class DataBaseConfig implements TransactionManagementConfigurer {


    private final static Logger logger = LoggerFactory.getLogger(DataBaseConfig.class);


    @Value("${c3p0.max_size}")
    private int c3p0_max_size;
    @Value("${c3p0.url}")
    private String c3p0_url;
    @Value("${c3p0.username}")
    private String c3p0_username;
    @Value("${c3p0.password}")
    private String c3p0_password;

    @PostConstruct
    public void init() {
        logger.info("database-config:");
        logger.info("c3p0_max_size:" + c3p0_max_size);
        logger.info("c3p0_url:" + c3p0_url);
        logger.info("c3p0_username:" + c3p0_username);
        logger.info("c3p0_password:" + c3p0_password);
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        logger.info("gen data-source for url [" + c3p0_url + "]");
        try {
            c3p0_username = CryptoUtil.decryptApi(c3p0_username);
        } catch (Exception e) {
            logger.error("decode user [{}] failed ", c3p0_username);
        }
        try {
            c3p0_password = CryptoUtil.decryptApi(c3p0_password);
        } catch (Exception e) {
            logger.error("decode password [{}] failed ", c3p0_password);
        }
        BoneCPDataSource db = null;
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(c3p0_url);
        config.setUsername(c3p0_username);
        config.setPassword(c3p0_password);
        config.setStatementsCacheSize(0);
        config.setDetectUnclosedStatements(true);
        config.setCloseOpenStatements(true);
        config.setConnectionTimeoutInMs(10000);
        config.setConnectionTestStatement("SELECT 1 FROM DUAL");
        config.setDefaultTransactionIsolation("READ_COMMITTED");
        config.setPartitionCount(4);
        config.setMaxConnectionsPerPartition(c3p0_max_size / 4 == 0 ? 1 : c3p0_max_size / 4);
        config.setMaxConnectionAge(0, TimeUnit.SECONDS);
        config.setMinConnectionsPerPartition(1);
        config.setAcquireRetryAttempts(3);
        config.setAcquireRetryDelayInMs(5000);
        config.setIdleMaxAge(5, TimeUnit.MINUTES);//mysql的wati_timeout默认为28800
        config.setIdleConnectionTestPeriod(1, TimeUnit.MINUTES);
        db = new BoneCPDataSource(config); // setup the connection pool
        logger.info("gen data-source for url [" + c3p0_url + "] success");
        return db;
    }

    @Bean(name = "sessionFactory", destroyMethod = "destroy")
    public LocalSessionFactoryBean sessionFactory() throws IOException {
        logger.info("gen sessionFactory start");
        LocalSessionFactoryBean session = new org.springframework.orm.hibernate4.LocalSessionFactoryBean();
        session.setPackagesToScan(new String[]{"com.zhuangjy.entity"});
//        session.setPackagesToScan(new String[]{"com.cnc.**.entity", "com.cnc.**.bean", "com.cnc.**.model"});
        session.setDataSource(dataSource());
        InputStream cr = this.getClass().getResourceAsStream("/hibernate.properties");
        Properties hiberanteProperties = new Properties();
        hiberanteProperties.load(cr);
        cr.close();
        session.setHibernateProperties(hiberanteProperties);
        logger.info("gen sessionFactory success");
        return session;
    }

    @Bean(name = "lobHandler")
    public LobHandler lobHandler() {
        return new org.springframework.jdbc.support.lob.DefaultLobHandler();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Qualifier("txManager")
    public PlatformTransactionManager txManager() throws IOException {
        return new org.springframework.orm.hibernate4.HibernateTransactionManager(sessionFactory()
                .getObject());
    }


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        try {
            return txManager();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    @Qualifier("sqlMapClient")
    public SqlMapClientFactoryBean sqlMapClient() {
        logger.info("gen sqlMapClient start");
        SqlMapClientFactoryBean bean = new SqlMapClientFactoryBean();
//        bean.setConfigLocation(new FileSystemResource("/etc/eagle/ibatis/sqlmap-config.xml"));
        bean.setConfigLocation(new ClassPathResource("/sqlmap-config.xml"));
        bean.setDataSource(dataSource());
        bean.setLobHandler(lobHandler());
        logger.info("gen sqlMapClient success");
        return bean;
    }

}
