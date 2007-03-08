/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path.condition;

import java.util.Iterator;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.path.SimplePathSegment;

/**
 * This is an implementation of the {@link Condition} interface that simply
 * checks is a simple descendent-path exists.<br>
 * An example for such a condition is <code>[foo/bar/@attr]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PathCondition implements Condition {

  /** the segments */
  private final SimplePathSegment[] segments;

  /**
   * The constructor.
   * 
   * @param pathSegments
   *        are the segments specifying the path to the descendant matched by
   *        this condition.
   */
  public PathCondition(SimplePathSegment[] pathSegments) {

    super();
    this.segments = pathSegments;
  }

  /**
   * @see net.sf.mmm.configuration.base.path.condition.Condition#canBeEstablished(net.sf.mmm.configuration.base.AbstractConfiguration, java.lang.String)
   */
  public boolean canBeEstablished(AbstractConfiguration configuration, String namespaceUri) {
  
    return true;
  }
  
  /**
   * This method recursively implements the accept method.
   * 
   * @param configuration
   *        is the current configuration node.
   * @param segmentIndex
   *        is the current index in the segment-list.
   * @return <code>true</code> if there is at least one path that matches.
   */
  private boolean accept(AbstractConfiguration configuration, int segmentIndex) {

    SimplePathSegment segment = this.segments[segmentIndex];
    Iterator<AbstractConfiguration> childIterator;
    if (segment.isPattern()) {
      childIterator = configuration.getChildren(segment.getPattern());
    } else {
      childIterator = configuration.getChildren(segment.getString());
    }
    int nextIndex = segmentIndex + 1;
    while (childIterator.hasNext()) {
      AbstractConfiguration child = childIterator.next();
      boolean acceptChild;
      if (nextIndex < this.segments.length) {
        acceptChild = accept(child, nextIndex);
      } else {
        acceptChild = acceptDescendant(child);
      }
      if (acceptChild) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method checks if the given <code>descendant</code> is accepted by
   * this condition. Override this method to create a more specific condition.
   * 
   * @param descendant
   *        is a descendant that matches the condition-path.
   * @return <code>true</code> if the give <code>descendant</code> is
   *         acceptable for this condition.
   */
  protected boolean acceptDescendant(AbstractConfiguration descendant) {

    return true;
  }

  /**
   * @see net.sf.mmm.configuration.base.path.condition.Condition#accept(net.sf.mmm.configuration.base.AbstractConfiguration, String)
   */
  public boolean accept(AbstractConfiguration configuration, String namespaceUri) {

    return accept(configuration, 0);
  }
  
  /**
   * @see net.sf.mmm.configuration.base.path.condition.Condition#establish(net.sf.mmm.configuration.base.AbstractConfiguration, String)
   */
  public AbstractConfiguration establish(AbstractConfiguration configuration, String namespaceUri) {

    AbstractConfiguration node = configuration;
    for (SimplePathSegment segment : this.segments) {
      if (segment.isPattern()) {
        // TODO: NLS
        throw new ConfigurationException("Cannot establish condition with wildcards!");
      }
      node = node.requireChild(segment.getString(), namespaceUri);
    }
    doEstablish(node);
    return configuration;
  }

  /**
   * This method establishes this condition on the configuration at the path of
   * this condition which is given by <code>node</code>.
   * 
   * @param node
   *        is the configuration this {@link #getSegments() path} points to.
   */
  protected void doEstablish(AbstractConfiguration node) {

  // nothing to do...
  }

  /**
   * This method gets the simple path segments.
   * 
   * @return the segments.
   */
  public SimplePathSegment[] getSegments() {

    return this.segments;
  }

  /**
   * Override to append chars before condition-end char of {@link #toString()}.
   * 
   * @param buffer
   *        is the buffer to use.
   */
  protected void appendCondition(StringBuffer buffer) {

  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    StringBuffer buffer = new StringBuffer();
    buffer.append(Configuration.PATH_CONDITION_START);
    boolean separator = false;
    for (SimplePathSegment segment : this.segments) {
      if (separator) {
        buffer.append(Configuration.PATH_SEPARATOR);
      }
      buffer.append(segment);
      separator = true;
    }
    appendCondition(buffer);
    buffer.append(Configuration.PATH_CONDITION_END);
    return buffer.toString();
  }

}
