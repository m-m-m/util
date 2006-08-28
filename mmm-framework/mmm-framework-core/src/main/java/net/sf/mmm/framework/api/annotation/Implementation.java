/* $ Id: $ */
package net.sf.mmm.framework.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark the implementation of a component
 * {@link net.sf.mmm.framework.api.annotation.Specification specification}.<br>
 * Be aware that an implementation may implement more than one component
 * {@link net.sf.mmm.framework.api.annotation.Specification specification}. In any case
 * the {@link net.sf.mmm.framework.muell.IocContainerIF} must ensure that the given
 * {@link net.sf.mmm.framework.api.annotation.InstantiationPolicy} is satisfied. So e.g.
 * for a {@link net.sf.mmm.framework.api.annotation.InstantiationPolicy#SINGLETON}
 * implementation only one instance will be created even if multiple component
 * {@link net.sf.mmm.framework.api.annotation.Specification specifications} are
 * implemented.
 * 
 * @see net.sf.mmm.framework.api.annotation.Injection
 * @see net.sf.mmm.framework.api.annotation.Lifecycle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Implementation {

    /**
     * This method gets the annotated instantiation policy for this component
     * implementation. This policy decides when to create a new instance of the
     * component and when to reuse an existing instance.
     * 
     * @return the instantiation policy.
     */
    InstantiationPolicy value();

}
