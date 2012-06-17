/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.link;

import net.sf.mmm.data.api.link.LinkManager;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of the {@link LinkManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractLinkManager extends AbstractLoggableComponent implements LinkManager {

  /**
   * The constructor.
   */
  public AbstractLinkManager() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getInverseClassifier(String classifier) {

    String result = getInverseClassifierStrict(classifier);
    if (result == null) {
      result = classifier;
    }
    return result;
  }

}
