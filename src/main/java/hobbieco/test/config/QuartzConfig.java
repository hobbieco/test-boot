package hobbieco.test.config;

import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.context.annotation.Configuration;

import hobbieco.test.common.R;
import hobbieco.test.schedule.TestJobA;
import hobbieco.test.schedule.TestJobB;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class QuartzConfig {

	@Resource(name="quartzScheduler")
	private StdScheduler quartzScheduler;
	
	@PostConstruct
	public void quartzSchedulerConfig() throws SchedulerException {
		log.debug("##########################################################");
		log.debug("quartzSchedulerConfig");
		log.debug("##########################################################");
		quartzScheduler.scheduleJob(testJobDetailA(),testJobTriggerA());
		quartzScheduler.scheduleJob(testJobDetailB(),testJobTriggerB());
		
		log.debug("quartz job pause test : stop JOB A");
		quartzScheduler.pauseJob(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()));
		log.debug("quartz job pause test : stop JOB B");
		quartzScheduler.pauseJob(JobKey.jobKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
		//quartzScheduler.pauseTrigger(TriggerKey.triggerKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
		
		
		
		return;
	}
	
	public JobDetail testJobDetailA() {
		JobBuilder jobBuilder = JobBuilder.newJob();
		jobBuilder.ofType(TestJobA.class);
		jobBuilder.storeDurably().withIdentity(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl());
		jobBuilder.withDescription("test quartz");
		return jobBuilder.build();
	}
	
	public Trigger testJobTriggerA() {
		ScheduleBuilder<CronTrigger> t = CronScheduleBuilder.cronSchedule("0/10 * * * * ?").inTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		//ScheduleBuilder<CronTrigger> t = null;
		Trigger trigger = TriggerBuilder.newTrigger().forJob(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())
				.withIdentity(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())
				.withSchedule(t)
				.build();
		return trigger;
	}
	
	public JobDetail testJobDetailB() {
		return JobBuilder.newJob(TestJobB.class)
				.storeDurably().withIdentity(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl())
				.withDescription("test quartz")
				.usingJobData("jobDetail", "value jobDetail")
				.build();
	}
	
	public Trigger testJobTriggerB() {
		return TriggerBuilder.newTrigger().forJob(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl())
				.withIdentity(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl())
				.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?").inTimeZone(TimeZone.getTimeZone("Asia/Seoul")))
				.usingJobData("trigger", "value trigger")
				.build();
	}
}
