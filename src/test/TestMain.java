package test;

import java.util.ArrayList;
import java.util.List;

public class TestMain {

	public static void main(String[] args) {
		List<ITestCase> testcases = new ArrayList<ITestCase>();
		// prepare test cases.
		testcases.add(new IndividualCrudInRam());
		
		// launch test cases.
		for(ITestCase i: testcases){
			System.out.println("==Test case: " + i.getClass().getName());
			if(i.prepare()){
				i.test();
			}
			else{
				break;
			}
		}
	}

}