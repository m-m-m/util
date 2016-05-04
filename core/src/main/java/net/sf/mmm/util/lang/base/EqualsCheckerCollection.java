/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Collection;
import java.util.Iterator;

import net.sf.mmm.util.lang.api.AbstractEqualsChecker;
import net.sf.mmm.util.lang.api.EqualsChecker;

/**
 * This is an implementation of {@link EqualsChecker} that recursively checks {@link Collection}s based on a
 * given {@link EqualsChecker} delegate. So if two objects should be {@link #isEqual(Object, Object) checked
 * for equality} that are both {@link Collection}s of the same kind, this implementation will check if they
 * have the same length and then recursively {@link #isEqual(Object, Object) check} the contained elements.
 * For objects of other types it will delegate to the {@link EqualsChecker} given at construction. This way
 * you can simply check if two {@link Collection}s have the
 * {@link net.sf.mmm.util.lang.api.EqualsCheckerIsSame same} or
 * {@link net.sf.mmm.util.lang.api.EqualsCheckerIsEqual equal} elements. <br>
 * In case you want to ignore the type of the collection (e.g. allow {@link java.util.List} and
 * {@link java.util.Set} or only different implementations of {@link java.util.List} with same elements to be
 * considered as equal) you can extend this class and override
 * {@link #isEqualCollectionType(Collection, Collection)}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class EqualsCheckerCollection extends AbstractEqualsChecker<Object> {

  private static final long serialVersionUID = 1L;

  /** The delegate to check regular objects. */
  private final EqualsChecker<Object> delegate;

  /**
   * The constructor.
   *
   * @param delegate is the {@link EqualsChecker} used to check objects other than {@link Collection}s.
   */
  public EqualsCheckerCollection(EqualsChecker<Object> delegate) {

    super();
    this.delegate = delegate;
  }

  @Override
  protected boolean isEqualNotNull(Object value1, Object value2) {

    if (value1 instanceof Collection) {
      if (!(value2 instanceof Collection)) {
        return false;
      }
      Collection<?> collection1 = (Collection<?>) value1;
      Collection<?> collection2 = (Collection<?>) value2;
      if (!isEqualCollectionType(collection1, collection2)) {
        return false;
      }
      int size = collection1.size();
      if (collection2.size() != size) {
        return false;
      }
      Iterator<?> iterator1 = collection1.iterator();
      Iterator<?> iterator2 = collection2.iterator();
      while (iterator1.hasNext()) {
        Object e1 = iterator1.next();
        Object e2 = iterator2.next();
        if (!isEqual(e1, e2)) {
          return false;
        }
      }
      assert (!iterator2.hasNext());
      return true;
    }
    return this.delegate.isEqual(value1, value2);
  }

  /**
   * This method is called from {@link #isEqualNotNull(Object, Object)} to check if the
   * {@link Object#getClass() type} of two given {@link Collection}s should be considered as equal.
   *
   * @param collection1 is the first {@link Collection}.
   * @param collection2 is the second {@link Collection}.
   * @return {@code true} if the {@link Object#getClass() type} of the given {@link Collection}s is
   *         considered as equal, {@code false} otherwise.
   */
  protected boolean isEqualCollectionType(Collection<?> collection1, Collection<?> collection2) {

    return collection1.getClass().equals(collection2.getClass());
  }
}
