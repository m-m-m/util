/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Collection;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.nls.api.NlsResourceBundle;

/**
 * This interface allows to {@link #getBundles() get} all registered bundles.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@ComponentSpecification
public interface NlsResourceBundleProvider {

  /**
   * This method gets all {@link net.sf.mmm.util.nls.api.NlsTemplateResolver#CLASSPATH_NLS_BUNDLE central
   * registered} {@link NlsResourceBundle}s.
   * 
   * @return a collection with the central {@link NlsResourceBundle}s.
   */
  Collection<NlsResourceBundle> getBundles();

}
