package il.ac.huji.todolist.test;

import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
//		TestSuite suite = new TestSuite(AllTests.class.getName());
//		//$JUnit-BEGIN$
//		suite.addTestSuite(TodoListManagerTest.class);
//		//$JUnit-END$
//		return suite;
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}

}
