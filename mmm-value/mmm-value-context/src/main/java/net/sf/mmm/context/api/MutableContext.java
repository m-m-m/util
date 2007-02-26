/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.context.api;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the interface for a context that can be modified by the user.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableContext extends Context {

    /**
     * This method sets the variable specified by <code>variableName</code> to
     * the given <code>value</code>. If the variable is already set, it will
     * be overriden.
     * 
     * @see #getObject(String)
     * 
     * @param variableName
     *        is the name of the variable to set.
     * @param value
     *        is the value to assign to the variable.
     */
    void setObject(String variableName, Object value);

    /**
     * This method sets the variable specified by <code>variableName</code> to
     * the given <code>value</code>. If the variable is already set, it will
     * be overriden.
     * 
     * @see #getValue(String)
     * 
     * @param variableName
     *        is the name of the variable to set.
     * @param value
     *        is the value to assign to the variable.
     */
    void setValue(String variableName, GenericValue value);

    /**
     * This method unsets the variable specified by <code>variableName</code>.
     * This will only remove a variable defined in this context. A variable
     * inherited from a {@link Context#createChildContext() parent context}
     * can not be removed here. <br>
     * If this method is called with the name of a variable not defined in this
     * context it will have no effect.
     * 
     * @param variableName
     *        is the name of the variable to unset.
     */
    void unsetValue(String variableName);

    /**
     * This method gets an immutable proxy of this context that can NOT be
     * casted to {@link MutableContext}.
     * 
     * @return the immutable view on this context.
     */
    Context getImmutableContext();

}