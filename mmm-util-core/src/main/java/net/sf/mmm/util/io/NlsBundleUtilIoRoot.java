/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsBundleMessage;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This interface holds the {@link NlsBundle internationalized messages} for this module.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public interface NlsBundleUtilIoRoot extends NlsBundle {

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpected input/output error has occurred!")
  NlsMessage errorIo();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occurred while reading data!")
  NlsMessage errorIoRead();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occurred while writing data!")
  NlsMessage errorIoWrite();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to close handle!")
  NlsMessage errorIoClose();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Failed to flush handle!")
  NlsMessage errorIoFlush();

  /**
   * @see net.sf.mmm.util.io.api.RuntimeIoException
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("An unexpetected error has occurred while copying data!")
  NlsMessage errorIoCopy();

  /**
   * {@code net.sf.mmm.util.io.api.StreamClosedException}
   *
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("The stream is already closed!")
  NlsMessage errorStreamClosed();

  /**
   * {@code net.sf.mmm.util.io.api.BufferExceedException}
   *
   * @param length the given length.
   * @param capacity the maximum capacity.
   * @return the {@link NlsMessage}
   */
  @NlsBundleMessage("Offset or length \"{length}\" exceeds buffer with capacity \"{capacity}\"!")
  NlsMessage errorBufferLengthExceed(@Named("length") int length, @Named("capacity") int capacity);

}
