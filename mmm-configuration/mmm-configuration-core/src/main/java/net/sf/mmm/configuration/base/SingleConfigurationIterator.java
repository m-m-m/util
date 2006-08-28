/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * TODO This type ...
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
     * @see java.util.Iterator#hasNext()
     * {@inheritDoc}
     */
    public boolean hasNext() {

        return (this.next != null);
    }

    /**
     * @see java.util.Iterator#next()
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
     * @see java.util.Iterator#remove()
     * {@inheritDoc}
     */
    public void remove() {

        throw new UnsupportedOperationException();
    }

}
