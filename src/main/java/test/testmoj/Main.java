package test.testmoj;

import pit.kos.process.annotation.ProcessBefore;
import pit.kos.process.annotation.UseProcessFlow;

@UseProcessFlow(definition="databases") // connect definition to configuration 
public class Main {

	private final static ConfigurationProcessApp app = new ConfigurationProcessApp(); 
	// create proxy 
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Main main = app.get(Main.class);
		main.processBefore("1");
		main.processBefore("2");
		main.processBefore("3");
	}
	
	@ProcessBefore
	public void processBefore(String value) {
		System.out.println(value);
	}

	public Main() {
		super();
		System.out.println("Constructor");
	}
	
	

}
