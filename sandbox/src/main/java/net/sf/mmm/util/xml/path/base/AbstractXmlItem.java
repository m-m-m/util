/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.base;

/**
 * This is the abstract base implementation of all items of an
 * {@link net.sf.mmm.util.xml.path.api XML expression}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractXmlItem {

  /**
   * This method is like {@link #toString()} using the given
   * {@code stringBuilder}.
   * 
   * @param stringBuilder is where to append the string representation to.
   */
  public abstract void toString(StringBuilder stringBuilder);

  @Override
  public String toString() {

    StringBuilder stringBuilder = new StringBuilder();
    toString(stringBuilder);
    return stringBuilder.toString();
  }

}
