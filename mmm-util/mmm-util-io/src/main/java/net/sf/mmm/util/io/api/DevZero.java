/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is an implementation of <code>/dev/zero</code> as {@link InputStream}. In other words the
 * {@link DevZero} is a dummy {@link InputStream} that produces infinite zeros. <br>
 * <b>ATTENTION:</b><br>
 * Please be careful NOT to cause infinity loops when using this class!
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class DevZero extends InputStream {

  /** The singleton instance. */
  public static final DevZero INSTANCE = new DevZero();

  /**
   * The constructor.
   */
  private DevZero() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b, int off, int len) {

    for (int i = off; i < len; i++) {
      b[i] = 0;
    }
    return len;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {

    // do nothing...
  }

}
