/**
 * A simple class to say hello
 */
public class Greeter {

	/**
	 * Say hello to the world
	 * @return
	 */
	public String sayHello() {
		return sayHello("World");
	}

	/**
	 * Say hello to a given someone
	 * @param who
	 * @return
	 */
	public String sayHello(String who) {
		return "Hello, " + who + "!";
	}

}
