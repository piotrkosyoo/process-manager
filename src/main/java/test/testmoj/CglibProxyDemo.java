package test.testmoj;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
 
import java.lang.reflect.Method;

public class CglibProxyDemo {
	 
   
 
    public static void main(String[] args){
        Original original = new Original();
        MethodInterceptor handler = new Handler(original);
        Original f = (Original) Enhancer.create(Original.class,handler);
        f.originalMethod("Hallo");
    }
   }


 class Handler implements MethodInterceptor {
    private final Original original;

    public Handler(Original original) {
        this.original = original;
    }

    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("BEFORE");
        method.invoke(original, args);
        System.out.println("AFTER");
        return null;
    }
}