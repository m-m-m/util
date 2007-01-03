/* $Id$ */
package net.sf.mmm.search.impl;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import net.sf.mmm.search.base.AbstractSearchEntry;

/**
 * This is the implementation of the {@link net.sf.mmm.search.api.SearchEntry}
 * interface using lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchEntry extends AbstractSearchEntry {

  /** the lucene document that represents the actual entry */
  private final Document document;

  /**
   * The constructor
   * 
   * @param entry
   *        is the lucene document that represents the actual entry.
   */
  public LuceneSearchEntry(Document entry) {

    super();
    this.document = entry;
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getProperty(java.lang.String)
   */
  public String getProperty(String name) {

    return this.document.get(name);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getPropertyNames()
   */
  public Iterator<String> getPropertyNames() {

    // this.document.getFields();
    return new EnumerationIterator(this.document.fields());
  }

  /**
   * This inner class iterates the names of all fields supplied by an adapted
   * enumeration.
   */
  @SuppressWarnings("all")
  private static final class EnumerationIterator implements Iterator<String> {

    private Enumeration/* <Field> */enumeration;

    private String next;

    /**
     * The constructor
     */
    public EnumerationIterator(Enumeration stringEnumeration) {

      super();
      this.enumeration = stringEnumeration;
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
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {

      return (this.next != null);
    }

    /**
     * @see java.util.Iterator#next()
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
     * @see java.util.Iterator#remove()
     */
    public void remove() {

      throw new UnsupportedOperationException();
    }

  }

}
