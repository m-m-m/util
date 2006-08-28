/* $ Id: WriteableEnvironmentIF.java $ */
package net.sf.mmm.context.api;

import net.sf.mmm.value.api.GenericValueIF;

/**
 * This is the interface for a context that can be modified by the user.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableContextIF extends ContextIF {

    /**
     * This method sets the variable specified by variableName to the given
     * value. If the variable is already set, it will be overriden.
     * 
     * @param variableName
     *        is the name of the variable to set.
     * @param value
     *        is the value to assign to the variable.
     */
    void setVariable(String variableName, Object value);

    /**
     * This method sets the variable specified by variableName to the given
     * value. If the variable is already set, it will be overriden.
     * 
     * @param variableName
     *        is the name of the variable to set.
     * @param value
     *        is the value to assign to the variable.
     */
    void setVariable(String variableName, GenericValueIF value);

    /**
     * This method unsets the variable specified by variableName. This will only
     * remove a variable defined in this context. A variable inherited from
     * a {@link ContextIF#createChildContext() "parent context"} can
     * not be removed here. <br>
     * If this method is called with the name of a variable not defined in this
     * context it will have no effect.
     * 
     * @param variableName
     *        is the name of the variable to unset.
     */
    void unsetVariable(String variableName);

    /**
     * This method gets an immutable proxy of this context that can NOT be
     * casted to {@link MutableContextIF}.
     * 
     * @return the immutable view on this context.
     */
    ContextIF getImmutableContext();

}