package pit.kos.process.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.objenesis.ObjenesisHelper;
import org.reflections.Reflections;

import com.google.common.cache.Cache;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import pit.kos.process.annotation.UseProcessFlow;

public abstract class ConfigurationProcess {

	private Map<Class<?>, Object> processMap;
	private Package packageConfigClass;
	private Set<Class<?>> processFlowClass;

	public ConfigurationProcess() {
		processMap = new HashMap<>();
		packageConfigClass = this.getClass().getPackage();
		Reflections reflections = new Reflections(packageConfigClass.getName());
		processFlowClass = reflections.getTypesAnnotatedWith(UseProcessFlow.class);
		for (Class<?> clazz : processFlowClass) {
			initHandlers(clazz);
		}
	}

	public <T> T get(Class<T> clazz) {
		return clazz.cast(processMap.get(clazz));
	}

	private <T> void initHandlers(Class<T> clazz) {
		if (processMap.get(clazz) == null && !Enhancer.isEnhanced(clazz)) {

			final Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clazz);
			Handler handler = new Handler();
			enhancer.setCallbackType(Handler.class);

			final Class<?> proxyClass = enhancer.createClass();
			Enhancer.registerCallbacks(proxyClass, new Callback[] { handler });

			// Object real = getCastClass(clazz); with constructor
			// Object proxy = (Object) Enhancer.create(clazz, new Handler(real));

			// processMap.put(clazz, (T) ObjenesisHelper.newInstance(proxyClass)); // with constructor
			processMap.put(clazz, getCastClass(proxyClass));	// without constuktor
		}
	}

	private <T> Object getCastClass(Class<T> clazz) {
		try {
			return clazz.cast(clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
