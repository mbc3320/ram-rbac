//package top.beanshell.rbac.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import lombok.extern.slf4j.Slf4j;
//import org.beetl.sql.starter.SQLManagerCustomize;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import top.beanshell.common.utils.IdUtil;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * simple datasource config
// */
////@Configuration
//@Slf4j
//public class SimpleBeetlSQLDataSourceConfig {
//
//    @Resource
//    ApplicationContext ctx;
//
//    @Primary
//    @Bean(name = "ds1")
//    public DataSource datasource(Environment env) {
//        HikariDataSource ds = new HikariDataSource();
//        ds.setJdbcUrl(env.getProperty("spring.datasource.url"));
//        ds.setUsername(env.getProperty("spring.datasource.username"));
//        ds.setPassword(env.getProperty("spring.datasource.password"));
//        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        return ds;
//    }
//
//    @Bean
//    public SQLManagerCustomize mySQLManagerCustomize(){
//        return (sqlManagerName, manager) -> {
//            // custom id auto gen
//            manager.addIdAutoGen("myId", params -> IdUtil.getId());
//        };
//    }
//}
