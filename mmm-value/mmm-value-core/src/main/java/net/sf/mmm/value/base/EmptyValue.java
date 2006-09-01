/* $Id$ */
package net.sf.mmm.value.base;

import net.sf.mmm.value.api.MutableGenericValueIF;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.value.api.MutableGenericValueIF} interface that is always
 * {@link #isEmpty() empty}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyValue extends AbstractObjectValue {

    /** UID for serialization */
    private static final long serialVersionUID = 4749888507801081650L;

    /** the singleton instance */
    private static final EmptyValue INSTANCE = new EmptyValue("empty");

    /** @see #toString() */
    private final String name;

    /**
     * The constructor.
     * 
     * @param valueName
     *        is the name of the value returned by {@link #toString()}.
     */
    public EmptyValue(String valueName) {

        super();
        assert (valueName != null);
        this.name = valueName;
    }

    /**
     * This method gets the singleton instance of the empty, immutable value.
     * 
     * @return the instance.
     */
    public static MutableGenericValueIF getInstance() {

        return INSTANCE;
    }

    /**
     * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#getPlainValue()
     * {@inheritDoc}
     */
    @Override
    protected Object getPlainValue() {

        return null;
    }

    /**
     * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#setPlainValue(java.lang.Object)
     * {@inheritDoc}
     */
    @Override
    protected void setPlainValue(Object newValue) {

        throw new IllegalStateException();
    }

    /**
     * @see net.sf.mmm.value.api.MutableGenericValueIF#isEditable()
     *      {@inheritDoc}
     */
    public boolean isEditable() {

        return false;
    }

    /**
     * @see net.sf.mmm.value.api.GenericValueIF#isAddDefaults() {@inheritDoc}
     */
    @Override
    public boolean isAddDefaults() {

        return false;
    }

    /**
     * @see net.sf.mmm.value.base.AbstractObjectValue#toString() {@inheritDoc}
     */
    @Override
    public String toString() {

        return this.name;
    }

}
