package test.testmoj;

import pit.kos.process.annotation.Process;
import pit.kos.process.annotation.ProcessConfiguration;
import pit.kos.process.configuration.ConfigurationProcess;

@ProcessConfiguration(configuration = "databases")
public class ConfigurationProcessApp extends ConfigurationProcess {

	public ConfigurationProcessApp() {
		super();
	}
	
	@Process(id="saveCustomer")
	public void saveCustomer() {
		
	}
}
