/* $Id$ */
package net.sf.mmm.context.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.value.api.GenericValueIF;
import net.sf.mmm.value.api.ValueNotSetException;
import net.sf.mmm.value.base.EmptyValue;
import net.sf.mmm.value.impl.ObjectValue;
import net.sf.mmm.value.impl.StringValue;

/**
 * This class is the basic implementation of the {@link ContextIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MutableContext implements MutableContextIF {

    /** maps variable names to {@link #getValue(String) values} */
    private final Map<String, GenericValueIF> variableTable;

    /** the parent context */
    private final ContextIF parent;

    /** the {@link #getImmutableContext() "immutable context"} */
    private final ContextIF immutableContext;

    /**
     * The constructor for a root-context.
     */
    public MutableContext() {

        this(null);
    }

    /**
     * The constructor for a {@link #createChildContext() sub-context}.
     * 
     * @param parentContext
     *        is the context the created one will derive from.
     */
    public MutableContext(ContextIF parentContext) {

        super();
        this.parent = parentContext;
        this.variableTable = new HashMap<String, GenericValueIF>();
        this.immutableContext = new ContextProxy(this);
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#getValue(java.lang.String)
     *      {@inheritDoc}
     * 
     * @param variableName
     *        is the name of the requested context value.
     * @return the requested value or <code>null</code> if no such value
     *         exists.
     */
    private GenericValueIF get(String variableName) {

        GenericValueIF result = this.variableTable.get(variableName);
        if ((result == null) && (this.parent != null)) {
            result = this.parent.getValue(variableName);
        }
        return result;
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#getValue(java.lang.String)
     *      {@inheritDoc}
     */
    public GenericValueIF getValue(String variableName) {

        GenericValueIF result = get(variableName);
        if (result == null) {
            result = new EmptyValue(variableName);
        }
        return result;
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#getObject(java.lang.String)
     *      {@inheritDoc}
     */
    public Object getObject(String variableName) {

        GenericValueIF value = get(variableName);
        if (value == null) {
            throw new ValueNotSetException(variableName);
        }
        return value.getObject(null);
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#hasValue(java.lang.String)
     *      {@inheritDoc}
     */
    public boolean hasValue(String variableName) {

        if (this.variableTable.containsKey(variableName)) {
            return true;
        } else {
            if (this.parent == null) {
                return false;
            } else {
                return this.parent.hasValue(variableName);
            }
        }
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#getVariableNames() {@inheritDoc}
     */
    public Set<String> getVariableNames() {

        Set<String> result;
        if (this.parent == null) {
            result = new HashSet<String>();
        } else {
            result = this.parent.getVariableNames();
        }
        result.addAll(this.variableTable.keySet());
        return result;
    }

    /**
     * @see net.sf.mmm.context.api.MutableContextIF#setVariable(java.lang.String,
     *      net.sf.mmm.value.api.GenericValueIF) {@inheritDoc}
     */
    public void setVariable(String variableName, GenericValueIF value) {

        this.variableTable.put(variableName, value);
    }

    /**
     * @see net.sf.mmm.context.api.MutableContextIF#setVariable(java.lang.String,
     *      java.lang.Object) {@inheritDoc}
     */
    public void setVariable(String variableName, Object value) {

        if ((value != null) && (value instanceof String)) {
            setVariable(variableName, new StringValue((String) value));
        } else {
            setVariable(variableName, new ObjectValue(value));
        }
    }

    /**
     * @see net.sf.mmm.context.api.ContextIF#createChildContext() {@inheritDoc}
     */
    public MutableContextIF createChildContext() {

        return new MutableContext(this);
    }

    /**
     * @see net.sf.mmm.context.api.MutableContextIF#unsetVariable(java.lang.String)
     *      {@inheritDoc}
     */
    public void unsetVariable(String variableName) {

        this.variableTable.remove(variableName);
    }

    /**
     * @see net.sf.mmm.context.api.MutableContextIF#getImmutableContext()
     *      {@inheritDoc}
     */
    public ContextIF getImmutableContext() {

        return this.immutableContext;
    }

}