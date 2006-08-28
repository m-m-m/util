/* $ Id: $ */
package net.sf.mmm.framework.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to associate a public non-arg method of a component
 * {@link net.sf.mmm.framework.api.annotation.Implementation} with one (or multiple)
 * lifecycle {@link net.sf.mmm.framework.api.annotation.LifecycleAction action}(s).
 * 
 * @see net.sf.mmm.framework.api.annotation.LifecycleAction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Lifecycle {

    /**
     * This method gets the lifecycle action(s) annotated in the according
     * method.
     * 
     * @return the lifecycle action.
     */
    LifecycleAction[] value();

}
