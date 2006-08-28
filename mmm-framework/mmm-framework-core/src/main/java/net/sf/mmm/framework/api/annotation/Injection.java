/* $ Id: $ */
package net.sf.mmm.framework.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used for "dependency injection" in a
 * {@link net.sf.mmm.framework.api.annotation.Implementation "component implementation"}.<br>
 * It associates a field, method, constructor or parameter with a dependency to
 * a specific
 * {@link net.sf.mmm.framework.api.annotation.Specification component}. The
 * only recomended way for injections is using a public setter method taking a
 * single argument that is the
 * {@link net.sf.mmm.framework.api.annotation.Specification} of the required
 * component. This injection type must be supported by a legal
 * {@link net.sf.mmm.framework.muell.IocContainerIF container-framework}
 * implementation.<br>
 * The {@link net.sf.mmm.framework.muell.IocContainerIF container-framework} will
 * automatically set this dependency during the startup of a component's
 * {@link net.sf.mmm.framework.api.annotation.Lifecycle lifecycle}. The
 * framework must allow to configure a priority for component injection so
 * specific components such as a logger get injected before others.<br>
 * If a method is associated, it must be a setter that takes exactly one
 * argument. That argument or the type of the associated field must be the
 * {@link net.sf.mmm.framework.api.annotation.Specification} of another
 * component managed by the framework. Be aware that this can also be the
 * {@link net.sf.mmm.framework.muell.IocContainerIF} or component's that will be
 * individual for the component such as a logger or configuration. A single
 * component {@link net.sf.mmm.framework.api.annotation.Implementation} can have
 * multiple methods associated with this annotation.<br>
 * The following code snipplet gives a simple example of a single dependency
 * method:
 * 
 * <pre>
 * &#64;{@link Implementation}({@link InstantiationPolicy#SINGLETON})
 * public class MyComponentImpl implements IMyComponent {
 * ...
 * &#64;{@link Injection}
 * public void setLogger(Logger logger) { ... }
 * ...
 * }
 * </pre>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Injection {

}
