/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.io.api.DetectorInputStream;
import net.sf.mmm.util.io.api.DetectorOutputStream;
import net.sf.mmm.util.io.api.DetectorStreamProvider;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessorFactory;

/**
 * This is the abstract base implementation of the
 * {@link DetectorStreamProvider}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDetectorStreamProvider extends AbstractLoggable implements
    DetectorStreamProvider {

  /** @see #getProcessorFactoryList() */
  private List<DetectorStreamProcessorFactory> processorFactoryList;

  /**
   * The constructor.
   */
  public AbstractDetectorStreamProvider() {

    super();
  }

  /**
   * This method gets the {@link List} of
   * {@link #addProcessorFactory(DetectorStreamProcessorFactory) registered}
   * {@link DetectorStreamProcessorFactory}-instances.
   * 
   * @return the processorFactoryList
   */
  public List<DetectorStreamProcessorFactory> getProcessorFactoryList() {

    if (this.processorFactoryList == null) {
      this.processorFactoryList = new ArrayList<DetectorStreamProcessorFactory>();
    }
    return this.processorFactoryList;
  }

  /**
   * This method registers a {@link DetectorStreamProcessorFactory} to this
   * {@link DetectorStreamProvider}.
   * 
   * @see #setProcessorFactoryList(List)
   * 
   * @param processorFactory
   */
  public void addProcessorFactory(DetectorStreamProcessorFactory processorFactory) {

    getProcessorFactoryList().add(processorFactory);
  }

  /**
   * This method sets the complete {@link List} of
   * {@link DetectorStreamProcessorFactory}-instances. Do NOT call this method
   * after {@link #addProcessorFactory(DetectorStreamProcessorFactory)} has been
   * called.
   * 
   * @param processorFactoryList is the complete {@link List} of
   *        {@link DetectorStreamProcessorFactory}-instances to set.
   */
  public void setProcessorFactoryList(List<DetectorStreamProcessorFactory> processorFactoryList) {

    getInitializationState().requireNotInitilized();
    if (this.processorFactoryList != null) {
      throw new AlreadyInitializedException();
    }
    this.processorFactoryList = processorFactoryList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (getProcessorFactoryList().size() == 0) {
      throw new ResourceMissingException("processorFactoryList");
    }
  }

  /**
   * {@inheritDoc}
   */
  public DetectorInputStream wrapInputStream(InputStream stream) {

    return wrapInputStream(stream, new HashMap<String, Object>());
  }

  /**
   * {@inheritDoc}
   */
  public DetectorOutputStream wrapOutputStream(OutputStream stream) {

    return wrapOutputStream(stream, new HashMap<String, Object>());
  }

}
