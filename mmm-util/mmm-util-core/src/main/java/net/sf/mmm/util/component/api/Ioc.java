/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

/**
 * This interface is only used for documentation of what is meant by the terms <em>IoC</em>.<br>
 * <em>IoC</em> is a shortcut for <em>Inversion of Control</em> that is an excellent design pattern supporting
 * <em>SoC</em> (separation of concerns). If a component <em>A</em> has a dependency on some other component
 * <em>B</em> it simply aggregates the interface of the other component B and typically offers a public setter
 * taking the interface of B as argument. This way the component A has no knowledge about how to instantiate
 * or configure the component B. Instead a central {@link IocContainer} manages all components and takes care
 * of these dependencies and {@link javax.inject.Inject injects} the instance of B into A. This process is
 * called <em>dependency injection</em> (<em>DI</em>). Therefore the implementation of component B can be
 * replaced without touching component A. This pattern is the key to a maintainable architecture.<br/>
 * <br/>
 * <b>Important:</b><br/>
 * Please see {@link Cdi CDI} for the details how to realize the {@link Ioc IoC} pattern in Java.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface Ioc {

  // nothing to add, just documentation...

}
