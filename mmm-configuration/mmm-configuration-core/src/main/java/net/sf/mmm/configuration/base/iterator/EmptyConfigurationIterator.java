/* $Id$ */
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
   * @see java.util.Iterator#hasNext() 
   */
  public boolean hasNext() {

    return false;
  }

  /**
   * @see java.util.Iterator#next() 
   */
  public AbstractConfiguration next() {

    throw new NoSuchElementException();
  }

  /**
   * @see java.util.Iterator#remove() 
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
