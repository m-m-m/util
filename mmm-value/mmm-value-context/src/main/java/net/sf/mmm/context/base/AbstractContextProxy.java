/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.context.base;

import java.util.Set;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;
import net.sf.mmm.value.api.GenericValue;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.context.api.Context} interface that delegates to
 * another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContextProxy implements Context {

    /**
     * The constructor.
     */
    public AbstractContextProxy() {

        super();
    }

    /**
     * This method gets the delegate instance this proxy points to.
     * 
     * @return the real environment behind this proxy.
     */
    protected abstract Context getContext();
    
    /**
     * {@inheritDoc}
     */
    public Object getObject(String variableName) {

        return getContext().getObject(variableName);
    }

    /**
     * {@inheritDoc}
     */
    public GenericValue getValue(String variableName) {

        return getContext().getValue(variableName);
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasValue(String variableName) {
    
        return getContext().hasValue(variableName);
    }
    
    /**
     * {@inheritDoc}
     */
    public Set<String> getVariableNames() {

        return getContext().getVariableNames();
    }

    /**
     * {@inheritDoc}
     */
    public MutableContext createChildContext() {

        return getContext().createChildContext();
    }

}
