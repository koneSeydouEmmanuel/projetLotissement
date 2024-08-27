package com.ilot.utils.quartz.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysJob {
    private String  jobName;
    private String  jobGroup;
    private Class   jobClass;
    private String  cronExpression;
    private String  startTime;
    private String  nextFireTime;
    private String  previousFireTime;
    private Boolean mayFireAgain;
    private String typeJob;
    private String pathClass;
}
