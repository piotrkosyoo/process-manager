package pit.kos.process.annotation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Piotr Kosmala
 *
 */
@Retention(RUNTIME)
@Target({ FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, PARAMETER })
public @interface ProcessBefore {
	public String id() default "";
}
