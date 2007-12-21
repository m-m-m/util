/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link ProcessUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ProcessUtilTest {

  public ProcessUtil getProcessUtil() {

    return ProcessUtil.getInstance();
  }

  @Test
  public void testExecute() throws Exception {

    ProcessContext context = new ProcessContext();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    context.setErrStream(errStream);
    int exitCode = getProcessUtil().execute(context, new ProcessBuilder("java", "-version"));
    assertEquals(0, exitCode);
    byte[] errBytes = errStream.toByteArray();
    String errString = new String(errBytes);
    assertTrue(errString.contains("java version"));
  }

  @Test
  public void testExecuteAsync() throws Exception {

    ProcessContext context = new ProcessContext();
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    context.setErrStream(errStream);
    AsyncProcessExecutor executor = getProcessUtil().executeAsync(context,
        new ProcessBuilder("java", "-version"));
    int exitCode = executor.get().intValue();
    assertEquals(0, exitCode);
    byte[] errBytes = errStream.toByteArray();
    String errString = new String(errBytes);
    assertTrue(errString.contains("java version"));
  }

  @Test(timeout = 10000)
  public void testExecuteAsyncTimeout() throws Exception {

    ProcessContext context = new ProcessContext();
    ProcessBuilder builder = new ProcessBuilder("java", "-classpath", "target/test-classes",
        SleepApp.class.getName());
    long start = System.currentTimeMillis();
    try {
      int exitCode = getProcessUtil().execute(context, 100, TimeUnit.MILLISECONDS, builder);
      fail("TimeoutException expected!");
    } catch (TimeoutException e) {
      // expected
      long delay = System.currentTimeMillis() - start;
      assertTrue(delay >= 100);
      // test should also work under load so we do NOT check upper bound...
      // assertTrue(delay < 200);
    }
  }

  @Test(timeout = 10000)
  public void testExecuteAsyncStop() throws Exception {

    ProcessContext context = new ProcessContext();
    DummyInputStream inStream = new DummyInputStream();
    DummyOutputStream outStream = new DummyOutputStream();
    DummyOutputStream errStream = new DummyOutputStream();
    context.setInStream(inStream);
    context.setErrStream(errStream);
    context.setOutStream(outStream);
    ProcessBuilder builder = new ProcessBuilder("java", "-classpath", "target/test-classes",
        SleepApp.class.getName());
    assertFalse(inStream.isClosed());
    AsyncProcessExecutor executor = getProcessUtil().executeAsync(context, builder);
    Thread.sleep(10);
    assertFalse(outStream.isClosed());
    assertFalse(errStream.isClosed());
    boolean stopped = executor.cancel(true);
    assertTrue(stopped);
    Thread.sleep(10);
    assertTrue(inStream.isClosed());
    assertTrue(outStream.isClosed());
    assertTrue(errStream.isClosed());
  }

  @Ignore
  @Test(timeout = 10000)
  public void testExecuteAsyncStopChildProcess() throws Exception {

    ProcessContext context = new ProcessContext();
    ProcessBuilder builder = new ProcessBuilder("java", "-classpath", "target/test-classes",
        SwingApp.class.getName());
    AsyncProcessExecutor executor = getProcessUtil().executeAsync(context, builder);
    Thread.sleep(2000);
    boolean stopped = executor.cancel(true);
    assertTrue(stopped);
  }

  protected static class DummyInputStream extends InputStream {

    private volatile boolean closed;

    /**
     * @return <code>true</code> if this stream was {@link #close() closed}.
     */
    public boolean isClosed() {

      return this.closed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {

      this.closed = true;
      super.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {

      return -1;
    }

  }

  protected static class DummyOutputStream extends OutputStream {

    private volatile boolean closed;

    /**
     * @return <code>true</code> if this stream was {@link #close() closed}.
     */
    public boolean isClosed() {

      return this.closed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {

      this.closed = true;
      super.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {

    }

  }

}
