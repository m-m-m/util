/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.util.io.api.spi.DetectorStreamProcessorFactory} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class SimpleDetectorStreamProcessorFactory extends AbstractDetectorStreamProcessorFactory {

  /** @see #setProcessorImplementation(Class) */
  private Class<? extends DetectorStreamProcessor> processorImplementation;

  /**
   * The constructor.
   */
  public SimpleDetectorStreamProcessorFactory() {

    super();
    this.processorImplementation = null;
  }

  /**
   * The constructor.
   * 
   * @param processorImplementation is the
   *        {@link #setProcessorImplementation(Class) processor-implementation}.
   */
  public SimpleDetectorStreamProcessorFactory(
      Class<? extends DetectorStreamProcessor> processorImplementation) {

    super();
    this.processorImplementation = processorImplementation;
  }

  /**
   * @param processorImplementation is the processorImplementation to set
   */
  public void setProcessorImplementation(Class<DetectorStreamProcessor> processorImplementation) {

    this.processorImplementation = processorImplementation;
  }

  /**
   * {@inheritDoc}
   */
  public DetectorStreamProcessor createProcessor() {

    try {
      DetectorStreamProcessor processor = this.processorImplementation.newInstance();
      if (processor instanceof AbstractComponent) {
        ((AbstractComponent) processor).initialize();
      }
      return processor;
    } catch (InstantiationException e) {
      throw new InstantiationFailedException(this.processorImplementation);
    } catch (IllegalAccessException e) {
      throw new InstantiationFailedException(this.processorImplementation);
    }
  }

}
