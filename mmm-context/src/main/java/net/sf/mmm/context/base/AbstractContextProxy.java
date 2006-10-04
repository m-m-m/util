/* $Id$ */
package net.sf.mmm.context.base;

import java.util.Set;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.value.api.GenericValueIF;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.context.api.ContextIF} interface that delegates to
 * another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContextProxy implements ContextIF {

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
    protected abstract ContextIF getContext();
    
    /**
     * @see net.sf.mmm.context.api.ContextIF#getObject(java.lang.String)
     * 
     */
    public Object getObject(String variableName) {

        return getContext().getObject(variableName);
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#getValue(java.lang.String)
     * 
     */
    public GenericValueIF getValue(String variableName) {

        return getContext().getValue(variableName);
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#hasValue(java.lang.String)
     * 
     */
    public boolean hasValue(String variableName) {
    
        return getContext().hasValue(variableName);
    }
    
    /**
     * @see net.sf.mmm.context.api.ContextIF#getVariableNames()
     * 
     */
    public Set<String> getVariableNames() {

        return getContext().getVariableNames();
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#createChildContext()
     * 
     */
    public MutableContextIF createChildContext() {

        return getContext().createChildContext();
    }

}
