/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.sf.mmm.test.TestCategoryManual;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.process.api.AsyncProcessExecutor;
import net.sf.mmm.util.process.api.ProcessContext;
import net.sf.mmm.util.process.api.ProcessUtil;

/**
 * This is the test-case for {@link ProcessUtilImpl}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ProcessUtilTest {

  /** The platform specific line separator. */
  private static final String LINE_SEPARATOR = System.getProperty(StringUtil.SYSTEM_PROPERTY_LINE_SEPARATOR);

  /** The classpath for java execution. */
  private static final String CLASSPATH = "target/test-classes" + File.pathSeparatorChar + "eclipse-target/test-classes";

  public ProcessUtil getProcessUtil() {

    return ProcessUtilImpl.getInstance();
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
    AsyncProcessExecutor executor = getProcessUtil().executeAsync(context, new ProcessBuilder("java", "-version"));
    int exitCode = executor.get().intValue();
    assertEquals(0, exitCode);
    byte[] errBytes = errStream.toByteArray();
    String errString = new String(errBytes);
    assertTrue(errString.contains("java version"));
  }

  @Test
  @Category(TestCategoryManual.class)
  public void testExecuteAsyncTimeout() throws Exception {

    ProcessContext context = new ProcessContext();
    ProcessBuilder builder = new ProcessBuilder("java", "-classpath", CLASSPATH, SleepApp.class.getName());
    long start = System.currentTimeMillis();
    try {
      int exitCode = getProcessUtil().execute(context, 100, TimeUnit.MILLISECONDS, builder);
      fail("TimeoutException expected!");
    } catch (TimeoutException e) {
      // expected
      long delay = System.currentTimeMillis() - start;
      assertTrue("Delay was " + delay, delay >= 100);
      // test should also work under load so we do NOT check upper bound...
      // assertTrue(delay < 200);
    }
  }

  @Test
  @Category(TestCategoryManual.class)
  public void testExecuteAsyncStop() throws Exception {

    ProcessContext context = new ProcessContext();
    DummyInputStream inStream = new DummyInputStream();
    DummyOutputStream outStream = new DummyOutputStream();
    DummyOutputStream errStream = new DummyOutputStream();
    context.setInStream(inStream);
    context.setErrStream(errStream);
    context.setOutStream(outStream);
    ProcessBuilder builder = new ProcessBuilder("java", "-classpath", CLASSPATH, SleepApp.class.getName());
    assertFalse(inStream.isClosed());
    AsyncProcessExecutor executor = getProcessUtil().executeAsync(context, builder);
    Thread.sleep(1);
    assertFalse(outStream.isClosed());
    assertFalse(errStream.isClosed());
    boolean stopped = executor.cancel(true);
    assertTrue(stopped);
    Thread.sleep(10);
    assertTrue(inStream.isClosed());
    assertTrue(outStream.isClosed());
    assertTrue(errStream.isClosed());
  }

  @Test
  @Category(TestCategoryManual.class)
  public void testExecuteAsyncStopChildProcess() throws Exception {

    ProcessContext context = new ProcessContext();
    ProcessBuilder builder = new ProcessBuilder("java", "-classpath", CLASSPATH, SwingApp.class.getName());
    AsyncProcessExecutor executor = getProcessUtil().executeAsync(context, builder);
    Thread.sleep(2000);
    boolean stopped = executor.cancel(true);
    assertTrue(stopped);
  }

  @Test
  @Category(TestCategoryManual.class)
  public void testExecutePipe() throws Exception {

    ProcessContext context = new ProcessContext();
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    context.setOutStream(outStream);
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    context.setErrStream(errStream);
    ProcessBuilder app1Builder = new ProcessBuilder("java", "-classpath", CLASSPATH, PipeApp1.class.getName());
    ProcessBuilder app2Builder = new ProcessBuilder("java", "-classpath", CLASSPATH, PipeApp2.class.getName());
    int exitCode = getProcessUtil().execute(context, app1Builder, app2Builder);
    assertEquals(0, exitCode);
    Thread.sleep(10);
    // test output of stdout
    byte[] outBytes = outStream.toByteArray();
    String outString = new String(outBytes);
    StringBuilder expectedOutString = new StringBuilder(30);
    for (int i = 1; i <= 10; i++) {
      expectedOutString.append(Integer.toString(i));
      expectedOutString.append(LINE_SEPARATOR);
    }
    assertEquals(expectedOutString.toString(), outString);
    // test output of stderr
    byte[] errBytes = errStream.toByteArray();
    String errString = new String(errBytes);
    String expectedErrString = PipeApp1.class.getSimpleName() + " done." + LINE_SEPARATOR + PipeApp2.class.getSimpleName() + " done." + LINE_SEPARATOR;
    assertEquals(expectedErrString, errString);
  }

  protected static class DummyInputStream extends InputStream {

    private volatile boolean closed;

    /**
     * @return {@code true} if this stream was {@link #close() closed}.
     */
    public boolean isClosed() {

      return this.closed;
    }

    @Override
    public void close() throws IOException {

      this.closed = true;
      super.close();
    }

    @Override
    public int read() throws IOException {

      return -1;
    }

  }

  protected static class DummyOutputStream extends OutputStream {

    private volatile boolean closed;

    /**
     * @return {@code true} if this stream was {@link #close() closed}.
     */
    public boolean isClosed() {

      return this.closed;
    }

    @Override
    public void close() throws IOException {

      this.closed = true;
      super.close();
    }

    @Override
    public void write(int b) throws IOException {

    }

  }

}
