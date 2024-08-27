package com.ilot.utils.quartz;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
public class QrtzSchedulerConfig {
    @Autowired
    private JobFactory jobFactory;
    //@Autowired
    //@Qualifier("dataSource")
    //private DataSource primaryDataSource;
//    @Bean
//    @QuartzDataSource
//    public DataSource quartzDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        //Get configuration properties
        //PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        //propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));

        //In quartz.properties After the properties in the are read and injected, the object is initialized
        //propertiesFactoryBean.afterPropertiesSet();

        //Create schedulerfactorybean
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //factory.setQuartzProperties(propertiesFactoryBean.getObject());

        //Use data source, customize data source
        //factory.setDataSource(this.quartzDataSource());
        factory.setJobFactory(jobFactory);
        factory.setWaitForJobsToCompleteOnShutdown(true); // in this way, when spring is shut down, it will wait for all started quartz jobs to complete shutdown.
        factory.setOverwriteExistingJobs(false);
        factory.setStartupDelay(1);
        return factory;
    }


    /*
     *Get the instance of the scheduler through the schedulerfactorybean
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    @Bean
    public JobUtil JobUtil() {
        return new JobUtil();
    }
}