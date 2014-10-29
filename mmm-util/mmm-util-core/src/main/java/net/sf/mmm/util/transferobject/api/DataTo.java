/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

/**
 * This is the abstract base class for an {@link TransferObject} that only contains data without relations.
 * This is called <em>DTO</em> (data transfer object). Here data means properties that typically represent a
 * {@link net.sf.mmm.util.lang.api.Datatype} and potentially for relations the ID (typically as {@link Long}).
 * For actual relations you will use {@link CompositeTo CTO}s to express what set of entities to transfer,
 * load, save, update, etc. without redundancies. It typically corresponds to an
 * {@link net.sf.mmm.util.entity.api.GenericEntity entity}. In such case consider {@link EntityTo}. Otherwise
 * if you derive from this class rather than {@link EntityTo} use the suffix <code>Dto</code>. <br>
 * For additional details and an example consult the {@link net.sf.mmm.util.transferobject.api package
 * JavaDoc}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class DataTo extends AbstractTransferObject {

  /** UID for serialization. */
  private static final long serialVersionUID = -3039958170310580721L;

  /**
   * The constructor.
   */
  public DataTo() {

    super();
  }

}
