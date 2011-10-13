/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.security.api;

/**
 * This is the interface for a content-object that represents a <em>user</em> of
 * the system.<br>
 * In order to interact with the system (the repository) you need to create a
 * <em>connection</em> as a specific {@link ContentUser user} supplying its
 * {@link #getTitle() name} as <em>login</em>. Typically this requires an
 * <em>authentication</em> of the connections initiator (e.g. by verifying a
 * password).<br>
 * For each {@link ContentAction operation} performed on that connection the
 * associated {@link ContentUser} needs to have sufficient {@link ContentRule
 * permissions} to do so.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentUser extends ContentGroup {

}
