/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.base.MathUtilImpl;

/**
 * This is the implementation of the {@link SearchDependencies} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchDependenciesImpl extends AbstractComponent implements SearchDependencies {

  /** @see #getMathUtil() */
  private MathUtil mathUtil;

  /** @see #getIso8601Util() */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public SearchDependenciesImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MathUtil getMathUtil() {

    return this.mathUtil;
  }

  /**
   * @param mathUtil is the mathUtil to set
   */
  @Inject
  public void setMathUtil(MathUtil mathUtil) {

    getInitializationState().requireNotInitilized();
    this.mathUtil = mathUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iso8601Util getIso8601Util() {

    return this.iso8601Util;
  }

  /**
   * @param iso8601Util is the iso8601Util to set
   */
  @Inject
  public void setIso8601Util(Iso8601Util iso8601Util) {

    getInitializationState().requireNotInitilized();
    this.iso8601Util = iso8601Util;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.mathUtil == null) {
      this.mathUtil = MathUtilImpl.getInstance();
    }
    if (this.iso8601Util == null) {
      this.iso8601Util = Iso8601UtilImpl.getInstance();
    }
  }

}
