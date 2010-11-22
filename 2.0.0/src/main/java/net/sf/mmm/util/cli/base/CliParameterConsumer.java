/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * A {@link CliParameterConsumer} holds the command-line parameters together
 * with the current index. In other words it is like an
 * {@link java.util.Iterator} for the parameters.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliParameterConsumer {

  /** An empty instance. */
  static final CliParameterConsumer EMPTY_INSTANCE = new CliParameterConsumer();

  /** @see #getNext() */
  private final String[] parameters;

  /** @see #getNext() */
  private int index;

  /**
   * The constructor.
   * 
   * @param parameters are the parameters to consume.
   */
  public CliParameterConsumer(String... parameters) {

    super();
    this.parameters = parameters;
    this.index = 0;
  }

  /**
   * @see java.util.Iterator#hasNext()
   * @see java.util.Enumeration#hasMoreElements()
   * 
   * @return <code>true</code> if {@link #getNext() element is available},
   *         <code>false</code> otherwise.
   */
  public boolean hasNext() {

    return (this.index < this.parameters.length);
  }

  /**
   * @see java.util.Iterator#next()
   * @see java.util.Enumeration#nextElement()
   * 
   * @return the next element.
   */
  public String getNext() {

    String current = getCurrent();
    this.index++;
    return current;
  }

  /**
   * This method gets the current element. It is the same as {@link #getNext()}
   * except that it does NOT step on to the next element.
   * 
   * @return the current element or <code>null</code> if no {@link #hasNext()
   *         next} is available.
   */
  public String getCurrent() {

    if (this.index >= this.parameters.length) {
      return null;
    }
    String arg = this.parameters[this.index];
    if (arg == null) {
      throw new NlsNullPointerException("arguments[" + this.index + "]");
    }
    return arg;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.parameters.length; i++) {
      if (i > 0) {
        sb.append(' ');
      }
      if (i == this.index) {
        sb.append("(^)");
      }
      sb.append(this.parameters[i]);
    }
    return super.toString();
  }
}
