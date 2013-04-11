/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link ComponentSpecification} is used to annotate the specification (should be an interface) of a
 * component. It acts only for the purpose of documentation and has no functional impact. However it will say
 * that you can get one (or {@link #plugin() multiple}) instance(s) of this specification via
 * {@link javax.inject.Inject injection}.<br/>
 * If you find this:
 * 
 * <pre>
 * &#64;{@link ComponentSpecification}
 * public interface MyComponent { ... }
 * </pre>
 * 
 * and
 * 
 * <pre>
 * &#64;{@link ComponentSpecification}({@link #plugin() plugin}=true)
 * public interface MyPlugin { ... }
 * </pre>
 * 
 * Then you can simply do this in your code:
 * 
 * <pre>
 * &#64;{@link javax.inject.Named}
 * public class MyClass {
 *   ...
 *   &#64;{@link javax.inject.Inject}
 *   public void setMyComponent(MyComponent component) { ... }
 *   ...
 *   &#64;{@link javax.inject.Inject}
 *   public void setMyPlugins({@link java.util.List}&lt;MyPlugins&gt; plugins) { ... }
 *
 * }
 * </pre>
 * 
 * For simplicity all implementations of such component in this project have to be
 * {@link javax.inject.Singleton stateless} and thread-safe. Otherwise this has to be documented in an
 * explicit WARNING.
 * 
 * @see net.sf.mmm.util.component.api.Ioc
 * @see org.springframework.context.annotation.AnnotationConfigApplicationContext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
// TODO should be in api package, will move this for 4.0.0
public @interface ComponentSpecification {

  /**
   * <code>true</code> if multiple implementations of this "component" are (potentially) expected at a time.
   * In this case it is typically some sort of plugin that should be {@link javax.inject.Inject injected} to a
   * list of the annotated type.
   */
  boolean plugin() default false;

}
