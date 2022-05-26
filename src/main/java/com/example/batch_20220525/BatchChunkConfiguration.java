package com.example.batch_20220525;



import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class BatchChunkConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    //private final DataSource dataSource;
    private static final int chunkSize = 30;
    //private final TradeConfiguration tradeConfiguration;


//    @Bean("mySqlBatchProcess")
//    @StepScope
//    public ItemProcessor mySqlBatchProcess() {
//        return new ChunkTestItemProcessor();
//    }

    public BatchChunkConfiguration(JobBuilderFactory jobBuilderFactory,
                     StepBuilderFactory stepBuilderFactory
//                     DataSource dataSource
      //                 TradeConfiguration tradeConfiguration
                       ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        //this.dataSource = dataSource;
        //this.tradeConfiguration = tradeConfiguration;
    }

/*
    @Bean
    public JdbcPagingItemReader<Person> jdbcPagingItemReader() throws Exception {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("id", 1);

        return new JdbcPagingItemReaderBuilder<Person>()
                .pageSize(chunkSize)
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Person.class))
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues)
                .name("jdbcPagingItemReader")
                .build();

    }



    @Bean
    public PagingQueryProvider createQueryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource); // Database에 맞는 PagingQueryProvider를 선택하기 위해
        queryProvider.setSelectClause("id, first_name, last_name");
        queryProvider.setFromClause("from monkey.people2");
        queryProvider.setWhereClause("where id >= :id");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }

*/










//    @Bean
//    public PersonItemProcessor processor() {
//        Object person;
//        return new PersonItemProcessor(person);
//    }

    @Bean
    //public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob3")
                .incrementer(new RunIdIncrementer())
                //.listener(listener)
                .flow(step1)
                .end()
                .build();
    }



    @Bean
    public Step step1(@Qualifier("myBatisPagingItemReader") MyBatisPagingItemReader myBatisPagingItemReader
        ,  @Qualifier("myBatisBatchItemWriter") MyBatisBatchItemWriter myBatisBatchItemWriter
    ) throws  Exception  {

        return stepBuilderFactory.get("step3")
                .<Person, Person> chunk(30)
                .reader(myBatisPagingItemReader)
                //.processor(processor())
                .writer(myBatisBatchItemWriter)
                .build();
    }



//    @Bean
//    public Step step1() throws  Exception  {
//        return stepBuilderFactory.get("step3")
//                .<Person, Person> chunk(30)
//                .reader(jdbcPagingItemReader())
//                .writer(writer())
//                .build();
//    }



/*
    public ItemWriter<Person> writer() throws  Exception{

        JdbcBatchItemWriter<Person> itemWriter = new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql("INSERT INTO monkey.people3 (firstName, lastName) VALUES (:firstName, :lastName)")
                .beanMapped()
                .build();
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }
*/
//
//    @Component
//    public static class JobCompletionNotificationListener extends JobExecutionListenerSupport {
//
//        private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
//
//        private final JdbcTemplate jdbcTemplate;
//
//        @Autowired
//        public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//            this.jdbcTemplate = jdbcTemplate;
//        }
//
//        @Override
//        public void afterJob(JobExecution jobExecution) {
////            if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
////                log.info("!!! JOB FINISHED! Time to verify the results");
////
////                jdbcTemplate.query("SELECT first_name, last_name FROM monkey.people",
////                        (rs, row) -> new Person(
////                                rs.getString(1),
////                                rs.getString(2))
////                ).forEach(person -> log.info("Found <" + person + "> in the database."));
////            }
//        }
//    }
}
