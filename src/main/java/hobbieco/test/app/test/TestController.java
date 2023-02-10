package hobbieco.test.app.test;

import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hobbieco.test.app.test.service.TestService;
import hobbieco.test.common.CommonUtil;
import hobbieco.test.common.R;
import hobbieco.test.config.QuartzConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestController {

	@Resource(name="commonUtil")
	private CommonUtil commonUtil;
	
	@Resource(name="testService")
	private TestService service;
	
	@Resource(name="quartzScheduler")
	private StdScheduler scheduler;
	
	@Resource(name="quartzConfig")
	private QuartzConfig config;
	
	
	@RequestMapping(value="/test/test",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> test(HttpServletRequest request, HttpServletResponse response) {
		log.debug("call /test/test");
		Map<String,Object> result = service.test();
		return result;
	}
	
	@RequestMapping(value="/test/json",method= {RequestMethod.GET,RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String,Object> testJson(HttpServletRequest request, HttpServletResponse response) {
		log.debug("call /test/json");
		JSONObject result = service.testJson(request);
		return result.toMap();
	}
	
	@RequestMapping(value="/test/schedule/update")
	@ResponseBody
	public String testSchedule() {
		log.debug("call /test/schedule/update");
		try {
			// cron validate
			boolean check = CronExpression.isValidExpression("a");
			log.debug("tes - cron valid : {}",check);
			Trigger trigger = TriggerBuilder.newTrigger().forJob(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()))
					.withIdentity(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())
					.withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?").inTimeZone(TimeZone.getTimeZone("Asia/Seoul")))
					.build();
			if (scheduler.checkExists(trigger.getKey())) {
				scheduler.rescheduleJob(trigger.getKey(), trigger);
			} else {
				scheduler.scheduleJob(trigger);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "check log";
	}
	
	@RequestMapping(value="/test/schedule/stop")
	@ResponseBody
	public String testScheduleStop() {
		log.debug("call /test/schedule/stop");
		try {
			log.debug("check before job : {}",scheduler.checkExists(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			log.debug("check before trigger : {}",scheduler.checkExists(TriggerKey.triggerKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			scheduler.deleteJob(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()));
			//scheduler.pauseTrigger(TriggerKey.triggerKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()));
			//scheduler.pauseJob(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()));
			
			scheduler.deleteJob(JobKey.jobKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
			//scheduler.pauseTrigger(TriggerKey.triggerKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
			//scheduler.pauseJob(JobKey.jobKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
			log.debug("check after : {}",scheduler.checkExists(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			for (String key : scheduler.getPausedTriggerGroups()) {
				log.debug(key);
			}
			log.debug("trigger status : {}",scheduler.getTriggerState(TriggerKey.triggerKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			log.debug("trigger status : {}",scheduler.getTriggerState(TriggerKey.triggerKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl())));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "check log";
	}
	
	@RequestMapping(value="/test/schedule/start")
	@ResponseBody
	public String testScheduleStart() {
		log.debug("call /test/schedule/start");
		try {
			log.debug("check before job : {}",scheduler.checkExists(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			log.debug("check before trigger : {}",scheduler.checkExists(TriggerKey.triggerKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			scheduler.scheduleJob(config.testJobDetailA(), config.testJobTriggerA());
			//scheduler.resumeTrigger(TriggerKey.triggerKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()));
			//scheduler.resumeJob(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl()));
			
			scheduler.scheduleJob(config.testJobDetailB(), config.testJobTriggerB());
			//scheduler.resumeTrigger(TriggerKey.triggerKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
			//scheduler.resumeJob(JobKey.jobKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl()));
			log.debug("check after : {}",scheduler.checkExists(JobKey.jobKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			for (String key : scheduler.getPausedTriggerGroups()) {
				log.debug(key);
			}
			log.debug("trigger status : {}",scheduler.getTriggerState(TriggerKey.triggerKey(R.Schedule.TEST_A.getScheduleNm(),R.Schedule.TEST_A.getScheduleCl())));
			log.debug("trigger status : {}",scheduler.getTriggerState(TriggerKey.triggerKey(R.Schedule.TEST_B.getScheduleNm(),R.Schedule.TEST_B.getScheduleCl())));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return "check log";
	}
}
