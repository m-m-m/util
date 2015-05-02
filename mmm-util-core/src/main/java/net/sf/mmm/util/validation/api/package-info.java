/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for simple validation of values.
 * <a name="documentation"></a><h2>Validator API</h2>
 * Validation is a fundamental feature of an application. This package provides a simple but powerful API for
 * validation. While Java defines a very sophisticated API provided by <code>javax.validation</code> it is 
 * overloaded for simpler use-cases or a limited web-application based on GWT.
 * However, this API here can still act as a thin facade to such validation framework. <br>
 * <h3>Validation and GWT (Google Web Toolkit)</h3>
 * Techniques like GWT require specific limitations for your Java code in oder to be executable as JavaScript on the
 * client. These limitations shall not limit your validation on the server-side but still allow to reuse logic on the
 * client. I am aware that <a href="http://groups.google.com/group/gwt-validation">gwt-validation</a> brings 
 * <code>javax.validation</code> to the client. However, the price of this complexity is reduced turn-around times
 * during development and therefore limit the progress of your project. 
 */
package net.sf.mmm.util.validation.api;

