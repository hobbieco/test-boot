package hobbieco.test.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomUtil {
	
	private Random random = new Random();
	
	private List<String> str = new ArrayList<>();
	
	public String getRandomString(int number) {
		int idx = number % 10;
		if (str.size() < 10) {
			str.add(getTinyRandomString());
			str.add(getTinyRandomString());
			
			str.add(getSmallRandomString());
			str.add(getSmallRandomString());
			str.add(getSmallRandomString());
			str.add(getSmallRandomString());
			str.add(getSmallRandomString());
			str.add(getSmallRandomString());
			
			str.add(getMediumRandomString());
			str.add(getMediumRandomString());
		}
		
		return str.get(idx);
	}

	public String getTinyRandomString() {
		return RandomStringUtils.randomAlphanumeric(3500, 3800);
	}
	
	public String getSmallRandomString() {
		return RandomStringUtils.randomAlphanumeric(49000, 50000);
	}
	
	public String getMediumRandomString() {
		return RandomStringUtils.randomAlphanumeric(490000, 500000);
	}
	
	public String getBigRandomString() {
		return RandomStringUtils.randomAlphanumeric(990000, 1000000);
	}
	
	public String getGreatRandomString() {
		return RandomStringUtils.randomAlphanumeric(9990000, 10000000);
	}
	
	public int getRandom() {
		return random.nextInt();
	}
	
	public int getRandom(int range) {
		return random.nextInt(range);
	}
}
