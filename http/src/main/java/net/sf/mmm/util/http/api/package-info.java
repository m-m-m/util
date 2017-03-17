/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for dealing with the <em>HyperText Transfer Protocol</em> (HTTP). <a name="documentation"></a>
 * <h2>Util HTTP API</h2> This package contains the API to deal with HTTP easier. The most prominent interfaces are
 * {@link net.sf.mmm.util.http.api.HttpRequest} and {@link net.sf.mmm.util.http.api.HttpResponse}.
 *
 * <pre>
 * {@link net.sf.mmm.util.http.api.HttpRequestBuilder} builder = new {@link net.sf.mmm.util.http.api.HttpRequestBuilder}();
 * {@link net.sf.mmm.util.http.api.HttpRequest} request = builder.{@link net.sf.mmm.util.http.api.HttpRequestBuilder#doGet(String)
 * doGet}("/data/4711").{@link net.sf.mmm.util.http.api.HttpRequestBuilder#headerHost(String)
 * headerHost}("www.foo.bar").{@link net.sf.mmm.util.http.api.HttpRequestBuilder#build()
 * build}();
 * </pre>
 *
 * <h3>Yet another HTTP library?</h3> For HTTP there are already various existing libraries in Java. Each of them has
 * its focus and strengths.
 * <ul>
 * <li>JDK - with Java9 there is full
 * <a href="http://download.java.net/java/jdk9/docs/api/java/net/http/package-summary.html">HTTP 2.0 support</a> already
 * included. For general purpose use this to build HTTP clients, etc.</li>
 * <li><a href="http://netty.io/">Netty</a> - for high performance and throughput you should consider this
 * implementation.</li>
 * <li>Apache http components - an alternativ to the JDK solution. Consider especially for support Java 8 or lower.</li>
 * <li>Servers or servlet containers like undertow, jetty, tomcat, vert.x, etc. - avoid building your own server. There
 * are ready-to-use solutions available that are most probably better than anything you can create on your own. These
 * products will use existing HTTP libraries as the ones listed above under the hood. You can also embed such solutions
 * e.g. via spring boot.</li>
 * <li>Service frameworks like CXF, dropwizard or RestEasy - also to implement services (REST, SOAP, etc.) as server or
 * client you shall not reinvent the wheel and use an existing and established product.</li>
 * </ul>
 * As you can see in most cases you will not need yet another HTTP library. However, this library offers some unique
 * features that could still be of high value and interest:
 * <ul>
 * <li>Advanced and high-level header support - most frameworks represent HTTP headers as {@code Map<String, String>} or
 * {@code Map<String, List<String>>}. If you want to determine the Browser from an
 * {@link net.sf.mmm.util.http.api.HttpRequest} or the filename of an uploaded or downloaded file you need to fiddle
 * with string parsing and in most cases you will do things wrong as the standards are historically grown and complex.
 * This library allows specific implementations for particular headers that give you a clean API to access particular
 * information without any string fiddling. See {@link net.sf.mmm.util.http.api.header HTTP API header documentation}
 * for further details.</li>
 * <li>Flexibility and support for specific edge-cases - e.g. for SSDP you need to do HTTP over UDP multicast requests.
 * None of the above listed libraries give support for such things. This library does. For SSDP there is also a
 * dedicated {@link net.sf.mmm.util.http.api.ssdp API and implementation}.</li>
 * </ul>
 */
package net.sf.mmm.util.http.api;
