/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * This enum contains all possible directions.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public enum Direction implements SimpleDatatype<String>, NlsObject {

  /** Direction to the south (down/bottom). */
  SOUTH("S", NlsBundleUtilCoreRoot.INF_SOUTH) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoSouth();
    }
  },

  /** Direction to the east (right). */
  EAST("E", NlsBundleUtilCoreRoot.INF_EAST) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoEast();
    }
  },

  /** Direction the the west (left). */
  WEST("W", NlsBundleUtilCoreRoot.INF_WEST) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoWest();
    }
  },

  /** Direction the the north (up/top). */
  NORTH("N", NlsBundleUtilCoreRoot.INF_NORTH) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoNorth();
    }
  },

  /** Direction to the south-east (down/bottom and right). */
  SOUTH_EAST("SE", NlsBundleUtilCoreRoot.INF_SOUTH_EAST) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoSouthEast();
    }
  },

  /** Direction to the south-west (down/bottom and left). */
  SOUTH_WEST("SW", NlsBundleUtilCoreRoot.INF_SOUTH_WEST) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoSouthWest();
    }
  },

  /** Direction to the north-east (up/top and right). */
  NORTH_EAST("NE", NlsBundleUtilCoreRoot.INF_NORTH_EAST) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoNorthEast();
    }
  },

  /** Direction to the north-west (up/top and left). */
  NORTH_WEST("NW", NlsBundleUtilCoreRoot.INF_NORTH_WEST) {

    /**
     * {@inheritDoc}
     */
    @Override
    public NlsMessage toNlsMessage() {

      return getBundle().infoNorthWest();
    }
  };

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #getTitle()}.
   */
  private Direction(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

  /**
   * @return the instance of {@link NlsBundleUtilCoreRoot}.
   */
  private static NlsBundleUtilCoreRoot getBundle() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class);
  }

  /**
   * @return <code>true</code> if pointing to the east ({@link #EAST}, {@link #SOUTH_EAST}, or
   *         {@link #NORTH_EAST}), <code>false</code> otherwise.
   */
  public boolean isToEast() {

    return (this == EAST) || (this == SOUTH_EAST) || (this == NORTH_EAST);
  }

  /**
   * @return <code>true</code> if pointing to the west ({@link #WEST}, {@link #SOUTH_WEST}, or
   *         {@link #NORTH_WEST}), <code>false</code> otherwise.
   */
  public boolean isToWest() {

    return (this == WEST) || (this == SOUTH_WEST) || (this == NORTH_WEST);
  }

  /**
   * @return <code>true</code> if pointing to the south ({@link #SOUTH}, {@link #SOUTH_EAST}, or
   *         {@link #SOUTH_WEST}), <code>false</code> otherwise.
   */
  public boolean isToSouth() {

    return (this == SOUTH) || (this == SOUTH_EAST) || (this == SOUTH_WEST);
  }

  /**
   * @return <code>true</code> if pointing to the north ({@link #NORTH}, {@link #NORTH_EAST}, or
   *         {@link #NORTH_WEST}), <code>false</code> otherwise.
   */
  public boolean isToNorth() {

    return (this == NORTH) || (this == NORTH_EAST) || (this == NORTH_WEST);
  }

}
