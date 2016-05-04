/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.api;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.util.io.api.DevNullSource;
import net.sf.mmm.util.io.api.DevNullTarget;

/**
 * This class represents the context for a process. It is a java-bean that holds the {@link #getInStream() stdin},
 * {@link #getOutStream() stdout} and {@link #getErrStream() stderr}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ProcessContext {

  private OutputStream outStream;

  private OutputStream errStream;

  private InputStream inStream;

  private boolean keepStreamsOpen;

  /**
   * The constructor. All streams are initialized to act like {@code /dev/null}.
   */
  public ProcessContext() {

    super();
    this.inStream = DevNullSource.INSTANCE;
    this.outStream = DevNullTarget.INSTANCE;
    this.errStream = DevNullTarget.INSTANCE;
  }

  /**
   * @return the outStream
   */
  public OutputStream getOutStream() {

    return this.outStream;
  }

  /**
   * @param outStream the outStream to set
   */
  public void setOutStream(OutputStream outStream) {

    this.outStream = outStream;
  }

  /**
   * @return the errStream
   */
  public OutputStream getErrStream() {

    return this.errStream;
  }

  /**
   * @param errStream the errStream to set
   */
  public void setErrStream(OutputStream errStream) {

    this.errStream = errStream;
  }

  /**
   * This method gets the input stream that is transferred to the process as {@code stdin}.
   *
   * @return the inStream.
   */
  public InputStream getInStream() {

    return this.inStream;
  }

  /**
   * @param inStream the inStream to set
   */
  public void setInStream(InputStream inStream) {

    this.inStream = inStream;
  }

  /**
   * @return the keepStreamsOpen
   */
  public boolean isKeepStreamsOpen() {

    return this.keepStreamsOpen;
  }

  /**
   * @param keepStreamsOpen the keepStreamsOpen to set
   */
  public void setKeepStreamsOpen(boolean keepStreamsOpen) {

    this.keepStreamsOpen = keepStreamsOpen;
  }

}
