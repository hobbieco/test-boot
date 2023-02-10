package hobbieco.test.schedule;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestScheduler {
	
	@Autowired
	private ThreadPoolTaskScheduler pool;
	
	//@Scheduled(cron="*/5 * * * * *")
	public void testA() {
		log.debug("A - test schedule run");
		log.debug("A - ThreadPoolTaskScheduler active count : {}",pool.getActiveCount());
		ScheduledThreadPoolExecutor executor = pool.getScheduledThreadPoolExecutor();
		log.debug("A - ThreadPoolTaskScheduler Pool Executor active count : {}",executor.getActiveCount());
		log.debug("A - ThreadPoolTaskScheduler Pool Executor Queue size : {}",executor.getQueue().size());
	}

	//@Scheduled(cron="*/10 * * * * *")
	public void testB() {
		log.debug("B - test schedule run");
		log.debug("B - ThreadPoolTaskScheduler active count : {}",pool.getActiveCount());
		ScheduledThreadPoolExecutor executor = pool.getScheduledThreadPoolExecutor();
		log.debug("B - ThreadPoolTaskScheduler Pool Executor active count : {}",executor.getActiveCount());
		log.debug("B - ThreadPoolTaskScheduler Pool Executor Queue size : {}",executor.getQueue().size());
		pool.destroy();
	}
}
