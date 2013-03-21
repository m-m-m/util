/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This interface only exists for documentation purpose!<br/>
 * <em>CDI</em> is a shortcut for <em>Context and Dependency Injection</em> and is a java standard supporting
 * the {@link Ioc IoC} pattern. The annotations from JSR 250 and JSR 330 are combined to configure component
 * implementations. A {@link IocContainer CDI framework} can understand these standardized annotations and
 * assembles your components performing the dependency injection. We strongly recommend to use <a
 * href="http://www.springframework.org">spring</a> as {@link IocContainer} but there are also other
 * implementations like JBoss Seam, Google Guice (GIN for GWT)/Eclipse SiSu, etc.<br/>
 * Here is an example how everything works together:
 * 
 * <pre>
 * public interface MyComponent {
 *   void doSomething();
 * }
 *
 * public interface OtherComponent {
 *   void doSomethingElse();
 * }
 *
 * //this annotation declares this as a component that is configured by the {@link IocContainer}...
 * &#64;{@link javax.inject.Named}
 * public class MyComponentImpl implements MyComponent {
 *
 *   private OtherComponent otherComponent;
 *
 *   // this annotation defines a dependency to OtherComponent. The {@link IocContainer} verifies that there is exactly
 *   // one component available that implements OtherComponent and that gets automatically injected here...
 *   &#64;{@link javax.inject.Inject}
 *   public void setOtherComponent(OtherComponent otherComponent) {
 *     this.otherComponent = otherComponent;
 *   }
 *
 *   // this annotation defines an initializer method that is called after all dependencies have been injected (e.g.
 *   // setOtherComponent must have been called before).
 *   &#64;{@link javax.annotation.PostConstruct}
 *   public void initialize() {
 *     // ... setup the component here ...
 *   }
 *
 *   // this annotation defines an destroy method that is called when the application is shut down...
 *   &#64;{@link javax.annotation.PostConstruct}
 *   public void dispose() {
 *     // ... free resources, release thread-pool, etc. ...
 *   }
 *
 *   public void doSomething() {
 *     // do something and maybe then
 *     this.otherComponent.doSomethingElse();
 *   }
 *
 * }
 *
 * // and finally to complete the example...
 * &#64;{@link javax.inject.Named}
 * public class OtherComponentImpl implements OtherComponent {
 *
 *   public void doSomethingElse() {
 *     // here we do something else...
 *   }
 *
 * }
 * </pre>
 * 
 * Even though there are different variants how to do it, we strongly recommend using public setter injection
 * as it is the best approach for testability, debugging and getting most helpful messages in case of an
 * error. Also we provide {@link net.sf.mmm.util.component.base.AbstractLoggableComponent} that you can extend
 * from.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface Cdi {

  /**
   * A <em>CDI name</em> is the {@link javax.inject.Named name} of a {@link ComponentSpecification component}
   * for {@link Cdi CDI}.<br/>
   * The regular implementation will be annotated with &#64;{@link javax.inject.Named}
   * (MyComponentInterface.CDI_NAME).<br/>
   * If you want to replace this implementation you can do so by defining your own spring-bean with the same
   * bean-id at a higher level (in a spring-XML file directly or indirectly importing the component-scan for
   * the default implementation) or even using the same annotation (if you know what you are doing). Your
   * replacement may or may not extend the default implementation.<br/>
   * <b>NOTE:</b><br/>
   * This constant is only for documentation purpose. Please never use it in your code.
   */
  String CDI_NAME = null;

  /**
   * We strongly believe in the {@link Ioc} pattern and try to avoid the keyword <em>static</em> without
   * <em>final</em>. Books like <em>Design Patterns. Elements of Reusable Object-Oriented Software.</em>
   * introduced various patterns like <em>Singleton</em> (or maybe <em>AbstractFactory</em>) that make use of
   * <em>static</em> access and lead to inflexible and bad design. Instead {@link Ioc} is the pattern of
   * choice to get access to singletons or factories. However, the problem is that Java does NOT support
   * {@link Ioc} in its core. So for a small and simple application it might be over-complicated to use it.<br/>
   * <br/>
   * Therefore we picked a design that is the best compromise between easy access and flexibility:
   * <ul>
   * <li>We strictly follow the {@link Ioc} design which allows extension and customization.</li>
   * <li>Additionally we providing static <code>getInstance()</code> for convenience.</li>
   * </ul>
   * You have the choice. If you just want to use {@link net.sf.mmm.util.lang.api.StringUtil} it is fine to
   * use {@link net.sf.mmm.util.lang.base.StringUtilImpl#getInstance()}. However if you want to make use of
   * many components of this project and decide for {@link Ioc} you can get all utils and other components
   * injected. This gives you the final freedom to replace or extend components without patching our code.<br/>
   * <b>NOTE:</b><br/>
   * This constant is only for documentation purpose. Please never use it in your code.
   */
  String GET_INSTANCE = null;

}
