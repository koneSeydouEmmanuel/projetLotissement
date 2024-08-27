package com.ilot.utils.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * Create a job instance factory to solve the spring injection problem.
 * If you use the default, the @Autowired of spring will not be able to inject
 *
 */
@Component
public class JobFactory extends AdaptableJobFactory {
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //Call the method of the parent class
        Object jobInstance = super.createJobInstance(bundle);
        //Carry out injection
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

}