package pit.kos.process.main;

import pit.kos.process.annotation.ProcessAfter;
import pit.kos.process.annotation.ProcessBefore;
import pit.kos.process.annotation.UseProcessFlow;

/**
 * @author Piotr Kosmala
 *
 */
@UseProcessFlow(configuration="databases")
public class Main {
	
	public Main() {
		super();
	}

	private final static ConfigurationProcessApp app = new ConfigurationProcessApp(); 
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Main main = app.get(Main.class);
		//main.testProcess("Before 1");
		
		Main mm = new Main();
		mm.testProcess("Before 1");
	}
	
	@ProcessAfter(id="process1")
	@ProcessBefore(id="process2")
	public void testProcess(String value) {
		System.out.println(value);
	}
	
	@ProcessAfter(id="process2")
	@ProcessBefore(id="process1")
	public void processAfter(String value) {
		System.out.println(value);
	}
	
	@ProcessAfter
	@ProcessBefore
	public void processBeforeAfter(String value) {
		System.out.println(value);
	}
	
}
