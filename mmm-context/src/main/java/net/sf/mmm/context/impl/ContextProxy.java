/* $Id$ */
package net.sf.mmm.context.impl;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.base.AbstractContextProxy;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.context.api.Context} interface that delegates to
 * another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContextProxy extends AbstractContextProxy {

    /** the {@link #getContext() "delegate instance"} this proxy points to. */
    private final Context delegate;

    /**
     * The constructor.
     * 
     * @param environment
     *        is the {@link #getContext() "delegate instance"} this proxy
     *        points to.
     */
    public ContextProxy(Context environment) {

        super();
        this.delegate = environment;
    }

    /**
     * @see net.sf.mmm.context.base.AbstractContextProxy#getContext()
     * 
     */
    @Override
    protected Context getContext() {

        return this.delegate;
    }

}
