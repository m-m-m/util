/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.api;

/**
 * This interface only exists for documentation purpose!<br>
 * <em>API</em> is a shortcut for <em>Application Programming Interface</em> and identifies all types required
 * to access a {@link net.sf.mmm.util.component.api.ComponentSpecification component}. The main idea is to
 * abstract from the implementation of the component so it gets replaceable and the design is maintainable. <br>
 * The most important type in Java to define an API is the {@link Class#isInterface() interface}. However an
 * API may also consist of {@link Enum} types or {@link net.sf.mmm.util.pojo.api.Pojo} {@link Class classes}
 * and {@link net.sf.mmm.util.lang.api.Datatype}s that are accepted as arguments by an interface. The actual
 * <em>API</em> of a {@link net.sf.mmm.util.component.api.ComponentSpecification component} is therefore the
 * combination of all these types. It has to be <em>self-contained</em> what means that it only contains
 * references to types legally considered as part of the <em>API</em>. With references we are NOT talking
 * about JavaDoc-links. <br>
 * A typical negative example is an interface that references objects from the implementation. That is bad
 * design as the idea of the <em>API</em> is to abstract from the implementation. <br>
 * Please also note that the <em>API</em> will typically have references to common java
 * {@link net.sf.mmm.util.lang.api.Datatype}s such as {@link String}, {@link Long}, etc. as well as
 * {@link java.util.Collection}s especially {@link java.util.List}. All these types are considered as legal
 * parts that do NOT influence if <em>API</em> is considered as <em>self-contained</em>. <br>
 * We strongly recommend to define sub-packages named <em>api</em> for your components that define the actual
 * <em>API</em> (see Developer-Guide of this project). This allows easy checking if your code is following
 * architectural guidelines via the full-qualified references (typically in import statements). Within this
 * context it is also important to consider dependencies between components. Therefore it is very important to
 * consider the following guidelines:
 * <ul>
 * <li>The application architecture has to define the components AND the dependencies between them.</li>
 * <li>You need to distinguish between the technical architecture defining application layers and their
 * dependencies and the business architecture defining slices (business components that are orthogonal to the
 * layers).</li>
 * <li>Cyclic dependencies between components indicate a bad design and are prohibited.</li>
 * <li>The proper tailoring of a large scope into components is the key to a maintainable application. It is
 * easy to fix a bad component implementation compared to a bad architecture design. This is the main task of
 * the architect and requires a lot of experience that can not be expressed in simple instructions. There are
 * good books on this topic but the most important skills can only be learned by doing (including doing
 * mistakes).</li>
 * <li>The code has to follow these rules. All types of a component should only reference types of another
 * component that are part of the <em>API</em> and that follow the allowed dependencies defined by the
 * architecture.</li>
 * <li>To ensure that these rules are followed in a large software project, you need tools to support you.
 * Such tools need to show warnings to the developers if they violate the architecture.</li>
 * </ul>
 * Following these rules is key to build high quality software.
 * 
 * @see Cdi
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface Api {

  /**
   * This is used to mark interfaces of the {@link Api API} that may potentially be <em>extended</em>. With
   * extended we mean that new methods will be added. This is no problem for the user of the interface but for
   * implementors. To prevent implementors from incompatibilities on updates of components provided by this
   * project, we provide abstract base implementations in the {@code base} packages that are located as
   * siblings of the {@code api} packages. Typically that {@link Class} is named like the interface with
   * the prefix {@code Abstract}. You are strongly encouraged to extend these base implementations rather
   * than directly implementing the interface to gain compatibility in case of an extension. The latter is
   * still allowed but you have to be aware of the consequences that you may have to fix compile errors after
   * upgrading and your code may NOT work across different releases. <br>
   * <b>NOTE:</b><br>
   * With Java8 default methods can be added to interfaces. This reduces the problem but may not eliminate it.
   * This constant is only for documentation purpose. Please never use it in your code.
   */
  String EXTENDABLE_INTERFACE = null;

}
