package hobbieco.test.schedule;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJobA implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("test quartz job ~~~~~ AAAAAAAAAAAAAAAAAAAA");
		
	}

}
