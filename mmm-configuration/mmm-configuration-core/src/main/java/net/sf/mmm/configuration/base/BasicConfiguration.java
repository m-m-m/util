/* $Id: BasicConfiguration.java 208 2006-08-22 20:29:11Z hohwille $ */
package net.sf.mmm.configuration.base;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListenerIF;
import net.sf.mmm.term.api.TermIF;
import net.sf.mmm.value.api.MutableGenericValueIF;
import net.sf.mmm.value.api.ValueException;
import net.sf.mmm.value.base.AbstractStringValue;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfigurationIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class BasicConfiguration extends AbstractConfiguration {

    /** the parent configuration */
    private AbstractConfiguration parent;

    /** @see #getValue() */
    private MutableGenericValueIF value;

    /** @see #isEditable() */
    private Boolean editable;

    /** @see #isAddDefaults() */
    private Boolean addDefaults;

    /**
     * The constructor.
     * 
     * @param parentConfiguration
     *        is the parent configuration.
     */
    public BasicConfiguration(AbstractConfiguration parentConfiguration) {

        super();
        this.parent = parentConfiguration;
        this.value = null;
        this.editable = null;
        this.addDefaults = null;
    }

    /**
     * This method gets the parent of this configuration.
     * 
     * @return the parent configuration or <code>null</code> if this is the
     *         root configuration.
     */
    protected AbstractConfiguration getParent() {

        return this.parent;
    }

    /**
     * This method sets (changes) the {@link #getParent() parent} of this
     * configuration.<br>
     * ATTENTION: Use this method only if you exactly know what you are doing.
     * 
     * @param newParent
     *        is the new parent for this configuration.
     */
    protected void setParent(AbstractConfiguration newParent) {

        this.parent = newParent;
    }

    /**
     * This method sets (overrides) the {@link #isAddDefaults()} flag.
     * 
     * @param isEditable
     *        is the value to set.
     */
    public void setEditable(boolean isEditable) {

        this.editable = Boolean.valueOf(isEditable);
    }

    /**
     * @see net.sf.mmm.configuration.api.MutableConfigurationIF#isEditable()
     *      {@inheritDoc}
     */
    public final boolean isEditable() {

        if (this.editable == null) {
            // inherit the flag if not explicitly set.
            if (this.parent == null) {
                return true;
            } else {
                return this.parent.isEditable();
            }
        } else {
            return this.editable.booleanValue();
        }
    }

    /**
     * This method sets (overrides) the {@link #isAddDefaults()} flag.
     * 
     * @param doAddDefaults
     *        is the value to set.
     */
    public void setAddDefaults(boolean doAddDefaults) {

        this.addDefaults = Boolean.valueOf(doAddDefaults);
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#isAddDefaults()
     *      {@inheritDoc}
     */
    public boolean isAddDefaults() {

        if (this.addDefaults == null) {
            // inherit the flag if not explicitly set.
            if (this.parent == null) {
                return true;
            } else {
                return this.parent.isAddDefaults();
            }
        } else {
            return this.addDefaults.booleanValue();
        }
    }

    /**
     * This method creates the {@link #getValue() value}.
     * 
     * @return the value.
     */
    protected MutableGenericValueIF createValue() {

        return new Value();
    }

    /**
     * This method gets the {@link #getValue() value} as plain string.
     * 
     * @return the plain string value or <code>null</code> if value is not
     *         set.
     */
    protected abstract String getPlainString();

    /**
     * This method sets the plain string {@link #getValue() value}.
     * 
     * @param newValue
     *        is the new value to set.
     */
    protected abstract void setPlainString(String newValue);

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getValue()
     *      {@inheritDoc}
     */
    public MutableGenericValueIF getValue() {

        if (this.value == null) {
            this.value = createValue();
        }
        return this.value;
    }

    /**
     * This method is to has to be called every time the configuration changes.
     * It will set the "dirty" flag so
     * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#save() saving}
     * is only performed as needed.
     * 
     */
    protected void setModified() {

        if (getOwnerDocument() != null) {
            getOwnerDocument().setModified(this);
        }
    }

    /**
     * @see net.sf.mmm.event.EventSourceIF#addListener(net.sf.mmm.event.EventListenerIF)
     *      {@inheritDoc}
     */
    public void addListener(ConfigurationChangeListenerIF listener) {

        getOwnerDocument().addListener(this, listener);
    }

    /**
     * @see net.sf.mmm.event.EventSourceIF#removeListener(net.sf.mmm.event.EventListenerIF)
     *      {@inheritDoc}
     */
    public void removeListener(ConfigurationChangeListenerIF listener) {

        getOwnerDocument().removeListener(this, listener);
    }

    /**
     * This inner class represents
     */
    private class Value extends AbstractStringValue {

        /** UID for serialization */
        private static final long serialVersionUID = 6303301763148895128L;

        /** thw path suffix */
        private static final String PATH_SUFFIX = "/value()";

        /**
         * the expression term used if value contains variable(s), else
         * <code>null</code>
         */
        private TermIF expression;

        /**
         * The constructor.
         * 
         */
        public Value() {

            super();
            initExpression(BasicConfiguration.this.getPlainString());
        }

        /**
         * This method initializes the expression according to the given value.
         * 
         * @param plainValue
         *        is the plain value.
         */
        protected void initExpression(String plainValue) {

            if ((plainValue != null)
                    && (plainValue.contains(ConfigurationExpressionParser.VARIABLE_START))) {
                try {
                    this.expression = ConfigurationExpressionParser.parse(plainValue);
                } catch (Exception e) {
                    // TODO: i18n
                    throw new ConfigurationException(
                            e,
                            "Error in configuration value node \"{0}\": expression \"{0}\" could not be parsed!",
                            this, plainValue);
                }
            } else {
                this.expression = null;
            }
        }

        /**
         * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#getPlainValue()
         *      {@inheritDoc}
         */
        @Override
        protected String getPlainValue() {

            if (this.expression == null) {
                return BasicConfiguration.this.getPlainString();
            } else {
                Throwable nested = null;
                try {
                    Object result = this.expression.evaluate(getOwnerDocument().getContext());
                    if (result != null) {
                        return (String) result;
                    }
                } catch (ValueException e) {
                    nested = e;
                }
                // TODO i18n
                throw new ConfigurationException(
                        nested,
                        "Error in configuration value node \"{0}\": expression \"{1}\" could not be evaluated!",
                        this, this.expression);
            }
        }

        /**
         * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#setPlainValue(java.lang.Object)
         *      {@inheritDoc}
         */
        @Override
        protected void setPlainValue(String newValue) {

            BasicConfiguration.this.setPlainString(newValue);
            initExpression(newValue);
        }

        /**
         * @see net.sf.mmm.value.api.GenericValueIF#isAddDefaults()
         *      {@inheritDoc}
         */
        @Override
        public boolean isAddDefaults() {

            return BasicConfiguration.this.isAddDefaults();
        }

        /**
         * @see net.sf.mmm.value.api.MutableGenericValueIF#isEditable()
         *      {@inheritDoc}
         */
        public boolean isEditable() {

            return BasicConfiguration.this.isEditable();
        }

        /**
         * @see net.sf.mmm.value.base.AbstractStringValue#toString()
         *      {@inheritDoc}
         */
        @Override
        public String toString() {

            String path = BasicConfiguration.this.toString();
            int len = path.length() + PATH_SUFFIX.length();
            StringBuffer sb = new StringBuffer(len);
            sb.append(path);
            sb.append(PATH_SUFFIX);
            return sb.toString();
        }

    }

}
