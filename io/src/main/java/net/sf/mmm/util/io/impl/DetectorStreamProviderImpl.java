/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import net.sf.mmm.util.io.api.DetectorInputStream;
import net.sf.mmm.util.io.api.DetectorOutputStream;
import net.sf.mmm.util.io.base.AbstractDetectorStreamProvider;

/**
 * This is the implementation of the {@link net.sf.mmm.util.io.api.DetectorStreamProvider} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class DetectorStreamProviderImpl extends AbstractDetectorStreamProvider {

  /**
   * The constructor.
   */
  public DetectorStreamProviderImpl() {

    super();
  }

  @Override
  public DetectorInputStream wrapInputStream(InputStream stream, Map<String, Object> metadata) {

    return new ProcessableDetectorInputStream(stream, metadata, this);
  }

  @Override
  public DetectorOutputStream wrapOutputStream(OutputStream stream, Map<String, Object> metadata) {

    return new ProcessableDetectorOutputStream(stream, metadata, this);
  }

}
