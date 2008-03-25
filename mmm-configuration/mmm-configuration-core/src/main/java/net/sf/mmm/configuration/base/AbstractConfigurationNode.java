/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.term.api.Term;
import net.sf.mmm.util.event.ChangeEventType;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.value.api.MutableGenericValue;
import net.sf.mmm.value.base.AbstractStringValue;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfiguration} interface for
 * "real" configurations (instead of proxies).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationNode extends AbstractConfiguration {

  /** the parent configuration */
  private AbstractConfiguration parent;

  /** @see #getValue() */
  private MutableGenericValue value;

  /** @see #isEditable() */
  private Boolean editable;

  /** @see #isAddDefaults() */
  private Boolean addDefaults;

  /**
   * The constructor.
   * 
   * @param parentConfiguration is the {@link #getParent() parent}
   *        configuration.
   */
  public AbstractConfigurationNode(AbstractConfiguration parentConfiguration) {

    super();
    this.parent = parentConfiguration;
    this.value = null;
    this.editable = null;
    this.addDefaults = null;
  }

  /**
   * This method gets the parent of this configuration.
   * 
   * @return the parent configuration or <code>null</code> if this is the root
   *         configuration.
   */
  @Override
  public AbstractConfiguration getParent() {

    return this.parent;
  }

  /**
   * This method sets (changes) the {@link #getParent() parent} of this
   * configuration.<br>
   * ATTENTION: Use this method only if you exactly know what you are doing.
   * 
   * @param newParent is the new parent for this configuration.
   */
  protected void setParent(AbstractConfiguration newParent) {

    this.parent = newParent;
  }

  /**
   * This method sets (overrides) the {@link #isAddDefaults()} flag.
   * 
   * @param isEditable is the value to set.
   */
  public void setEditable(boolean isEditable) {

    this.editable = Boolean.valueOf(isEditable);
  }

  /**
   * {@inheritDoc}
   */
  public final boolean isEditable() {

    // TODO: this is called quite often, this recursion stuff may be a big waste
    if (this.editable == null) {
      // inherit the flag if not explicitly set.
      if (this.parent == null) {
        AbstractConfigurationDocument ownerDoc = getOwnerDocument();
        if (ownerDoc == null) {
          return true;
        } else {
          return !ownerDoc.isImmutable();
        }
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
   * @param doAddDefaults is the value to set.
   */
  public void setAddDefaults(boolean doAddDefaults) {

    this.addDefaults = Boolean.valueOf(doAddDefaults);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAddDefaults() {

    // TODO: this is called quite often, this recursion stuff may be a big waste
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
  protected MutableGenericValue createValue() {

    return new Value();
  }

  /**
   * This method gets the {@link #getValue() value} as plain string.
   * 
   * @return the plain string value or <code>null</code> if value is not set.
   */
  protected abstract String getPlainString();

  /**
   * This method sets the plain string {@link #getValue() value}.
   * 
   * @param newValue is the new value to set.
   */
  protected abstract void setPlainString(String newValue);

  /**
   * {@inheritDoc}
   */
  public MutableGenericValue getValue() {

    if (this.value == null) {
      this.value = createValue();
    }
    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  public void addListener(ConfigurationChangeListener listener) {

    AbstractConfigurationDocument ownerDoc = getOwnerDocument();
    if (ownerDoc != null) {
      ownerDoc.addListener(this, listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeListener(ConfigurationChangeListener listener) {

    AbstractConfigurationDocument ownerDoc = getOwnerDocument();
    if (ownerDoc != null) {
      return ownerDoc.removeListener(this, listener);
    }
    return false;
  }

  /**
   * This method has to be called every time the configuration (value) is
   * updated.
   */
  protected void fireUpdate() {

    if (getOwnerDocument() != null) {
      getOwnerDocument().configurationChanged(this, ChangeEventType.UPDATE);
    }
  }

  /**
   * This inner class represents
   */
  private class Value extends AbstractStringValue {

    /** UID for serialization. */
    private static final long serialVersionUID = 6303301763148895128L;

    /** the path suffix */
    private static final String PATH_SUFFIX = "/value()";

    /**
     * the expression term used if value contains variable(s), else
     * <code>null</code>
     */
    private Term expression;

    /**
     * The constructor.
     */
    public Value() {

      super();
      initExpression(AbstractConfigurationNode.this.getPlainString());
    }

    /**
     * This method initializes the expression according to the given value.
     * 
     * @param plainValue is the plain value.
     */
    protected void initExpression(String plainValue) {

      // TODO: detection for expression only works for bootstrap term parser
      if ((plainValue != null) && (plainValue.contains(Term.VARIABLE_START))) {
        try {
          this.expression = getTermParser().parse(plainValue);
        } catch (Exception e) {
          throw new GeneralConfigurationException(e, AbstractConfigurationNode.this);
        }
      } else {
        this.expression = null;
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPlainValue() {

      if (this.expression == null) {
        return AbstractConfigurationNode.this.getPlainString();
      } else {
        try {
          Object result = this.expression.evaluate(getOwnerDocument().getContext());
          if (result == null) {
            return null;
          } else {
            return result.toString();
          }
        } catch (ValueException e) {
          throw new GeneralConfigurationException(e, AbstractConfigurationNode.this);
        }
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setPlainValue(String newValue) {

      AbstractConfigurationNode.this.setPlainString(newValue);
      initExpression(newValue);
      fireUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAddDefaults() {

      return AbstractConfigurationNode.this.isAddDefaults();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEditable() {

      return AbstractConfigurationNode.this.isEditable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      String path = AbstractConfigurationNode.this.toString();
      int len = path.length() + PATH_SUFFIX.length();
      StringBuffer sb = new StringBuffer(len);
      sb.append(path);
      sb.append(PATH_SUFFIX);
      return sb.toString();
    }

  }

}
