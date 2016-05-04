/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is like a {@link Map} that maps elements ({@literal <E>}) to an {@code int} value that represents the
 * {@link #getRank(Object) rank} of the according element. <br>
 * This is useful for heuristic decisions where specific detections indicate a {@link #addRank(Object, int) gain or
 * loss} of a specific element (representing a decision). Additionally an element can be {@link #setUnacceptable(Object)
 * declared} {@link #RANK_UNACCEPTABLE unacceptable} so it is out of the decision.
 *
 * @param <E> is the type of the elements (decisions) to rank.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class RankMap<E> {

  /**
   * The rank that represents an element that has been disqualified. If an element has this rank it can NOT change it
   * again and calls to {@link #addRank(Object, int)} will have no effect.
   *
   * @see #setUnacceptable(Object)
   */
  public static final int RANK_UNACCEPTABLE = Integer.MIN_VALUE;

  private final Map<E, Ranking> map;

  private final Collection<E> elements;

  /**
   * The constructor.
   */
  public RankMap() {

    super();
    this.map = new HashMap<>();
    this.elements = Collections.unmodifiableCollection(this.map.keySet());
  }

  /**
   * This method gets all elements in this {@link RankMap} that have been actively ranked (by
   * {@link #addRank(Object, int)} or {@link #setUnacceptable(Object)}).
   *
   * @return a {@link Collection} with the ranked elements.
   */
  public Collection<E> getElements() {

    return this.elements;
  }

  /**
   * This method gets the current rank for the given {@code element}. The rank is in the range from
   * {@link #RANK_UNACCEPTABLE -1} to {@link Integer#MAX_VALUE}. If the {@code element} has no rank, a value of
   * {@code 0} will be returned.
   *
   * @see #RANK_UNACCEPTABLE
   * @see #addRank(Object, int)
   *
   * @param element is the element for which the rank is requested.
   * @return the current rank of the given {@code element}.
   */
  public int getRank(E element) {

    Ranking ranking = this.map.get(element);
    if (ranking == null) {
      return 0;
    } else {
      return ranking.rank;
    }
  }

  /**
   * This method ranks the given {@code element} as {@link #RANK_UNACCEPTABLE unacceptable}. After the call of this
   * method the {@link #getRank(Object) rank} of the given {@code element} is set to {@link #RANK_UNACCEPTABLE} and can
   * NOT be changed again.
   *
   * @param element is the unacceptable element.
   */
  public void setUnacceptable(E element) {

    Ranking ranking = this.map.get(element);
    if (ranking == null) {
      ranking = new Ranking();
      this.map.put(element, ranking);
    }
    ranking.setUnacceptable();
  }

  /**
   * This method determines if the given {@code element} has been {@link #setUnacceptable(Object) declared}
   * {@link #RANK_UNACCEPTABLE unacceptable}.
   *
   * @param element is the element to check.
   * @return {@code true} if the given {@code element} is unacceptable, {@code false} if the given element is
   *         acceptable.
   */
  public boolean isUnacceptable(E element) {

    Ranking ranking = this.map.get(element);
    if (ranking == null) {
      return false;
    }
    return ranking.isUnacceptable();
  }

  /**
   * This method adds the given {@code gain} to the current {@link #getRank(Object) rank} of the given {@code element}.
   * If the {@code element} is {@link #setUnacceptable(Object) unacceptable}, this method will have no effect. This
   * method guarantees that there will be no overflow of the {@link #getRank(Object) rank}.
   *
   * @param element is the element to rank.
   * @param gain is the value to add to the current rank. It may be negative to reduce the rank. A value of {@code 0}
   *        will have no effect.
   * @return the new rank.
   */
  public int addRank(E element, int gain) {

    Ranking ranking = this.map.get(element);
    if (ranking == null) {
      if (gain == 0) {
        return 0;
      }
      ranking = new Ranking();
      this.map.put(element, ranking);
    }
    ranking.addRank(gain);
    return ranking.rank;
  }

  /**
   * This method gets the element that has currently the best (highest) positive {@link #getRank(Object) rank} . If
   * there are multiple best elements, it is unspecified which one is returned.
   *
   * @see #getBests()
   *
   * @return the element with the best positive rank or {@code null} if there is no element with a positive rank.
   */
  public E getBest() {

    return getBest(1);
  }

  /**
   * This method gets the element that has currently the best (highest) {@link #getRank(Object) rank} greater or equal
   * to the given {@code threshold}. If there are multiple best elements, it is unspecified which one is returned.
   *
   * @see #getBests()
   *
   * @param threshold is the minimum rank accepted for the best element.
   * @return the element with the best rank greater or equal to the given {@code threshold} or {@code null} if there is
   *         no such element.
   */
  public E getBest(int threshold) {

    E best = null;
    int bestRank = threshold - 1;
    for (E element : this.map.keySet()) {
      Ranking ranking = this.map.get(element);
      if (ranking != null) {
        if (ranking.rank > bestRank) {
          best = element;
          bestRank = ranking.rank;
        }
      }
    }
    return best;
  }

  /**
   * This method gets a {@link List} containing the element(s) that has currently the best (highest)
   * {@link #getRank(Object) rank}. If there is no element with a positive rank, an empty {@link List} will be returned.
   * If there are multiple elements with the (same) {@link #getBest() best} rank, they will all be contained in the
   * returned {@link List}.
   *
   * @return the {@link List} with the best element(s).
   */
  public List<E> getBests() {

    List<E> bests = new ArrayList<>();
    int bestRank = RANK_UNACCEPTABLE + 1;
    for (E element : this.map.keySet()) {
      Ranking ranking = this.map.get(element);
      if (ranking != null) {
        if (ranking.rank > bestRank) {
          bests.clear();
          bests.add(element);
          bestRank = ranking.rank;
        } else if (ranking.rank == bestRank) {
          bests.add(element);
        }
      }
    }
    return bests;
  }

  /**
   * This method gets all elements with a {@link #getRank(Object) rank} greater or equal to the given {@code threshold}.
   *
   * @param threshold is the minimum accepted {@link #getRank(Object) rank}.
   * @return the list with all elements better or equal to the given {@code threshold}.
   */
  public List<E> getBetterOrEqual(int threshold) {

    List<E> bests = new ArrayList<>();
    for (E element : this.map.keySet()) {
      Ranking ranking = this.map.get(element);
      if ((ranking != null) && (ranking.rank >= threshold)) {
        bests.add(element);
      }
    }
    return bests;
  }

  /**
   * This inner class represents the {@link #rank} of an element.
   */
  private static class Ranking {

    /** The ranking value. */
    private int rank;

    /**
     * @see RankMap#setUnacceptable(Object)
     */
    public void setUnacceptable() {

      this.rank = RANK_UNACCEPTABLE;
    }

    /**
     * @return {@code true} if {@link RankMap#RANK_UNACCEPTABLE unacceptable}.
     */
    public boolean isUnacceptable() {

      return (this.rank == RANK_UNACCEPTABLE);
    }

    /**
     * @see RankMap#addRank(Object, int)
     *
     * @param gain is the amount by which to increase the rank.
     */
    public void addRank(int gain) {

      if ((this.rank != RANK_UNACCEPTABLE) && (gain != 0)) {
        int newRank = this.rank + gain;
        if (gain > 0) {
          if (newRank < this.rank) {
            // overflow
            newRank = Integer.MAX_VALUE;
          }
        } else {
          if ((newRank > this.rank) || (newRank == RANK_UNACCEPTABLE)) {
            newRank = RANK_UNACCEPTABLE + 1;
          }
        }
        this.rank = newRank;
      }
    }
  }

}
