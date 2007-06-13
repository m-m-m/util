/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.iterator;

import java.util.Iterator;

import net.sf.mmm.configuration.api.Configuration.Type;
import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This class iterates over all child nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ChildTypeIterator extends SiblingIterator {

  /** the current child-list iterator */
  private final Iterator<AbstractConfiguration> iterator;

  /** the type of the children to iterate */
  private final Type type;

  /**
   * The constructor.
   * 
   * @param childIterator is the iterator of all children to iterate.
   * @param childType is the type of the children to iterate or
   *        <code>null</code> to iterate all children.
   */
  public ChildTypeIterator(Iterator<AbstractConfiguration> childIterator, Type childType) {

    super();
    this.iterator = childIterator;
    this.type = childType;
    stepNext();
  }

  /**
   * {@inheritDoc}
   */
  protected boolean stepNext() {

    boolean hasNext = super.stepNext();
    // has next sibling to iterate?
    if (!hasNext) {
      while (this.iterator.hasNext()) {
        AbstractConfiguration configuration = this.iterator.next();
        if ((this.type == null) || (configuration.getType() == this.type)) {
          setNext(configuration);
          return true;
        }
      }
    }
    return hasNext;
  }

}
