package inedo.buildmasterextensions.java;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import java.util.ArrayList;
import java.io.*;


public class jUnitAction {

	public static void main(String... args) {
		
		if (args.length == 0) {
			System.err.println("No arguments specified");
			System.exit(1);
			return;
		}

		try {
			Class.forName("junit.framework.JUnit4TestAdapter");
		} catch (ClassNotFoundException e) {
			System.err.println("JUnit4TestAdapter could not be loaded; jUnit 4.x must be available in classpath or extensions.");
			System.exit(1);
			return;
		}

		ArrayList<String> classNames = new ArrayList<String>();
		for (String arg : args) {
			if (arg.startsWith("@")) {
				try {
					BufferedReader in = new BufferedReader(new FileReader(arg.substring(1)));
					while ((arg = in.readLine()) != null) classNames.add(arg);
					in.close();
				} catch (FileNotFoundException e) {
					System.err.println(e.getMessage());
					return;
				} catch (IOException e) { 
					System.err.println(e);
					return;
				}
			}
			else 
				classNames.add(arg);
		}


		System.out.println("<TestResults>");
		for (String className : classNames) runTest(className.trim()	);
		System.out.println("</TestResults>");
	}

	static String escapeCdata(String s) {
		if (s==null) return "";
		return s.replace("]]>","]]]]><![CDATA[>");
	}
	
	static String escapeAttrib(String s) { 
		if (s==null) return "";
		return s
			.replace("&","&amp;")
			.replace("<","&lt;")
			.replace(">","&gt;")
			.replace("\"","&quot;")
			.replace("'","&apos;")
		; 
	}

	static ArrayList<String> readFile(String fileName) {
		ArrayList<String> lines = new ArrayList<String>();
		return lines;
	}

	static void runTest(String testClassName) {
		Result testResult; String testOutput;
		try {
			Class testClass = Class.forName(testClassName);

			PrintStream standardOut = System.out; PrintStream standardErr = System.err;

			ByteArrayOutputStream redirectedOut = new ByteArrayOutputStream();
			System.setOut(new PrintStream(redirectedOut));
			System.setErr(new PrintStream(redirectedOut));

			try  {  testResult = new JUnitCore().run( testClass ); }
			finally { System.setOut(standardOut); System.setErr(standardErr); }

			testOutput = redirectedOut.toString().trim();
		}
		catch (Throwable t) {
			testOutput = null;
			testResult = new Result();
			testResult.getFailures().add(new Failure(Description.createSuiteDescription(testClassName), t));
		}
		

		System.out.println(" <TestResult");
		System.out.println("    Class=\"" + escapeAttrib(testClassName) + "\"");
		System.out.println("    IgnoreCount=\"" + testResult.getIgnoreCount() + "\"");
		System.out.println("    RunCount=\"" + testResult.getRunCount() + "\"");
		System.out.println("    RunTime=\"" + testResult.getRunTime() + "\"");
		System.out.println("    >");
		if (testResult.getFailureCount() == 0) {
			System.out.println("  <Failures />");
		}
		else {
			System.out.println("  <Failures>");
			for (Failure fail : testResult.getFailures()) {
				System.out.println("   <Failure");
				System.out.println("      TestHeader=\"" + escapeAttrib(fail.getTestHeader()) + "\"");
				System.out.println("      Message=\"" + escapeAttrib(fail.getMessage()) + "\"");
				System.out.println("      ExceptionClass=\"" + escapeAttrib(fail.getException().getClass().getName())  + "\"");
				System.out.println("      />");
			}
			System.out.println("  </Failures>");
		}
		if (testOutput != null && testOutput.length() > 0)
		{
			System.out.print("  <TestOutput><![CDATA[");
			System.out.print(escapeCdata(testOutput));
			System.out.println("]]></TestOutput>");
		}
		System.out.println(" </TestResult>");
	}
}
