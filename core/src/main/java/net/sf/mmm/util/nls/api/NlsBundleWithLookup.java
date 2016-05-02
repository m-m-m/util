/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Map;

/**
 * This is the interface for {@link NlsBundle}s that require {@link #getMessage(String, Map) generic lookup} of a
 * {@link NlsMessage}. <br>
 * <b>Note:</b><br>
 * Please only use this interface when the {@link #getMessage(String, Map)} is really required. Otherwise only extend
 * {@link NlsBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract interface NlsBundleWithLookup extends NlsBundle, NlsMessageLookup {

  /**
   * {@inheritDoc}
   * 
   * This allows generic access to invoke a specific method of your bundle. E.g. assume you have an interface
   * {@code NlsBundleMyExampleRoot} extending {@link NlsMessageLookup} that declares the following method:
   * 
   * <pre>
   * &#064;NlsBundleMessage(&quot;The object \&quot;{object}\&quot; is null!&quot;)
   * NlsMessage errorArgumentNull(@Named(&quot;object&quot;) Object object);
   * </pre>
   * 
   * Then you could do:
   * 
   * <pre>
   * NlsBundleMyExampleRoot myBundle = {@link NlsAccess}.getBundleFactory().createBundle(NlsBundleMyExampleRoot.class);
   * Map&lt;String, Object&gt; parameters = new HashMap&lt;&gt;();
   * parameters.put("object", myObject);
   * {@link NlsMessage} message = myBundle.getMessage("errorArgumentNull", parameters);
   * </pre>
   * 
   * This would have the same result as {@code myBundle.errorArgumentNull(myObject)}.
   */
  // ATTENTION: parameter names must NOT be changed!
  NlsMessage getMessage(String methodName, Map<String, Object> nlsArguments);

}
