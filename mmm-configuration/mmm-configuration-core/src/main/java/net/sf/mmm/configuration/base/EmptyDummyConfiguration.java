/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListenerIF;
import net.sf.mmm.event.EventListenerIF;
import net.sf.mmm.value.api.MutableGenericValueIF;
import net.sf.mmm.value.base.EmptyValue;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.configuration.api.MutableConfigurationIF} interface that is
 * always empty.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyDummyConfiguration extends AbstractConfiguration {

  /** the empty configuration element */
  private static final EmptyDummyConfiguration EMPTY_ELEMENT = new EmptyDummyConfiguration(
      Type.ELEMENT);

  /** the empty configuration attribute */
  private static final EmptyDummyConfiguration EMPTY_ATTRIBUTE = new EmptyDummyConfiguration(
      Type.ATTRIBUTE);

  /** the type */
  private final Type type;

  /**
   * The constructor.
   * 
   * @param configurationType
   *        is the type of the empty configuration.
   */
  private EmptyDummyConfiguration(Type configurationType) {

    super();
    this.type = configurationType;
  }

  /**
   * This method gets the singleton instance of the empty configuration for the
   * given type.
   * 
   * @param configurationType
   *        is the type of the requested empty configuration.
   * @return the requested configuration.
   */
  public static final AbstractConfiguration getInstance(Type configurationType) {

    switch (configurationType) {
      case ELEMENT:
        return EMPTY_ELEMENT;
      case ATTRIBUTE:
        return EMPTY_ATTRIBUTE;
      default :
        throw new IllegalArgumentException("" + configurationType);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfigurationIF#getValue()
   *      
   */
  public MutableGenericValueIF getValue() {

    return EmptyValue.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#getName() 
   */
  public String getName() {

    return "";
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#getNamespaceUri()
   *      
   */
  public String getNamespaceUri() {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfigurationIF#isEditable()
   *      
   */
  public boolean isEditable() {

    return false;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#isAddDefaults()
   *      
   */
  public boolean isAddDefaults() {

    return false;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#getType() 
   */
  public Type getType() {

    return this.type;
  }

  /**
   * @see net.sf.mmm.configuration.api.ConfigurationIF#getDescendant(java.lang.String,
   *      java.lang.String) 
   */
  public AbstractConfiguration getDescendant(String path, String namespaceUri) {

    // TODO: check type (attributes can not have children).
    // TODO: check if last segment starts with @
    return this;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getDescendants(java.lang.String,
   *      java.lang.String) 
   */
  public Collection<AbstractConfiguration> getDescendants(String path, String namespaceUri) {

    return Collections.emptyList();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  AbstractConfiguration doCreateChild(String name, String namespace) throws ConfigurationException {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespace)
      throws ConfigurationException {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.ConfigurationIF.Type)
   *      
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    return EmptyConfigurationIterator.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   *      
   */
  @Override
  protected void doRemove() throws ConfigurationException {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   *      
   */
  @Override
  protected void removeChild(AbstractConfiguration child) {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   *      
   */
  @Override
  protected void addChild(AbstractConfiguration child) {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   *      
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfigurationIF#disable()
   *      
   */
  public void disable() throws ConfigurationException {

  }

  /**
   * @see net.sf.mmm.event.EventSourceIF#addListener(EventListenerIF)
   *      
   */
  public void addListener(ConfigurationChangeListenerIF listener) {

  }

  /**
   * @see net.sf.mmm.event.EventSourceIF#removeListener(EventListenerIF)
   *      
   */
  public void removeListener(ConfigurationChangeListenerIF listener) {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getParent()
   *      
   */
  @Override
  protected AbstractConfiguration getParent() {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getOwnerDocument()
   *      
   */
  @Override
  protected AbstractConfigurationDocument getOwnerDocument() {

    return null;
  }

}
