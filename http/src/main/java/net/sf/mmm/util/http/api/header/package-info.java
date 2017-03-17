/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for dealing with HTTP headers. <a name="documentation"></a>
 * <h2>Util HTTP API Header</h2> This package contains the API to deal with HTTP Headers. The most important interfaces
 * are {@link net.sf.mmm.util.http.api.header.HttpHeader} for a single (but potentially repeated/mutli-valued) header
 * and {@link net.sf.mmm.util.http.api.header.HttpHeaders} for an entire set of headers. For both of these interfaces
 * there is an abstract class ({@link net.sf.mmm.util.http.api.header.AbstractHttpHeader} and
 * {@link net.sf.mmm.util.http.api.header.AbstractHttpHeaders}) that also act as factory. Further, for all common
 * headers there is a specific implementation with high-level support to retrieve specific information or to assemble it
 * from such information. As a prominent example have a look at
 * {@link net.sf.mmm.util.http.api.header.HttpHeaderUserAgent} with
 * {@link net.sf.mmm.util.http.api.header.HttpHeaderUserAgent#getBrowser() getBrowser()},
 * {@link net.sf.mmm.util.http.api.header.HttpHeaderUserAgent#getBrowserVersion() getBrowserVersion()},
 * {@link net.sf.mmm.util.http.api.header.HttpHeaderUserAgent#getOs() getOs()}, etc. Also
 * {@link net.sf.mmm.util.http.api.header.HttpHeaderContentDisposition} gives advanced access such as
 * {@link net.sf.mmm.util.http.api.header.HttpHeaderContentDisposition#getFilename() getFilename()}. This is one of the
 * key value factors of this library above others. There features can also be used additionally with other libraries to
 * parse and process header values easier. For other protocols that extend HTTP you can find headers in additional
 * packages such as {@link net.sf.mmm.util.http.api.ssdp.header}.
 */
package net.sf.mmm.util.http.api.header;
