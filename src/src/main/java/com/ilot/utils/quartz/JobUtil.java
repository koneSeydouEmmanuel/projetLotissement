package com.ilot.utils.quartz;

import com.ilot.utils.Utilities;
import com.ilot.utils.quartz.contract.SysJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Component
public class JobUtil {
    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public JobUtil() {
        // dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * Create a new task
     */
    public String addJob(SysJob job) throws Exception {

        String jobName  = job.getJobName();
        String jobGroup = job.getJobGroup();
        Class  jobClass = job.getJobClass();

        if (!CronExpression.isValidExpression(job.getCronExpression())) {
            return "illegal cron expression"; // the format of the expression is incorrect
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        JobDetail  jobDetail  = null;
        jobDetail = scheduler.getJobDetail(new JobKey(jobName, jobGroup));
        boolean hasJobDetail = jobDetail != null;

        if (!hasJobDetail) {
            //Build job information
            jobDetail = JobBuilder
                    .newJob(jobClass)
                    .withIdentity(jobName, jobGroup)
                    .build();
        }

        //Expression scheduling Builder (that is, when the task is executed, not immediately)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                .cronSchedule(job.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        //Construct a new trigger according to the new cron-expression expression
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(jobName, jobGroup)
                .startNow()
                .withSchedule(scheduleBuilder)
                .build();

        if (hasJobDetail) {
            scheduler.rescheduleJob(triggerKey, trigger);
        } else {
            scheduler.scheduleJob(jobDetail, trigger);
        }

        // pauseJob(job.getJobName(), job.getJobGroup());
        return "success";
    }

    /**
     * Get job status
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.pauseJob(jobKey);
    }

    public List<SysJob> getJobList() throws SchedulerException {
        List<SysJob> jobList = new ArrayList<>();
        for (String groupName : scheduler.getJobGroupNames()) {

            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                String jobName  = jobKey.getName();
                String jobGroup = jobKey.getGroup();

                //get job's trigger
                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                if (Utilities.isNotEmpty(triggers)) {
                    Trigger trigger          = triggers.get(0);
                    Date    nextFireTime     = trigger.getNextFireTime();
                    Date    previousFireTime = trigger.getPreviousFireTime();
                    Boolean mayFireAgain     = trigger.mayFireAgain();
                    String  cronExpression   = null;
                    String  typeJob          = "";
                    String  pathClass        = "";

                    if ((Utilities.areEquals(jobName, "Job_AlertNotify")) || jobName.contains("Job_AlertNotify") || jobName.contains("CRITICITE") || jobName.contains("Job_AlertSeuilCriticite")){
                        typeJob = "evenementiel";
                        pathClass = "com.Ilot.cron.JobAlertNotify";
                    }
                    if ((Utilities.areEquals(jobName, "Job_SondeRealtimeDashboard"))){
                        pathClass = "com.Ilot.cron.JobSondeRealtimeDashboard";
                    }
                    if ((Utilities.areEquals(jobName, "Job_KnowStatusPing"))){
                        pathClass = "com.Ilot.cron.JobKnowStatusPingOfSonde";
                    }
                    if (trigger instanceof CronTrigger) {
                        cronExpression = ((CronTrigger) trigger).getCronExpression();
                    }

                    System.out.println("[jobName] : " + jobName + " [groupName] : " + jobGroup + " - " + nextFireTime);

                    SysJob sysJob = SysJob.builder()
                            .jobGroup(jobGroup)
                            .jobName(jobName)
                            .nextFireTime(nextFireTime != null ? dateTimeFormat.format(nextFireTime) : null)
                            .previousFireTime(previousFireTime != null ? dateTimeFormat.format(previousFireTime) : null)
                            .cronExpression(cronExpression)
                            .mayFireAgain(mayFireAgain)
                            .typeJob(typeJob)
                            .pathClass(pathClass)
                            .build();
                    jobList.add(sysJob);
                }
            }
        }

        return jobList;
    }

    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        scheduler.resumeJob(jobKey);
    }

    public boolean interruptJob(String jobName, String jobGroup) throws SchedulerException {
        // JobKey jobKey = new JobKey(jobName, jobGroup);
        // scheduler.interrupt(jobKey);

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        if (scheduler.checkExists(triggerKey)) {
            scheduler.unscheduleJob(triggerKey); // trigger + job
            return false;
        }
        return false;
    }
}
