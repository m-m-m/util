/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.attribute;

/**
 * This interface gives read access to the {@link #isDeprecated() deprecated} attribute of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public interface AttributeReadDeprecated {

  /**
   * This method determines if this object is <em>deprecated</em>. Like in Java deprecated means that the object may not
   * be used anymore.
   *
   * @return {@code true} if active, {@code false} otherwise.
   */
  boolean isDeprecated();

}
