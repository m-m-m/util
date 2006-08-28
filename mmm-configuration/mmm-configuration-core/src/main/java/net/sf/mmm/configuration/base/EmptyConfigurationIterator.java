/* $Id: EmptyConfigurationIterator.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.configuration.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements an empty iterator of
 * {@link net.sf.mmm.configuration.api.ConfigurationIF configurations}. It is a
 * singleton that can be retrieved via {@link #getInstance()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyConfigurationIterator implements Iterator<AbstractConfiguration> {

    /** the singleton instance */
    private static final Iterator<AbstractConfiguration> INSTANCE = new EmptyConfigurationIterator();

    /**
     * The constructor.
     * 
     */
    private EmptyConfigurationIterator() {

        super();
    }

    /**
     * @see java.util.Iterator#hasNext()
     * {@inheritDoc}
     */
    public boolean hasNext() {

        return false;
    }

    /**
     * @see java.util.Iterator#next()
     * {@inheritDoc}
     */
    public AbstractConfiguration next() {

        throw new NoSuchElementException();
    }

    /**
     * @see java.util.Iterator#remove()
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
