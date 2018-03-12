package pit.kos.process.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import pit.kos.process.annotation.ProcessAfter;
import pit.kos.process.annotation.ProcessBefore;
import pit.kos.process.annotation.ProcessDef;

/**
 * @author Piotr Kosmala
 *
 */
public class Handler implements MethodInterceptor {
	
	private ConfigurationProcess config;
	private Map<String, Method> mapMethod;
	
	public Handler(ConfigurationProcess config) {
		this.config = config;
		mapMethod = new HashMap<>();
		for (Method m: config.getClass().getDeclaredMethods()) {
			for(Annotation annotation : m.getDeclaredAnnotations()){
				 if(annotation instanceof ProcessDef){
					 ProcessDef process = (ProcessDef) annotation;
					 mapMethod.put(process.id(), m);
				 }
			}
		}
	}

	@Override
	public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		try {
			if (method.isAnnotationPresent(ProcessBefore.class)) {
				ProcessBefore pb = method.getAnnotation(ProcessBefore.class);
				mapMethod.get(pb.id()).invoke(config, args);
			}
			return methodProxy.invokeSuper(o, args);
		} finally {
			if (method.isAnnotationPresent(ProcessAfter.class)) {
				ProcessAfter pa = method.getAnnotation(ProcessAfter.class);
				mapMethod.get(pa.id()).invoke(config, args);
			}
		}
	}
}
