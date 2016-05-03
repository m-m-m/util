/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.contenttype.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.contenttype.api.ContentType;
import net.sf.mmm.util.contenttype.api.ContentTypeManager;
import net.sf.mmm.util.exception.api.DuplicateObjectException;

/**
 * This is the abstract base implementation of the {@link ContentTypeManager} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentTypeManager extends AbstractLoggableComponent implements ContentTypeManager {

  /** @see #getContentType(String) */
  private final Map<String, ContentType> id2contentTypeMap;

  /**
   * The constructor.
   */
  public AbstractContentTypeManager() {

    super();
    this.id2contentTypeMap = new HashMap<String, ContentType>();
  }

  @Override
  public ContentType getContentType(String id) {

    return this.id2contentTypeMap.get(id);
  }

  /**
   * This method registers the given {@code contentType} in this {@link ContentTypeManager}.
   *
   * @see #getContentType(String)
   *
   * @param contentType is the {@link ContentType} to add.
   * @throws DuplicateObjectException if a {@link ContentType} with the same {@link ContentType#getId() ID}
   *         has already been registered.
   */
  protected void addContentType(ContentType contentType) throws DuplicateObjectException {

    String id = contentType.getId();
    if (this.id2contentTypeMap.containsKey(id)) {
      throw new DuplicateObjectException(contentType, id);
    }
    this.id2contentTypeMap.put(id, contentType);
  }

}
