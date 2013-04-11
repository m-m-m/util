/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.function;

/**
 * This is the back-port for the according interface of Java 1.8+.<br/>
 * <b>ATTENTION:</b><br/>
 * The Java 1.8+ version of this interface also defines default methods that can NOT be supported in prior
 * java versions. Therefore, <code>mmm-util-core</code> offers helper methods that give similar features.
 * However, we strongly recommend that you update your project to Java 1.8+ to make use of closures (lambda
 * expressions) and other benefits.
 * 
 * @param <T> is the generic type of the first input object to {@link #accept(Object, Object) accept}.
 * @param <U> is the generic type of the second input object to {@link #accept(Object, Object) accept}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface BiConsumer<T, U> {

  /**
   * Performs operations upon the provided objects which may modify those objects and/or external state.
   * 
   * @param t is the first input object
   * @param u is the second input object
   */
  void accept(T t, U u);

}
