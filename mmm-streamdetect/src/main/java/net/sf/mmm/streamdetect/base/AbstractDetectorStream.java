/* $Id$ */
package net.sf.mmm.streamdetect.base;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.context.impl.MutableContext;
import net.sf.mmm.streamdetect.api.DetectorStreamIF;

/**
 * This is the abstract base implementation of a {@link DetectorStreamIF}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDetectorStream implements DetectorStreamIF {

    /** @see #getMetadata() */
    private MutableContextIF context;

    /** @see #isDone() */
    private boolean done;

    /**
     * The constructor.
     */
    public AbstractDetectorStream() {

        this(new MutableContext());
    }

    /**
     * The constructor.
     * 
     * @param metadata
     *        is the initial {@link #getMetadata() metadata}.
     */
    public AbstractDetectorStream(MutableContextIF metadata) {

        super();
        this.context = metadata;
        this.done = false;
    }

    /**
     * @see net.sf.mmm.streamdetect.api.DetectorStreamIF#getMetadata()
     *      {@inheritDoc}
     */
    public ContextIF getMetadata() {

        return this.context.getImmutableContext();
    }

    /**
     * This method sets the {@link #isDone() done} flag.
     */
    protected void setDone() {

        this.done = true;
    }

    /**
     * @see net.sf.mmm.streamdetect.api.DetectorStreamIF#isDone() {@inheritDoc}
     */
    public boolean isDone() {

        return this.done;
    }

}
