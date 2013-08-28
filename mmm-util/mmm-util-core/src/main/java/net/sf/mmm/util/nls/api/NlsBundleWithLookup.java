/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Map;

/**
 * This is the interface for {@link NlsBundle}s that require {@link #getMessage(String, Map) generic lookup}
 * of a {@link NlsMessage}.<br/>
 * <b>Note:</b><br/>
 * Please only use this interface when the {@link #getMessage(String, Map)} is really required. Otherwise only
 * extend {@link NlsBundle}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract interface NlsBundleWithLookup extends NlsBundle {

  /**
   * This method gets the {@link NlsMessage} with the un-interpolated message for the given
   * <code>methodName</code>. This allows generic access invoke a specific method of your bundle. E.g. assume
   * you have an interface <code>NlsBundleMyExampleRoot</code> extending {@link NlsBundleWithLookup} that
   * declares the following method:
   * 
   * <pre>
   *   @NlsBundleMessage("The object \"{object}\" is null!")
   *   NlsMessage errorArgumentNull(@Named("object") Object object);
   * </pre>
   * 
   * Then you could do:
   * 
   * <pre>
   * NlsBundleMyExampleRoot myBundle = {@link NlsAccess}.getBundleFactory().createBundle(NlsBundleMyExampleRoot.class);
   * Map<String, Object> parameters = new HashMap<>();
   * parameters.put("object", myObject);
   * {@link NlsMessage} message = myBundle.getMessage("errorArgumentNull", parameters);
   * </pre>
   * 
   * This would have the same result as <code>myBundle.errorArgumentNull(myObject)</code>.
   * 
   * @param methodName is the {@link java.lang.reflect.Method#getName() method name}.
   * @param nlsArguments are the {@link NlsMessage#getArgument(String) arguments}. May be <code>null</code>
   *        for no arguments.
   * @return the {@link NlsMessage} or <code>null</code> if no message exists for the given
   *         <code>methodName</code> (no such method exists).
   */
  // ATTENTION: parameter names must NOT be changed!
  NlsMessage getMessage(String methodName, Map<String, Object> nlsArguments);

}
