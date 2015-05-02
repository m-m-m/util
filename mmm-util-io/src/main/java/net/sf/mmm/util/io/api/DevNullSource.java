/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is an implementation of <code>/dev/null</code> as {@link InputStream}. In other words the
 * {@link DevNullTarget} is a dummy {@link InputStream} that always has reached its end. It will never change
 * its state so it can also be {@link InputStream#close() closed}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DevNullSource extends InputStream {

  /** The singleton instance. */
  public static final DevNullSource INSTANCE = new DevNullSource();

  /**
   * The constructor.
   */
  private DevNullSource() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {

    return -1;
  }

}
