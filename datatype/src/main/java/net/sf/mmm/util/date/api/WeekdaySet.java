/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This {@link net.sf.mmm.util.lang.api.Datatype} represents a {@link #getSet() set} of {@link Weekday}s. It can
 * represent any unordered combination from none to all.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class WeekdaySet extends AbstractSimpleDatatype<Integer> {

  private static final long serialVersionUID = -2431073569164463800L;

  /**
   * An empty {@link WeekdaySet} {@link #getSet() containing} no {@link Weekday} at all.
   */
  public static final WeekdaySet NONE = new WeekdaySet(Integer.valueOf(0));

  /** An {@link WeekdaySet} {@link #getSet() containing} all {@link Weekday}s. */
  public static final WeekdaySet ALL = new WeekdaySet(Integer.valueOf(127));

  /**
   * An {@link WeekdaySet} {@link #getSet() containing} the working days (all but {@link Weekday#SUNDAY} and
   * {@link Weekday#SUNDAY}).
   */
  public static final WeekdaySet WORKDAYS = new WeekdaySet(Integer.valueOf(31));

  private transient Set<Weekday> set;

  /**
   * The constructor.
   *
   * @param bitmask - see {@link #getValue()}.
   */
  public WeekdaySet(Integer bitmask) {

    super(bitmask);
    ValueOutOfRangeException.checkRange(bitmask, Integer.valueOf(0), Integer.valueOf(127), WeekdaySet.class);
  }

  /**
   * The constructor.
   *
   * @param set is the {@link #getSet() set} representing the {@link WeekdaySet} to create.
   */
  public WeekdaySet(Set<Weekday> set) {

    super(Integer.valueOf(encode(set)));
    this.set = set;
  }

  /**
   * The constructor.
   *
   * @param weekdays are the {@link Weekday}s representing the {@link WeekdaySet} to create.
   */
  public WeekdaySet(Weekday... weekdays) {

    super(Integer.valueOf(encode(weekdays)));
  }

  /**
   * This method encodes the given {@code set} to a bit-mask for {@link #getValue()}.
   *
   * @param weekdays are the {@link Weekday}s.
   * @return the encoded {@link #getValue() value}.
   */
  private static int encode(Weekday... weekdays) {

    int bitmask = 0;
    for (Weekday weekday : weekdays) {
      bitmask = bitmask | (1 << weekday.ordinal());
    }
    return bitmask;
  }

  /**
   * This method encodes the given {@code set} to a bit-mask for {@link #getValue()}.
   *
   * @param set is the {@link Set} of {@link Weekday}s.
   * @return the encoded {@link #getValue() value}.
   */
  private static int encode(Set<Weekday> set) {

    int bitmask = 0;
    for (Weekday weekday : set) {
      bitmask = bitmask | (1 << weekday.ordinal());
    }
    return bitmask;
  }

  /**
   * This method returns the {@link WeekdaySet} as compressed mask of seven bits.
   *
   * {@inheritDoc}
   */
  @Override
  public Integer getValue() {

    return super.getValue();
  }

  /**
   * This method determines if this {@link WeekdaySet} contains the given {@link Weekday}. This is equivalent to:
   *
   * <pre>
   * {@link #getSet()}.{@link Set#contains(Object) contains}(weekday)
   * </pre>
   *
   * @param weekday is the {@link Weekday} to check.
   * @return {@code true} if the given {@code weekday} is contained in this {@link #getSet() set}, {@code false}
   *         otherwise.
   */
  public boolean contains(Weekday weekday) {

    return getSet().contains(weekday);
  }

  /**
   * This method gets an {@link Collections#unmodifiableSet(Set) unmodifiable} {@link Set} representing this
   * {@link WeekdaySet}.
   *
   * @return the {@link Set} with all {@link Weekday}s.
   */
  public Set<Weekday> getSet() {

    if (this.set == null) {
      HashSet<Weekday> hashSet = new HashSet<Weekday>();
      int value = getValue().intValue();
      for (Weekday weekday : Weekday.values()) {
        int mask = (1 << weekday.ordinal());
        if ((value & mask) != 0) {
          hashSet.add(weekday);
        }
      }
      this.set = Collections.unmodifiableSet(hashSet);
    }
    return this.set;
  }

  /**
   * This method gets the next {@link Weekday} {@link #contains(Weekday) contained} in this {@link WeekdaySet} starting
   * with the given {@code weekday}. If the given {@code weekday} is {@link #contains(Weekday) contained}, it will be
   * returned. Otherwise the {@link Weekday#getNext() next} successor is determined.
   *
   * @param weekday is the {@link Weekday} to start with.
   * @return the next {@link Weekday} starting with the given {@code weekday} or {@code null} if this {@link WeekdaySet}
   *         is {@link Set#isEmpty() empty}.
   */
  public Weekday getNextMatch(Weekday weekday) {

    if (contains(weekday)) {
      return weekday;
    } else if (getSet().isEmpty()) {
      return null;
    } else {
      Weekday result = weekday.getNext();
      while (!contains(result)) {
        result = weekday.getNext();
        if (result == weekday) {
          return null;
        }
      }
      return result;
    }
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder("{");
    getSet();
    for (Weekday weekday : Weekday.values()) {
      if (this.set.contains(weekday)) {
        if (buffer.length() > 1) {
          buffer.append(",");
        }
        buffer.append(weekday.toString());
      }
    }
    buffer.append('}');
    return buffer.toString();
  }

  /**
   * This method returns a new {@link WeekdaySet} that is the inverse selection of this {@link WeekdaySet}. Every
   * {@link Weekday} {@link #contains(Weekday) contained} in this {@link WeekdaySet} is NOT {@link #contains(Weekday)
   * contained} in the returned result and vice versa.
   *
   * @return the inverse {@link WeekdaySet}.
   */
  public WeekdaySet invert() {

    return new WeekdaySet(Integer.valueOf(getValue().intValue() ^ 127));
  }

}
