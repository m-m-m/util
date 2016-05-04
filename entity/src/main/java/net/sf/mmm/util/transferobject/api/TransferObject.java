/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

import net.sf.mmm.util.entity.api.Entity;

/**
 * This is the interface for a transfer-object. Such object is a {@link java.io.Serializable serializable}
 * {@link net.sf.mmm.util.pojo.api.Pojo} used to transfer data between application layers (e.g. from <em>logic</em> to
 * <em>client</em> layer). For more information see the
 * <a href="http://www.corej2eepatterns.com/Patterns2ndEd/TransferObject.htm">Transfer Object Pattern</a>.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface TransferObject extends Entity {

  // nothing to add, just a marker interface.

}
