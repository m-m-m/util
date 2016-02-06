/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;

import net.sf.mmm.logging.TestLogger;
import net.sf.mmm.logging.TestLogger.LogEvent;
import net.sf.mmm.logging.TestLogger.LogLevel;
import net.sf.mmm.test.ExceptionHelper;
import net.sf.mmm.util.io.api.AsyncTransferrer;
import net.sf.mmm.util.io.api.DevZero;
import net.sf.mmm.util.io.api.StreamUtil;
import net.sf.mmm.util.io.api.TransferCallback;

/**
 * This is the test-case for {@link StreamUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class StreamUtilTest extends Assertions {

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
    assertThat(bytes).isEqualTo(len);
    assertThat(writer.toString()).isEqualTo(s);
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
    long bytes = getStreamUtil().transfer(inStream, outStream, false);
    assertThat(bytes).isEqualTo(len);
    assertThat(outStream.toByteArray()).containsExactly(data);
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
    assertThat(bytes.longValue()).isEqualTo(len);
    assertThat(outStream.toByteArray()).containsExactly(data);
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
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(inStream, outStream, true, callback);
    Long bytes = transferrer.get();
    assertThat(bytes.longValue()).isEqualTo(len);
    assertThat(callback.bytesCompleted.longValue()).isEqualTo(len);
    assertThat(outStream.toByteArray()).containsExactly(data);
  }

  @Test
  public void testTransferStreamAsyncStop() throws Exception {

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(DevZero.INSTANCE, outStream, true);
    Thread.sleep(200);
    long size = outStream.size();
    transferrer.cancel(true);
    assertThat(size).isPositive();
    Thread.sleep(10);
    size = outStream.size();
    Thread.sleep(10);
    assertThat(outStream.size()).isEqualTo((int) size);
  }

  @Test
  public void testTransferReaderAsyncStop() throws Exception {

    InputStreamReader reader = new InputStreamReader(DevZero.INSTANCE);
    StringWriter writer = new StringWriter();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(reader, writer, true);
    Thread.sleep(200);
    long size = writer.getBuffer().length();
    transferrer.cancel(true);
    assertThat(size).isPositive();
    Thread.sleep(10);
    size = writer.getBuffer().length();
    Thread.sleep(10);
    assertThat(writer.getBuffer().length()).isEqualTo((int) size);
  }

  @Test
  public void testTransferStreamAsyncCallbackStop() throws Exception {

    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    Callback callback = new Callback();
    AsyncTransferrer transferrer = getStreamUtil().transferAsync(DevZero.INSTANCE, outStream, true, callback);
    Thread.sleep(200);
    long size = outStream.size();
    transferrer.cancel(true);
    assertThat(size).isPositive();
    Thread.sleep(10);
    size = outStream.size();
    Long bytesStopped = callback.bytesStopped;
    assertThat(bytesStopped).isNotNull().isEqualTo(size);
  }

  @Test
  public void testTransferStreamAsyncCallbackFail() throws Exception {

    final IOException error = new IOException("This is a test!");
    InputStream inStream = new InputStream() {

      @Override
      public int read() throws IOException {

        throw error;
      }
    };
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    Callback callback = new Callback();
    StreamUtilImplWithTestLogger streamUtil = new StreamUtilImplWithTestLogger();
    TestLogger logger = streamUtil.getLogger();
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
    assertThat(outStream.size()).isZero();
    ExceptionHelper.assertCause(callback.exception, error);
    boolean errorWasLogged = false;
    for (LogEvent logEvent : logger.getEventList()) {
      if (logEvent.getThrowable() != null) {
        if (ExceptionHelper.isCause(logEvent.getThrowable(), error)) {
          assertThat(logEvent.getLevel()).isEqualTo(LogLevel.ERROR);
          errorWasLogged = true;
        }
      }
    }
    assertThat(errorWasLogged).isTrue();
  }

  private static class Callback implements TransferCallback {

    private Exception exception;

    private Long bytesCompleted;

    private Long bytesStopped;

    public void expectEmpty() {

      assertThat(this.exception).isNull();
      assertThat(this.bytesCompleted).isNull();
      assertThat(this.bytesStopped).isNull();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transferCompleted(long bytesTransferred) {

      expectEmpty();
      this.bytesCompleted = Long.valueOf(bytesTransferred);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transferFailed(Exception e) {

      expectEmpty();
      this.exception = e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transferStopped(long bytesTransferred) {

      expectEmpty();
      this.bytesStopped = Long.valueOf(bytesTransferred);
    }

  }

  public static class StreamUtilImplWithTestLogger extends StreamUtilImpl {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Logger createLogger() {

      return new TestLogger();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TestLogger getLogger() {

      return (TestLogger) super.getLogger();
    }
  }
}
