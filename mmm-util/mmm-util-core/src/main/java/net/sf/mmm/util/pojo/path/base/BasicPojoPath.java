/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import net.sf.mmm.util.pojo.path.api.IllegalPojoPathException;
import net.sf.mmm.util.pojo.path.api.PojoPath;
import net.sf.mmm.util.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.pojo.path.api.PojoPropertyPath;

/**
 * This class represents a {@link PojoPath}. It contains the internal logic to validate and parse a
 * {@link PojoPath}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class BasicPojoPath implements PojoPath {

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
  public BasicPojoPath(String pojoPath) {

    super();
    if (pojoPath == null) {
      throw new IllegalPojoPathException(pojoPath);
    }
    this.pojoPath = pojoPath;
    String pParentPath = null;
    String pSegment = pojoPath;
    String pFunction = null;
    Integer pIndex = null;
    if (pojoPath.length() > 0) {
      int lastDot = pojoPath.lastIndexOf(PojoPropertyPath.SEPARATOR);
      if ((lastDot == 0) || (lastDot == (pojoPath.length() - 1))) {
        // starts or ends with dot
        throw new IllegalPojoPathException(pojoPath);
      }
      if (lastDot > 0) {
        pParentPath = pojoPath.substring(0, lastDot);
        pSegment = pojoPath.substring(lastDot + 1);
      }
      char firstChar = pSegment.charAt(0);
      if (firstChar == PojoPathFunction.FUNCTION_NAME_PREFIX) {
        pFunction = pSegment.substring(1);
        if (pFunction.length() == 0) {
          throw new IllegalPojoPathException(pojoPath);
        }
      } else {
        if ((firstChar >= '0') && (firstChar <= '9')) {
          try {
            pIndex = Integer.valueOf(pSegment);
          } catch (NumberFormatException e) {
            throw new IllegalPojoPathException(e, pojoPath);
          }
        }
      }
    }
    this.segment = pSegment;
    this.parentPath = pParentPath;
    this.function = pFunction;
    this.index = pIndex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPojoPath() {

    return this.pojoPath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getParentPath() {

    return this.parentPath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSegment() {

    return this.segment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFunction() {

    return this.function;
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
