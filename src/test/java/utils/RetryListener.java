package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {

	int count =0;
	int maxRetry=2;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(count<maxRetry) {
			count++;
			return true;
		}
		
		return false;
	}

}
