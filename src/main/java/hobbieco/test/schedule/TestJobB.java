package hobbieco.test.schedule;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJobB extends QuartzJobBean {
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.debug("test quartz job ~~~~~ BBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//		JobDataMap dataMap = context.getMergedJobDataMap();
//		for (String key : dataMap.getKeys()) {
//			log.debug("{} : {}",key,dataMap.getString(key));
//		}
//		log.debug("test non key get value : {}",dataMap.get("t"));
//		dataMap.putIfAbsent("test", "value test");
	}

	

}
