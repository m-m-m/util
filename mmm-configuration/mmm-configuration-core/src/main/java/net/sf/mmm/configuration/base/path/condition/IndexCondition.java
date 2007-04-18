/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path.condition;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This is the implementation of the {@link Condition} interface that matches if
 * the configuration has a sibling with a given {@link #getIndex() index}.<br>
 * An example for such condition is <code>[2]</code> (for the second
 * occurrence of the given node).<br>
 * <b>ATTENTION:</b><br>
 * This {@link #getIndex() index} starts with <code>0</code> while in the
 * syntax of the descendant-path (compliant with XPath) it is 1-based. So
 * <code>[0]</code> is illegal.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IndexCondition implements Condition {

  /** the required index */
  private final int index;

  /**
   * The constructor. 
   * 
   * @param siblingIndex
   *        is the {@link #getIndex() index} to check for. ATTENTION:
   *        translation from 1-based to 0-based index required!
   */
  public IndexCondition(int siblingIndex) {

    super();
    this.index = siblingIndex;
  }

  /**
   * {@inheritDoc}
   */
  public boolean accept(AbstractConfiguration configuration, String namespaceUri) {

    return (this.index == configuration.getSiblingIndex());
  }

  /**
   * {@inheritDoc}
   */
  public boolean canBeEstablished(AbstractConfiguration configuration, String namespaceUri) {
  
    return true;
  }
  
  /**
   * {@inheritDoc}
   */
  public AbstractConfiguration establish(AbstractConfiguration configuration, String namespaceUri) {

    int count = configuration.getSiblingCount();
    if (this.index < count) {
      return configuration.getSibling(this.index);
    } else {
      AbstractConfiguration parent = configuration.getParent();
      if (parent == null) {
        if (this.index == 0) {
          return configuration;
        } else {
          // TODO: NLS
          throw new ConfigurationException("Cannot establish index condition on root node!");
        }
      } else {
        String name = configuration.getName();
        String nsUri = namespaceUri;
        if (nsUri == null) {
          nsUri = configuration.getNamespaceUri();          
        }
        int diff = count - this.index + 1;
        if (diff > 20) {
          // TODO: should we return empty configuration instead???
          // TODO: NLS
          throw new ConfigurationException("Too many siblings to create for index condition!");
        }
        AbstractConfiguration sibling = configuration;
        while (diff > 0) {
          sibling = parent.createChild(name, nsUri);
          diff--;
        }
        return sibling;
      }
    }
  }

  /**
   * This method gets the sibling-index of this condition.<br>
   * <b>ATTENTION</b><br>
   * This index is 0-based while in the syntax of the descendant-path (compliant
   * with XPath) it is 1-based.
   * 
   * @see AbstractConfiguration#getSiblingIndex()
   * 
   * @return the index
   */
  public int getIndex() {

    return this.index;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer buffer = new StringBuffer(4);
    buffer.append(Configuration.PATH_CONDITION_START);
    buffer.append(this.index + 1);
    buffer.append(Configuration.PATH_CONDITION_END);
    return buffer.toString();
  }

}
