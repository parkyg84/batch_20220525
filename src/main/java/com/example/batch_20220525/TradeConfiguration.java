package com.example.batch_20220525;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


//@MapperScan(value ="demo.mssql.dao", sqlSessionFactoryRef =  "msSqlSqlSessionFactory")  //value = "@Mapper 패키지 경로"
//@EnableTransactionManagement
//출처: https://debugdaldal.tistory.com/186 [달달한 디버깅:티스토리]
//https://debugdaldal.tistory.com/186

@Configuration
public class TradeConfiguration {

    private final int pageSize = 10;

    @Bean(name="mySqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.sb.datasource")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = "mySqlSqlSessionFactory")
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("mySqlDataSource") DataSource db2DataSource
                , ApplicationContext applicationContext) throws Exception {


        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(db2DataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/trade/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "mySqlSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("mySqlSqlSessionFactory") SqlSessionFactory db2SqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(db2SqlSessionFactory);
    }

    @Primary
    @Bean(name = "tradeTX")
    public PlatformTransactionManager ProductTransactionManager(@Qualifier("mySqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }




    @Bean(name ="myBatisPagingItemReader")
    @StepScope
    public MyBatisPagingItemReader<Person> tradeDbRead(@Qualifier("mySqlSqlSessionFactory") SqlSessionFactory db2SqlSessionFactory) {

        MyBatisPagingItemReader<Person> myBatisPagingItemReader = new MyBatisPagingItemReader<Person>();
        Map<String, Object> parameters = new HashMap<>();
        //
        // parameters.put("_page", myBatisPagingItemReader.getPage() );
        myBatisPagingItemReader.setQueryId("selectTest");
        myBatisPagingItemReader.setSqlSessionFactory(db2SqlSessionFactory);
        myBatisPagingItemReader.setParameterValues(parameters);
        return myBatisPagingItemReader;
    }


    @Bean(name ="myBatisBatchItemWriter")
    @StepScope
    public MyBatisBatchItemWriter<Person> writer(@Qualifier("mySqlSqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        MyBatisBatchItemWriter<Person> myBatisBatchItemWriter = new MyBatisBatchItemWriter<Person>();
        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
        myBatisBatchItemWriter.setStatementId("insertTest");
        return myBatisBatchItemWriter;
    }




}
