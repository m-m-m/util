/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.Iterator;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.event.ConfigurationChangeListener;
import net.sf.mmm.value.api.MutableGenericValue;
import net.sf.mmm.value.base.EmptyValue;

/**
 * This is an implementation of the
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyConfiguration extends AbstractConfiguration {

  /** the parent configuration */
  private AbstractConfiguration parent;

  /**
   * The constructor.
   * 
   * @param parentConfiguration
   */
  public EmptyConfiguration(AbstractConfiguration parentConfiguration) {

    super();
    this.parent = parentConfiguration;
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#getValue()
   */
  public MutableGenericValue getValue() {

    return EmptyValue.getInstance();
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected void addChild(AbstractConfiguration child) {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doCreateChild(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  AbstractConfiguration doCreateChild(String name, String namespaceUri)
      throws ConfigurationException {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getParent()
   */
  @Override
  protected AbstractConfiguration getParent() {

    return this.parent;
  }

  /**
   * @see net.sf.mmm.configuration.api.MutableConfiguration#isEditable()
   */
  public boolean isEditable() {

    return false;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#isAddDefaults()
   */
  public boolean isAddDefaults() {

    return false;
  }

  /**
   * @see net.sf.mmm.util.event.EventSourceIF#addListener(net.sf.mmm.util.event.EventListener)
   */
  public void addListener(ConfigurationChangeListener listener) {

  }

  /**
   * @see net.sf.mmm.util.event.EventSourceIF#removeListener(net.sf.mmm.util.event.EventListener)
   */
  public void removeListener(ConfigurationChangeListener listener) {

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doDisable()
   */
  @Override
  protected AbstractConfiguration doDisable() throws ConfigurationException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#doRemove()
   */
  @Override
  protected void doRemove() throws ConfigurationException {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfiguration getChild(String name, String namespaceUri)
      throws ConfigurationException {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.Configuration.Type)
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(Type childType) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
   *      java.lang.String) 
   */
  @Override
  public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#getOwnerDocument()
   */
  @Override
  protected AbstractConfigurationDocument getOwnerDocument() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  @Override
  protected void removeChild(AbstractConfiguration child) {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getName() 
   */
  public String getName() {

    return null;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getNamespaceUri()
   */
  public String getNamespaceUri() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getType() 
   */
  public Type getType() {

    // TODO Auto-generated method stub
    return null;
  }

}
