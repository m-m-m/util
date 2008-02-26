/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import net.sf.mmm.util.reflect.pojo.path.api.IllegalPojoPathException;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPath;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathNavigator;

/**
 * This class represents a {@link PojoPath}. It contains the internal logic to
 * validate and parse the {@link PojoPath}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimplePojoPath implements PojoPath {

  /** @see #getPojoPath() */
  private final String pojoPath;

  /** @see #getParentPath() */
  private final String parentPath;

  /** @see #getSegment() */
  private final String segment;

  /** @see #getFunction() */
  private final String function;

  /** @see #getIndex() */
  private final Integer index;

  /**
   * The constructor.
   * 
   * @param pojoPath is the {@link #getPojoPath() path} to represent.
   */
  public SimplePojoPath(String pojoPath) {

    super();
    if ((pojoPath == null) || (pojoPath.length() == 0)) {
      throw new IllegalPojoPathException(pojoPath);
    }
    this.pojoPath = pojoPath;
    int lastDot = pojoPath.lastIndexOf(PojoPathNavigator.SEPARATOR);
    if (lastDot < 0) {
      this.parentPath = null;
      this.segment = pojoPath;
    } else if ((lastDot == 0) || (lastDot == (pojoPath.length() - 1))) {
      // starts or ends with dot
      throw new IllegalPojoPathException(pojoPath);
    } else {
      this.parentPath = pojoPath.substring(0, lastDot);
      this.segment = pojoPath.substring(lastDot);
    }
    char firstChar = this.segment.charAt(0);
    if (firstChar == PojoPathFunction.FUNCTION_NAME_PREFIX) {
      this.function = this.segment.substring(1);
      if (this.function.length() == 0) {
        throw new IllegalPojoPathException(pojoPath);
      }
      this.index = null;
    } else {
      this.function = null;
      if ((firstChar >= '0') && (firstChar <= '9')) {
        try {
          this.index = Integer.valueOf(this.segment);
        } catch (NumberFormatException e) {
          throw new IllegalPojoPathException(pojoPath, e);
        }
      } else {
        this.index = null;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getPojoPath() {

    return this.pojoPath;
  }

  /**
   * {@inheritDoc}
   */
  public String getParentPath() {

    return this.parentPath;
  }

  /**
   * {@inheritDoc}
   */
  public String getSegment() {

    return this.segment;
  }

  /**
   * This method gets the name of the {@link PojoPathFunction} identified by the
   * {@link #getSegment() segment} or <code>null</code> if no
   * {@link PojoPathFunction}.
   * 
   * @return the name of the {@link PojoPathFunction} or <code>null</code> if
   *         the {@link #getSegment() segment} does NOT start with
   *         {@link PojoPathFunction#FUNCTION_NAME_PREFIX}.
   */
  public String getFunction() {

    return this.function;
  }

  /**
   * This method gets the index given by the {@link #getSegment() segment} or
   * <code>null</code> if no index.
   * 
   * @return the index given by the {@link #getSegment() segment} or
   *         <code>null</code> if the {@link #getSegment() segment} does NOT
   *         start with a Latin digit.
   */
  public Integer getIndex() {

    return this.index;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.pojoPath;
  }

}
