package com.hm.SSI.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskTest {
	
	private static int i = 0;
	
	@Scheduled(cron = "*/1 * * * * ? ")
	public void test(){
		System.out.println(++i);
	}
	
}
