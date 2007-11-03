/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.term.base.AbstractTerm;

/**
 * This is the implementation of a constant
 * {@link net.sf.mmm.term.api.Term term}. It simply holds a value as constant
 * that is always returned as {@link #evaluate(Context) evaluation} result.
 * 
 * @param <C> is the templated type of the constant.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Constant<C> extends AbstractTerm {

  /** @see XmlSerializerImpl */
  public static final String XML_TAG_VALUE = "value";

  /** @see XmlSerializerImpl */
  public static final String XML_TAG_NULL = "null";

  /** @see XmlSerializerImpl */
  public static final String XML_ATR_VALUE_CLASS = "class";

  /** uid for serialization */
  private static final long serialVersionUID = 8680487402807187621L;

  /** the <code>null</code> constant */
  public static final Constant<Object> NULL = new Constant<Object>(null);

  /** the <code>true</code> constant */
  public static final Constant<Boolean> TRUE = new Constant<Boolean>(Boolean.TRUE);

  /** the <code>false</code> constant */
  public static final Constant<Boolean> FALSE = new Constant<Boolean>(Boolean.FALSE);

  /** the constant value */
  private final C value;

  /**
   * The dummy constructor. Only use for testing or outside the project.
   * 
   * @param constantValue is the value of this constant.
   */
  public Constant(C constantValue) {

    super();
    this.value = constantValue;
  }

  /**
   * {@inheritDoc}
   */
  public C evaluate(Context variableSet) {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.value.toString();
  }

}
