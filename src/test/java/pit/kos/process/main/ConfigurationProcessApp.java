package pit.kos.process.main;

import pit.kos.process.annotation.ProcessDef;
import pit.kos.process.annotation.ProcessConfiguration;
import pit.kos.process.configuration.ConfigurationProcess;

/**
 * @author Piotr Kosmala
 *
 */
@ProcessConfiguration(configuration = "databases")
public class ConfigurationProcessApp extends ConfigurationProcess {

	public ConfigurationProcessApp() {
		super();
	}
	
	@ProcessDef(id="process1")
	public void processOne(String name) {
		System.out.println("proces1");
	}
	
	@ProcessDef(id="process2")
	public void processTwo(String name) {
		System.out.println("proces2");
	}
}
