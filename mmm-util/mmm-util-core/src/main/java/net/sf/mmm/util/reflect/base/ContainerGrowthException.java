/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link ContainerGrowthException} is thrown if a container should grow
 * (increase its size) but this failed. Here a container stands for an array
 * (that can never grow) or a {@link java.util.Collection}.
 * 
 * @see ReflectionUtilImpl#toType(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ContainerGrowthException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -9175146459502826035L;

  /**
   * The constructor.
   * 
   * @param size is the number of entries to increase the container.
   * @param max is the maximum increase allowed.
   */
  public ContainerGrowthException(int size, int max) {

    super(NlsBundleUtilCore.ERR_INCREASE_EXCEEDS_MAX_GROWTH, toMap(KEY_SIZE, Integer.valueOf(size),
        KEY_MAX, Integer.valueOf(max)));
  }

}
