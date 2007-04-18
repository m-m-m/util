package net.sf.mmm.search.impl;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * This class iterates the {@link Field#name() names} of all
 * {@link Field fields} (properties) of a given {@link Document}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public final class LuceneFieldNameIterator implements Iterator<String> {

  private Enumeration/* <Field> */enumeration;

  private String next;

  /**
   * The constructor. 
   * 
   * @param luceneDocument
   *        is the lucene document whos field names should be iterated.
   */
  public LuceneFieldNameIterator(Document luceneDocument) {

    super();
    // luceneDocument.getFields();
    this.enumeration = luceneDocument.fields();
    stepNext();
  }

  private void stepNext() {

    if (this.enumeration.hasMoreElements()) {
      Field field = (Field) this.enumeration.nextElement();
      this.next = field.name();
    } else {
      this.next = null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * {@inheritDoc}
   */
  public String next() {

    if (this.next != null) {
      String value = this.next;
      stepNext();
      return value;
    } else {
      throw new NoSuchElementException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
