/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This class implements an empty iterator of
 * {@link net.sf.mmm.configuration.api.Configuration configurations}. It is a
 * singleton that can be retrieved via {@link #getInstance()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyConfigurationIterator implements Iterator<AbstractConfiguration> {

  /** the singleton instance */
  private static final Iterator<AbstractConfiguration> INSTANCE = new EmptyConfigurationIterator();

  /**
   * The constructor.
   */
  private EmptyConfigurationIterator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration next() {

    throw new NoSuchElementException();
  }

  /**
   * {@inheritDoc}
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

  /**
   * This method gets the singleton instance of this class.
   * 
   * @return an empty iterator.
   */
  public static Iterator<AbstractConfiguration> getInstance() {

    return INSTANCE;
  }

}
