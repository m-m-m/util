/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class iterates over all child nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ChildIterator implements Iterator<AbstractConfiguration> {

    /** the iterator of all child-lists */
    private final Iterator<List<AbstractConfiguration>> childListIterator;

    /** the current child-list iterator */
    private Iterator<AbstractConfiguration> currentIterator;

    /** the next item */
    private AbstractConfiguration next;

    /**
     * The constructor.
     * 
     * @param attributeIterator
     *        is the iterator of all the attributes to iterate.
     * @param childLists
     *        is the iterator of all the child-element lists.
     */
    public ChildIterator(Iterator<AbstractConfiguration> attributeIterator,
            Iterator<List<AbstractConfiguration>> childLists) {

        super();
        this.childListIterator = childLists;
        this.currentIterator = attributeIterator;
        this.next = null;
        stepNext();
    }

    /**
     * This method steps to the next element.
     */
    private void stepNext() {

        this.next = null;
        if ((this.currentIterator != null) && (this.currentIterator.hasNext())) {
            this.next = this.currentIterator.next();
        } else if (this.childListIterator != null) {
            while (this.childListIterator.hasNext()) {
                this.currentIterator = this.childListIterator.next().iterator();
                if (this.currentIterator.hasNext()) {
                    this.next = this.currentIterator.next();
                    return;
                }
            }
        }
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

        if (this.next != null) {
            AbstractConfiguration result = this.next;
            stepNext();
            return result;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * @see java.util.Iterator#remove()
     * {@inheritDoc}
     */
    public void remove() {

        throw new UnsupportedOperationException();
    }

}
