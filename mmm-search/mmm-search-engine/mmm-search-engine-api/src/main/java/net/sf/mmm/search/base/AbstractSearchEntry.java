/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.util.component.base.AbstractLoggableObject;

/**
 * This is the abstract base implementation of the {@link SearchEntry}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchEntry extends AbstractLoggableObject implements SearchEntry {

  /**
   * The constructor.
   */
  public AbstractSearchEntry() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return getFieldAsString(FIELD_TITLE);
  }

  /**
   * {@inheritDoc}
   */
  public String getUri() {

    return getFieldAsString(FIELD_URI);
  }

  /**
   * {@inheritDoc}
   */
  public Long getId() {

    return getField(FIELD_ID, Long.class);
  }

  /**
   * {@inheritDoc}
   */
  public Object getCustomId() {

    return getField(FIELD_CUSTOM_ID);
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    String text = getFieldAsString(FIELD_TEXT);
    if (text == null) {
      // lucene will nuke empty texts...
      text = "";
    }
    return text;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return getFieldAsString(FIELD_TYPE);
  }

  /**
   * {@inheritDoc}
   */
  public String getCreator() {

    return getFieldAsString(FIELD_CREATOR);
  }

  /**
   * {@inheritDoc}
   */
  public String getSource() {

    return getFieldAsString(FIELD_SOURCE);
  }

  /**
   * {@inheritDoc}
   */
  public Long getSize() {

    return getField(FIELD_SIZE, Long.class);
  }

  /**
   * {@inheritDoc}
   */
  public String getFieldAsString(String name) {

    return getField(name, String.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    Object cid = getCustomId();
    if (cid == null) {
      Object id = getId();
      if (id == null) {
        return 0;
      } else {
        return id.hashCode();
      }
    } else {
      return cid.hashCode();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object object) {

    if (object == null) {
      return false;
    }
    if (object == this) {
      return true;
    }
    if (getClass() == object.getClass()) {
      // CID has higher priority as it will also work for transient entries
      Object myCid = getCustomId();
      if (myCid == null) {
        Object myId = getId();
        if (myId != null) {
          Object otherId = ((SearchEntry) object).getId();
          if (myId.equals(otherId)) {
            return true;
          }
        }
      } else {
        Object otherCid = ((SearchEntry) object).getCustomId();
        if (myCid.equals(otherCid)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    String uri = getUri();
    if (uri != null) {
      sb.append(uri);
      String title = getTitle();
      if (title != null) {
        sb.append(title);
      }
    }
    Object cid = getCustomId();
    if (cid != null) {
      sb.append('[');
      sb.append(cid);
      sb.append(']');
    }
    if (sb.length() == 0) {
      sb.append('-');
    }
    return sb.toString();
  }

}
