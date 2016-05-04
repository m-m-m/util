/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.api;

/**
 * This is the abstract base class for a composite {@link TransferObject}. Such object should contain (aggregate) other
 * {@link AbstractTransferObject}s but no atomic data. This means it has properties that contain a
 * {@link TransferObject} or a {@link java.util.Collection} of those but no {@link net.sf.mmm.util.lang.api.Datatype
 * values}. <br>
 * Classes extending this class should carry the suffix {@code Cto}. <br>
 * For additional details and an example consult the {@link net.sf.mmm.util.transferobject.api package JavaDoc}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public abstract class CompositeTo extends AbstractTransferObject {

  private static final long serialVersionUID = -5632071222008562903L;

  /**
   * The constructor.
   */
  public CompositeTo() {

    super();
  }

}
