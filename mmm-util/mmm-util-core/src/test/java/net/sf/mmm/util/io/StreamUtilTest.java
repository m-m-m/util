/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

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

import org.apache.commons.logging.impl.NoOpLog;
import org.junit.Test;

import net.sf.mmm.util.BasicUtil;
import net.sf.mmm.util.concurrent.SimpleExecutor;
import net.sf.mmm.util.io.api.AsyncTransferrer;
import net.sf.mmm.util.io.api.TransferCallback;
import net.sf.mmm.util.io.base.DevZero;
import net.sf.mmm.util.pool.base.NoByteArrayPool;
import net.sf.mmm.util.pool.base.NoCharArrayPool;

/**
 * This is the test-case for {@link StreamUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StreamUtilTest {

  public StreamUtil getStreamUtil() {

    return StreamUtil.getInstance();
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
    assertTrue(BasicUtil.getInstance().isDeepEqual(data, outStream.toByteArray()));
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
    assertTrue(BasicUtil.getInstance().isDeepEqual(data, outStream.toByteArray()));
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
    assertTrue(BasicUtil.getInstance().isDeepEqual(data, outStream.toByteArray()));
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
    StreamUtil streamUtil = new StreamUtil();
    streamUtil.setExecutor(SimpleExecutor.INSTANCE);
    streamUtil.setLogger(new NoOpLog());
    streamUtil.setByteArrayPool(NoByteArrayPool.INSTANCE);
    streamUtil.setCharArrayPool(NoCharArrayPool.INSTANCE);
    AsyncTransferrer transferrer = streamUtil.transferAsync(inStream, outStream, true, callback);
    try {
      transferrer.get();
      fail("expected " + ExecutionException.class);
    } catch (ExecutionException e) {
      assertSame(error, e.getCause());
    }
    assertEquals(0, outStream.size());
    assertSame(error, callback.exception);
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
