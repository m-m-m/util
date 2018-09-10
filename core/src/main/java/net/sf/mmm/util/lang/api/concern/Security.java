/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api.concern;

/**
 * This interface is used to document the concern <em>security</em> and track special support that we offer for this
 * purpose. In order not to reinvent the wheel, we do NOT create yet another security framework. Instead we suggest to
 * use <a href="http://projects.spring.io/spring-security/">spring-security</a> and support integration with it.
 * However, there are some missing links that we aim to close with our project. Have a look at the known subinterfaces
 * and implementing classes to get an overview.
 *
 * @deprecated over-engineered. Will be deleted in a future release.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Deprecated
public interface Security {

  // nothing to add...

}
