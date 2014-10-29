/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for {@link #resolveTemplate(String) resolving} {@link NlsTemplate} for translating a
 * text-message to a {@link java.util.Locale}-specific language. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ComponentSpecification
public interface NlsTemplateResolver {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.nls.api.NlsTemplateResolver";

  /**
   * The classpath of a textual file containing the full qualified name(s) of one or multiple
   * {@link net.sf.mmm.util.nls.base.AbstractResourceBundle NLS-bundle}(s). <br>
   * There can be arbitrary implementations of {@link NlsTemplateResolver}. However the default implementation
   * will use the {@link Thread#getContextClassLoader() context-class-loader} to for this lookup.
   */
  String CLASSPATH_NLS_BUNDLE = "META-INF/net.sf.mmm/nls-bundles";

  /**
   * This method is used to create the {@link NlsTemplate} from information that is found by the given
   * <code>internationalizedMessage</code>. <br>
   * This may happen via a reverse lookup from {@link net.sf.mmm.util.nls.base.AbstractResourceBundle}(s).
   * 
   * @param internationalizedMessage is the {@link NlsMessage#getInternationalizedMessage() internationalized
   *        message}.
   * @return the according {@link NlsTemplate} or <code>null</code> if this implementation failed to resolve
   *         the according template.
   */
  NlsTemplate resolveTemplate(String internationalizedMessage);

}
