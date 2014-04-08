/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation for a {@link net.sf.mmm.service.api.RemoteInvocationCall} that provides the login
 * operation. Such invocation will treated specially:
 * <ul>
 * <li>It is the has to be called before any other secured remote invocation can be called.</li>
 * <li>It can NOT be grouped with other remote invocations in a
 * {@link net.sf.mmm.service.api.client.RemoteInvocationQueue queue}.</li>
 * <li>After it has been processed successfully on the server a {@link CsrfToken} is generated and associated
 * with the logged in user respectively its HTTP session.</li>
 * <li>All subsequent calls send from the client must contain the same {@link CsrfToken} or they will be
 * reject.</li>
 * </ul>
 * All these security aspects are already handled by the implementation of this API. If you are using a valid
 * implementation you can focus on specifying, implementing, and using remote services.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Login {

  // nothing to add, just a marker...

}
