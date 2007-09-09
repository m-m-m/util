/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.persistence.api;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.value.api.ContentId;
import net.sf.mmm.value.api.ValueParseException;

/**
 * This is the interface for a service that generates unique
 * {@link ContentObject#getId() identifiers} for
 * {@link ContentObject content-objects}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface IdService {

  /**
   * This method gets the {@link ContentId ID} of the root-folder.
   * 
   * @return the ID of the root-folder.
   */
  ContentId getRootFolderId();

  /**
   * This method gets the {@link ContentId ID} of the folder containing the content
   * (the real resources).
   * 
   * @return the ID of the content-folder.
   */
  ContentId getContentFolderId();

  /**
   * This method gets the {@link ContentId ID} of the
   * {@link net.sf.mmm.content.model.api.ContentModelService#getRootContentClass() root-class}.
   * 
   * @return the ID of the root-class.
   */
  ContentId getRootClassId();

  /**
   * This method crates an {@link ContentId ID} given in its string representation. The
   * method can be used if an ID is given from outside the system (e.g. via an
   * URI).<br>
   * The same thing can be done via the
   * {@link net.sf.mmm.value.api.ValueManager} of {@link ContentId IDs}. It can be
   * retrieved via {@link net.sf.mmm.value.api.ValueService#getManager(Class)}
   * if the if implementation of {@link ContentId ID} is known.
   * 
   * @param id is the ID as string.
   * @return the corresponding ID object.
   * @throws ValueParseException if the given string is illegal and could not be
   *         parsed as ID.
   */
  ContentId toId(String id) throws ValueParseException;

  /**
   * This method creates a new unique identifier.
   * 
   * @param type is the content class reflecting the type of the new resource
   *        that will get the new id.
   * @return the new unique identifier.
   */
  ContentId createId(ContentClass type);

  /**
   * This method releases a unique identifier that is unused. Identifiers are
   * released e.g. if a transaction failed so instead of letting the garbage
   * collector eat them they can be recycled. TOOD: whats gone happen with this?
   * 
   * @param unusedId is an identifier that is not used by any resource.
   */
  void releaseId(ContentId unusedId);

  /**
   * This method gets the ID of the {@link ContentClass content-class}
   * reflecting the content object owning the given id.
   * 
   * @param id is the ID of a content object whose type is requested. The given
   *        id must be produced from the the current id store.
   * @return the id of the reflecting class.
   */
  ContentId getContentClassId(ContentId id);

}
