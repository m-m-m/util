/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl.lucene;

import java.util.Iterator;

import net.sf.mmm.util.collection.base.AbstractIterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;

/**
 * This class iterates the {@link Fieldable#name() names} of all
 * {@link Fieldable fields} (properties) of a given {@link Document}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public final class LuceneFieldNameIterator extends AbstractIterator<String> {

  /** The enumeration to adapt. */
  private Iterator<Fieldable> enumeration;

  /**
   * The constructor.
   * 
   * @param luceneDocument is the lucene document whose field names should be
   *        iterated.
   */
  public LuceneFieldNameIterator(Document luceneDocument) {

    super();
    // luceneDocument.getFields();
    this.enumeration = luceneDocument.getFields().iterator();
    findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String findNext() {

    if (this.enumeration.hasNext()) {
      Fieldable field = this.enumeration.next();
      return field.name();
    }
    return null;
  }

}
