/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for utilities that help to deal with exceptions. <a name="documentation"></a>
 * <h2>Util Exception API</h2> This package contains the {@link net.sf.mmm.util.exception.api.NlsThrowable advanced
 * exceptions} with support for NLS (I18N/L10N), UUIDs, error codes, etc. - especially
 * {@link net.sf.mmm.util.exception.api.NlsRuntimeException}. Further it contains various exceptions for common errors
 * that can be reused. Finally, it contains the API of utilities that help to deal with {@link java.lang.Throwable
 * exceptions} especially {@link net.sf.mmm.util.exception.api.ExceptionUtil}.
 * <h3>Custom Exception Example:</h3>
 * 
 * <pre>
 * public class InvalidCustomerNumberException extends {@link net.sf.mmm.util.exception.api.NlsRuntimeException} {
 *   public InvalidCustomerNumberException(String customerNumber) {
 *     super({@link net.sf.mmm.util.exception.api.NlsRuntimeException#createBundle(Class) createBundle}(My{@link net.sf.mmm.util.nls.api.NlsBundle}Root.class).errorInvalidCustomerNumber(customerNumber);
 *   }
 *
 *   public {@link net.sf.mmm.util.exception.api.NlsRuntimeException#getCode() getCode()} { // optional method
 *     return "CUST0001"; // maybe use a constant
 *   }
 * }
 * </pre>
 * 
 * For native language support (NLS) you create an {@link net.sf.mmm.util.nls.api.NlsBundle} typically per module:
 * 
 * <pre>
 * public interface MyNlsBundleRoot extends {@link net.sf.mmm.util.nls.api.NlsBundle} {
 *
 *   {@literal @}{@link net.sf.mmm.util.nls.api.NlsBundleMessage}("The given customer number \"{id}\" is invalid!")
 *   NlsMessage errorInvalidCustomerNumber(@Named("id") String id);
 * }
 * </pre>
 * 
 * For further details about the NLS and I18N please read the {@link net.sf.mmm.util.nls.api NLS API documentation}.
 */
package net.sf.mmm.util.exception.api;
