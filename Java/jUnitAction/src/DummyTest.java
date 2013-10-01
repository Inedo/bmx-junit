package inedo.buildmasterextensions.java;
import junit.framework.*;

/**
 * <code>JUnitCore</code> is a facade for running tests. It supports running JUnit 4 tests, 
 * JUnit 3.8.x tests, and mixtures. To run tests from the command line, run 
 * <code>java org.junit.runner.JUnitCore TestClass1 TestClass2 ...</code>.
 * For one-shot test runs, use the static method {@link #runClasses(Class[])}. 
 * If you want to add special listeners,
 * create an instance of {@link org.junit.runner.JUnitCore} first and use it to run the tests.
 * 
 * @see org.junit.runner.Result
 * @see org.junit.runner.notification.RunListener
 * @see org.junit.runner.Request
 */
public class DummyTest extends TestCase 
{
    public DummyTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {

    }

    protected void tearDown() throws Exception {

    }

    public void testTwoEqualsOne() {

        assertEquals( 2, 1 );
    }

    public void testOneEqualsOne() {

        assertEquals( 1, 1 );
    }


}
