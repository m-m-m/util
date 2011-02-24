/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.util.List;
import java.util.ResourceBundle;

import net.sf.mmm.util.component.base.ComponentSpecification;

/**
 * This is the interface for an internal component that automatically
 * {@link #findBundles() finds} the declared {@link ResourceBundle bundles}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
@ComponentSpecification
public interface NlsResourceBundleLocator {

  /**
   * This method automatically finds and creates instances of
   * {@link ResourceBundle} for {@link java.util.Locale#ROOT} via
   * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver#CLASSPATH_NLS_BUNDLE}.
   * 
   * @return the {@link List} of {@link ResourceBundle}s.
   */
  List<ResourceBundle> findBundles();

}
