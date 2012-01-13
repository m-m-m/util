/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.junit.Test;

import net.sf.mmm.logging.TestLogger;
import net.sf.mmm.logging.TestLogger.LogEvent;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.test.TestStrings;
import net.sf.mmm.util.io.api.AsyncTransferrer;
import net.sf.mmm.util.io.api.DevZero;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.api.TransferCallback;
import net.sf.mmm.util.lang.base.BasicUtilImpl;

/**
 * This is the test-case for {@link StreamUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StreamUtilTest {

  protected StreamUtil getStreamUtil() {

    return StreamUtilImpl.getInstance();
  }

  @Test
  public void testTransferWriter() throws IOException {

    int len = 4096 + 4096 + 1;
    StringBuilder builder = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      builder.append((char) i);
    }
    String s = builder.toString();
    StringReader reader = new StringReader(s);
    StringWriter writer = new StringWriter();
    long bytes = getStreamUtil().transfer(reader, writer, true);
    assertEquals(len, bytes);
    assertEquals(s, writer.toString());
  }

  @Test
  public void testTransferStream() throws IOException {

    int len = 4096 + 4096 + 1;
    byte[] data = new byte[len];
    for (int i = 0; i < len; i++) {
      data[i] = (byte) i;
    }
    ByteArrayInputStream inStream = new ByteArrayInputStream(data);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    long bytes = getStreamUtil().transfer(inStream, outStream, true);
    assertEquals(len, bytes);
    assertTrue(BasicUtilImpl.getInstance().isDeepEqual(data, outStream.toByteArray()));
  }

  @Test
  public void testTransferStreamAsync() throws Exception {

    int len = 4096 + 4096 + 1;
    byte[] data = new byte[len];
    for (int i = 0; i < len; i++) {
      data[i] = (byte) i;
    }
    ByteArrayInputStream inStream = new ByteArrayInputStream(data);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(inStream, outStream, true);
    Long bytes = transferrer.get();
    assertEquals(len, bytes.longValue());
    assertTrue(BasicUtilImpl.getInstance().isDeepEqual(data, outStream.toByteArray()));
  }

  @Test
  public void testTransferStreamAsyncCallbackComplete() throws Exception {

    int len = 4096 + 4096 + 1;
    byte[] data = new byte[len];
    for (int i = 0; i < len; i++) {
      data[i] = (byte) i;
    }
    ByteArrayInputStream inStream = new ByteArrayInputStream(data);
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    Callback callback = new Callback();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(inStream, outStream, true,
        callback);
    Long bytes = transferrer.get();
    assertEquals(len, bytes.longValue());
    assertEquals(len, callback.bytesCompleted.longValue());
    assertTrue(BasicUtilImpl.getInstance().isDeepEqual(data, outStream.toByteArray()));
  }

  @Test
  public void testTransferStreamAsyncStop() throws Exception {

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(DevZero.INSTANCE, outStream, true);
    Thread.sleep(10);
    long size = outStream.size();
    transferrer.cancel(true);
    assertTrue(size > 0);
    Thread.sleep(10);
    size = outStream.size();
    Thread.sleep(10);
    assertEquals(size, outStream.size());
  }

  @Test
  public void testTransferReaderAsyncStop() throws Exception {

    InputStreamReader reader = new InputStreamReader(DevZero.INSTANCE);
    StringWriter writer = new StringWriter();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(reader, writer, true);
    Thread.sleep(10);
    long size = writer.getBuffer().length();
    transferrer.cancel(true);
    assertTrue(size > 0);
    Thread.sleep(10);
    size = writer.getBuffer().length();
    Thread.sleep(10);
    assertEquals(size, writer.getBuffer().length());
  }

  @Test
  public void testTransferStreamAsyncCallbackStop() throws Exception {

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    Callback callback = new Callback();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(DevZero.INSTANCE, outStream, true,
        callback);
    Thread.sleep(10);
    long size = outStream.size();
    transferrer.cancel(true);
    assertTrue(size > 0);
    Thread.sleep(10);
    size = outStream.size();
    assertEquals(size, callback.bytesStopped.longValue());
  }

  @Test
  public void testTransferStreamAsyncCallbackFail() throws Exception {

    final IOException error = new IOException("This is a test!");
    InputStream inStream = new InputStream() {

      public int read() throws IOException {

        throw error;
      }
    };
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    Callback callback = new Callback();
    StreamUtilImpl streamUtil = new StreamUtilImpl();
    TestLogger logger = new TestLogger();
    streamUtil.setLogger(logger);
    streamUtil.initialize();
    AsyncTransferrer transferrer = streamUtil.transferAsync(inStream, outStream, true, callback);
    try {
      transferrer.get();
      fail("expected " + ExecutionException.class);
    } catch (ExecutionException e) {
      if (error != e.getCause()) {
        e.printStackTrace();
      }
      ExceptionHelper.assertCause(e, error);
    }
    assertEquals(0, outStream.size());
    ExceptionHelper.assertCause(callback.exception, error);
    boolean errorWasLogged = false;
    for (LogEvent logEvent : logger.getEventList()) {
      if (ExceptionHelper.isCause(logEvent.throwable, error)) {
        errorWasLogged = true;
      }
    }
    Assert.assertTrue(errorWasLogged);
  }

  private static class Callback implements TransferCallback {

    private Exception exception;

    private Long bytesCompleted;

    private Long bytesStopped;

    public void expectEmpty() {

      assertTrue(this.exception == null);
      assertTrue(this.bytesCompleted == null);
      assertTrue(this.bytesStopped == null);
    }

    /**
     * {@inheritDoc}
     */
    public void transferCompleted(long bytesTransferred) {

      expectEmpty();
      this.bytesCompleted = Long.valueOf(bytesTransferred);
    }

    /**
     * {@inheritDoc}
     */
    public void transferFailed(Exception e) {

      expectEmpty();
      this.exception = e;
    }

    /**
     * {@inheritDoc}
     */
    public void transferStopped(long bytesTransferred) {

      expectEmpty();
      this.bytesStopped = Long.valueOf(bytesTransferred);
    }

  }

}
