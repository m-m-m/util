/* $ Id: $ */
package net.sf.mmm.framework.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark the specification of a component. That
 * specification should be a well documented JAVA interface defining the API of
 * the component.<br>
 * <p>
 * Definition: <em>A component is a self-contained, reuseable, replaceable,
 * composeable and dynamically bound piece of software following the "separation
 * of concerns" idea.
 * </em>
 * </p>
 * <br>
 * <p>
 * Here is a list of examples for components: search-engine, persistence-layer,
 * billing-service, connection-pool, uri-formatter, etc. The following list
 * gives examples for what is NOT a component: atomic types (such as String,
 * Integer, ...), entity types, simple data sets (JAVA Beans), etc.
 * </p>
 * <br>
 * In a diffrenet context such component may be called a service, factory,
 * manager, etc. Anyways in this context it is always called a component.<br>
 * For the specification of a component there should be one (or multiple)
 * {@link net.sf.mmm.framework.api.annotation.Implementation implementation}(s)
 * that fulfill(s) the specification.<br>
 * A {@link net.sf.mmm.framework.muell.IocContainerIF container-framework} is used to
 * manage the components following the principals of "inversion of control"
 * (IoC) and "dependecy injection".<br>
 * So the framework...
 * <ul>
 * <li>...decides which
 * {@link net.sf.mmm.framework.api.annotation.Implementation} to use for which
 * {@link Specification}.</li>
 * <li>...instantiates the component
 * {@link net.sf.mmm.framework.api.annotation.Implementation}s.</li>
 * <li>...manages the component's
 * {@link net.sf.mmm.framework.api.annotation.Lifecycle}.</li>
 * <li>...{@link net.sf.mmm.framework.api.annotation.Injection injects}
 * dependent components into a component
 * {@link net.sf.mmm.framework.api.annotation.Implementation}.</li>
 * </ul>
 * This means that the
 * {@link net.sf.mmm.framework.api.annotation.Implementation} does not need to
 * worry about how to lookup dependent components (logger, configuration, other
 * services, ...) or when to start and stop.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Specification {

}
