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
 * @param <T> is the generic type of the input object to {@link #accept(Object) accept}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Consumer<T> {

  /**
   * Accept an input value.
   * 
   * @param value the input object to consume.
   */
  void accept(T value);

}
