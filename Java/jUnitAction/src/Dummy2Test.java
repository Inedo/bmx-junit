package inedo.buildmasterextensions.java;

// http://today.java.net/pub/a/today/2006/12/07/junit-reloaded.html

import org.junit.Test;
import static org.junit.Assert.*;

public class Dummy2Test
{

	@Test
    public void twoEqualsOne() {

        assertEquals( 2, 1 );
    }

	@Test
    public void testOneEqualsOne() {

        assertEquals( 1, 1 );
		System.out.println("POOOOOP <  ]]> <asf");
    }

	@Test
    public void printHelloWorld() {

        System.out.println("Hello World.");
    }



}
