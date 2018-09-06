/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;

/**
 * A {@link ContainerGrowthException} is thrown if a container should grow (increase its size) but this
 * failed. Here a container stands for an array (that can never grow) or a {@link java.util.Collection}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ContainerGrowthException extends RuntimeException implements AttributeReadMessageCode {

  private static final long serialVersionUID = -9175146459502826035L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ContainerGrowth";

  /**
   * The constructor.
   *
   * @param size is the number of entries to increase the container.
   * @param max is the maximum increase allowed.
   */
  public ContainerGrowthException(int size, int max) {

    super("Can not increase size of array or list by '" + size + "', because limit is '" + max + "'!");
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
