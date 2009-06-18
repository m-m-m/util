/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import net.sf.mmm.util.io.api.ByteProvider;

/**
 * This is the abstract base implementation of the {@link ByteProvider}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public abstract class AbstractByteProvider implements ByteProvider {

  /**
   * {@inheritDoc}
   */
  public boolean isEmpty() {

    return (getBytesAvailable() == 0);
  }

}
