/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.iterator;

import java.util.Iterator;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.Configuration.Type;
import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This class iterates over all child nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ChildPatternIterator extends SiblingIterator {

  /** the current child-list iterator */
  private final Iterator<AbstractConfiguration> iterator;

  /** the name pattern */
  private final Pattern pattern;

  /** the namespace-URI */
  private final String namespace;

  /** the type */
  private final Type type;

  /**
   * The constructor.
   * 
   * @param childIterator
   *        is the iterator of all children to iterate.
   * @param childNamePattern
   *        is the pattern to be matched by the
   *        {@link net.sf.mmm.configuration.api.Configuration#getName() name} of
   *        the children.
   * @param namespaceUri
   *        is the
   *        {@link net.sf.mmm.configuration.api.Configuration#getNamespaceUri() namespace-URI}
   *        of the children to iterate or <code>null</code> if namespace will
   *        be ignored.
   */
  public ChildPatternIterator(Iterator<AbstractConfiguration> childIterator,
      Pattern childNamePattern, String namespaceUri) {

    this(childIterator, childNamePattern, namespaceUri, getType(childNamePattern));
  }

  /**
   * This method gets the type according to the given <code>pattern</code>.
   * 
   * @param pattern
   *        is the pattern to check.
   * @return the according type.
   */
  private static Type getType(Pattern pattern) {

    if (pattern.pattern().charAt(0) == Configuration.NAME_PREFIX_ATTRIBUTE) {
      return Type.ATTRIBUTE;
    } else {
      return Type.ELEMENT;
    }
  }

  /**
   * The constructor.
   * 
   * @param childIterator
   *        is the iterator of all children to iterate.
   * @param childNamePattern
   *        is the pattern to be matched by the
   *        {@link net.sf.mmm.configuration.api.Configuration#getName() name} of
   *        the children.
   * @param namespaceUri
   *        is the
   *        {@link net.sf.mmm.configuration.api.Configuration#getNamespaceUri() namespace-URI}
   *        of the children to iterate or <code>null</code> if namespace will
   *        be ignored.
   * @param childType
   *        is the type of the the children to iterate should
   *        {@link Configuration#getType() have} or <code>null</code> if the
   *        type should be ignored.
   */
  public ChildPatternIterator(Iterator<AbstractConfiguration> childIterator,
      Pattern childNamePattern, String namespaceUri, Type childType) {

    super();
    this.iterator = childIterator;
    this.pattern = childNamePattern;
    this.namespace = namespaceUri;
    this.type = childType;
    stepNext();
  }

  /**
   * {@inheritDoc}
   */
  protected boolean stepNext() {

    boolean hasNext = super.stepNext();
    // no sibling left to iterate?
    if (!hasNext) {
      while (this.iterator.hasNext()) {
        AbstractConfiguration next = this.iterator.next();
        String name = next.getName();
        // check namespace
        if ((this.namespace == null) || (this.namespace.equals(next.getNamespaceUri()))) {
          // check type
          if ((this.type == null) || (this.type == next.getType())) {
            // check name pattern
            if (this.pattern.matcher(name).matches()) {
              setNext(next);
              return true;
            }
          }
        }
      }
    }
    return hasNext;
  }

}
