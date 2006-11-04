/* $Id$ */
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
     * @see net.sf.mmm.context.api.Context#getObject(java.lang.String)
     * 
     */
    public Object getObject(String variableName) {

        return getContext().getObject(variableName);
    }

    /**
     * @see net.sf.mmm.context.api.Context#getValue(java.lang.String)
     * 
     */
    public GenericValue getValue(String variableName) {

        return getContext().getValue(variableName);
    }

    /**
     * @see net.sf.mmm.context.api.Context#hasValue(java.lang.String)
     * 
     */
    public boolean hasValue(String variableName) {
    
        return getContext().hasValue(variableName);
    }
    
    /**
     * @see net.sf.mmm.context.api.Context#getVariableNames()
     * 
     */
    public Set<String> getVariableNames() {

        return getContext().getVariableNames();
    }

    /**
     * @see net.sf.mmm.context.api.Context#createChildContext()
     * 
     */
    public MutableContext createChildContext() {

        return getContext().createChildContext();
    }

}
