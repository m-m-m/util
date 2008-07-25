/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the callback interface used to receive the status of an
 * {@link AsyncTransferrer}. Exactly one of the methods declared in this
 * interface are called when the transfer is done (successful or unsuccessful).<br>
 * <b>ATTENTION:</b><br>
 * The according method of this callback will (typically) be invoked by a
 * different {@link Thread}. Please ensure that your implementation is
 * thread-safe.
 * 
 * @see net.sf.mmm.util.io.base.StreamUtilImpl#transferAsync(java.io.InputStream,
 *      java.io.OutputStream, boolean, TransferCallback)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransferCallback {

  /**
   * This method is invoked if the transfer completed successfully.
   * 
   * @param bytesTransferred is the number of bytes that have been transferred.
   */
  void transferCompleted(long bytesTransferred);

  /**
   * This method is invoked if the transfer was stopped before it completed.
   * 
   * @param bytesTransferred is the number of bytes that have been transferred
   *        until the task has been stopped.
   */
  void transferStopped(long bytesTransferred);

  /**
   * This method is invoked if the transfer failed because an exception
   * occurred. This will typically be an {@link java.io.IOException}. It may
   * also be a {@link RuntimeException}.
   * 
   * @param e is the exception indicating the problem.
   */
  void transferFailed(Exception e);

}
