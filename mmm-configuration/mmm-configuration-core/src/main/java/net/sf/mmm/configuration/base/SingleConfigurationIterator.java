/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class iterates a single configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingleConfigurationIterator implements Iterator<AbstractConfiguration> {

  /** the next configuration */
  private AbstractConfiguration next;

  /**
   * The constructor.
   * 
   * @param configuration
   */
  public SingleConfigurationIterator(AbstractConfiguration configuration) {

    super();
    this.next = configuration;
  }

  /**
   * {@inheritDoc} 
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * {@inheritDoc} 
   */
  public AbstractConfiguration next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    AbstractConfiguration result = this.next;
    this.next = null;
    return result;
  }

  /**
   * {@inheritDoc} 
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
