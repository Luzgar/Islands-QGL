import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GreeterTest {

	Greeter greeter;

	@Before
	public void defineContext() {
		greeter = new Greeter();
	}

	@Test
	public void sayHelloToSomeone() {
		String seb = greeter.sayHello("Sebastien");
		assertEquals("Hello, Sebastien!", seb);
	}

	@Test
	public void sayHelloToTheWorld() {
		String world = greeter.sayHello();
		assertEquals("Hello, World!", world);
	}

	@Test
	public void sayHelloEquivalence() {
		assertEquals(greeter.sayHello(), greeter.sayHello("World"));
	}

}
