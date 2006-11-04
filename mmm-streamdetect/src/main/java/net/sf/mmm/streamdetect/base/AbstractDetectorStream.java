/* $Id$ */
package net.sf.mmm.streamdetect.base;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;
import net.sf.mmm.context.impl.MutableContextImpl;
import net.sf.mmm.streamdetect.api.DetectorStream;

/**
 * This is the abstract base implementation of a {@link DetectorStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDetectorStream implements DetectorStream {

    /** @see #getMetadata() */
    private MutableContext context;

    /** @see #isDone() */
    private boolean done;

    /**
     * The constructor.
     */
    public AbstractDetectorStream() {

        this(new MutableContextImpl());
    }

    /**
     * The constructor.
     * 
     * @param metadata
     *        is the initial {@link #getMetadata() metadata}.
     */
    public AbstractDetectorStream(MutableContext metadata) {

        super();
        this.context = metadata;
        this.done = false;
    }

    /**
     * @see net.sf.mmm.streamdetect.api.DetectorStream#getMetadata()
     *      
     */
    public Context getMetadata() {

        return this.context.getImmutableContext();
    }

    /**
     * This method sets the {@link #isDone() done} flag.
     */
    protected void setDone() {

        this.done = true;
    }

    /**
     * @see net.sf.mmm.streamdetect.api.DetectorStream#isDone() 
     */
    public boolean isDone() {

        return this.done;
    }

}
