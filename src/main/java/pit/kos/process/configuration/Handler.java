/**
 * 
 */
package pit.kos.process.configuration;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author yogibubu
 * @param <T>
 *
 */
public class Handler implements MethodInterceptor {

	/**
	 * private T original;
	 * 
	 * public Handler(T original) { this.original = original; }
	 **/

	@Override
	public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		try {
			System.out.println("BEFORE");
			return methodProxy.invokeSuper(o, args);
		} finally {
			System.out.println("AFTER");
		}
	}
}
