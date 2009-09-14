/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import net.sf.mmm.util.io.api.DetectorInputStream;
import net.sf.mmm.util.io.api.DetectorStreamProvider;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessorFactory;
import net.sf.mmm.util.io.base.SimpleDetectorStreamProcessorFactory;
import net.sf.mmm.util.pool.api.ByteArrayPool;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the test-case for the detector-stream-framework.
 * 
 * @see DetectorStreamProvider
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class DetectorStreamTest {

  protected DetectorStreamProvider getProvider(ByteArrayPool pool) {

    DetectorStreamProviderImpl provider = new DetectorStreamProviderImpl();
    provider.setByteArrayPool(pool);
    List<DetectorStreamProcessorFactory> processorFactoryList = new ArrayList<DetectorStreamProcessorFactory>();
    processorFactoryList.add(new SimpleDetectorStreamProcessorFactory(
        DetectorStreamProcessorCountX.class));
    processorFactoryList.add(new SimpleDetectorStreamProcessorFactory(
        DetectorStreamProcessorReplaceXx.class));
    provider.setProcessorFactoryList(processorFactoryList);
    provider.initialize();
    return provider;
  }

  protected DummyByteArrayPool getByteArrayPool() {

    return new DummyByteArrayPool(16);
  }

  @Test
  public void testFoo() throws Exception {

    DummyByteArrayPool pool = getByteArrayPool();
    DataResource resource = new ClasspathResource(DetectorStreamTest.class, ".txt", true);
    InputStream inStream = resource.openStream();
    try {
      DetectorInputStream detectorStream = getProvider(pool).wrapInputStream(inStream);
      InputStream wrappedStream = detectorStream.getStream();
      InputStreamReader reader = new InputStreamReader(wrappedStream);
      BufferedReader bufferedReader = new BufferedReader(reader);
      String line;
      line = bufferedReader.readLine();
      Assert.assertEquals("Hello World, this is a test...", line);
      line = bufferedReader.readLine();
      Assert.assertEquals("It has some chars, words and lines.", line);
      line = bufferedReader.readLine();
      Assert.assertEquals("", line);
      line = bufferedReader.readLine();
      Assert.assertEquals("Especially this strange sequence:", line);
      line = bufferedReader.readLine();
      // Assert.assertEquals("some xx is contained in fooxxbar and axxbxxcxxxxd.",
      // line);
      Assert.assertEquals("some y is contained in fooybar and aybycyyd.", line);
      line = bufferedReader.readLine();
      Assert.assertNull(line);
      Assert.assertTrue(detectorStream.isDone());
      Object xCount = detectorStream.getMetadata().get(DetectorStreamProcessorCountX.KEY_X_COUNT);
      Assert.assertEquals(Integer.valueOf(12), xCount);

      Assert.assertEquals(0, pool.bufferSet.size());
    } finally {
      inStream.close();
    }
  }

  protected static class DummyByteArrayPool implements ByteArrayPool {

    /** @see #release(byte[]) */
    private final Set<byte[]> bufferSet;

    private final int bufferSize;

    /**
     * The constructor.
     */
    public DummyByteArrayPool(int bufferSize) {

      super();
      this.bufferSet = new HashSet<byte[]>();
      this.bufferSize = bufferSize;
    }

    /**
     * {@inheritDoc}
     */
    public byte[] borrow() {

      byte[] buffer = new byte[this.bufferSize];
      this.bufferSet.add(buffer);
      return buffer;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    public void release(byte[] element) {

      boolean okay = this.bufferSet.remove(element);
      if (!okay) {
        if (element.length != this.bufferSize) {
          throw new IllegalStateException("Foreign byte-buffer released!");
        } else {
          throw new IllegalStateException("Foreign/duplicate byte-buffer released!");
        }
      }
    }
  }

}
