package pit.kos.process.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import pit.kos.process.annotation.ProcessConfiguration;
import pit.kos.process.annotation.UseProcessFlow;

/**
 * @author Potr Kosmala
 *
 */
public abstract class ConfigurationProcess {

	private Map<Class<?>, Object> processMap;
	private Package packageConfigClass;
	private Set<Class<?>> processFlowClass;

	public ConfigurationProcess() {
		processMap = new HashMap<>();
		packageConfigClass = this.getClass().getPackage();
		Reflections reflections = new Reflections(packageConfigClass.getName());
		// TODO connect correct id flow to configuration
		processFlowClass = reflections.getTypesAnnotatedWith(UseProcessFlow.class);
		for (Class<?> clazz : processFlowClass) {
			initHandlers(clazz, this);
		}
	}
	
	public Class<?> getConfiguretion(Set<Class<?>> processFlowConfiguration, String configuration) {
		for (Class<?> clazz : processFlowConfiguration) {
			ProcessConfiguration annotation = clazz.getAnnotation(ProcessConfiguration.class);
			if (configuration.equals(annotation.configuration())) {
				return clazz;
			}
		}
		return null;
	}
	
	public <T> T get(Class<T> clazz) {
		return clazz.cast(processMap.get(clazz));
	}

	private <T> void initHandlers(Class<T> clazz, ConfigurationProcess clazzConfig) {
		if (processMap.get(clazz) == null && !Enhancer.isEnhanced(clazz)) {
			final Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clazz);
			Handler handler = new Handler(clazzConfig);
			enhancer.setCallbackType(Handler.class);
			final Class<?> proxyClass = enhancer.createClass();
			Enhancer.registerCallbacks(proxyClass, new Callback[] { handler });
			processMap.put(clazz, getCastClass(proxyClass));
		}
	}
	
	private <T> Object getCastClass(Class<T> clazz) {
		try {
			return clazz.cast(clazz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
