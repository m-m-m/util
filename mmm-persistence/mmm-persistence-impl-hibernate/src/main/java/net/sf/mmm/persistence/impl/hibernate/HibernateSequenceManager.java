/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import java.util.Properties;

import net.sf.mmm.persistence.api.sequence.Sequence;
import net.sf.mmm.persistence.base.jpa.JpaSequenceManager;
import net.sf.mmm.util.component.api.ResourceMissingException;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.Dialect;

/**
 * This is the implementation of {@link SecurityManager} based on {@link JpaSequenceManager} and hibernate
 * {@link Dialect}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class HibernateSequenceManager extends JpaSequenceManager {

  /** @see #getHibernateDialect() */
  private String hibernateDialect;

  /** @see #getDialect() */
  private Dialect dialect;

  /**
   * The constructor.
   */
  public HibernateSequenceManager() {

    super();
  }

  /**
   * @return the hibernateDialect
   */
  public String getHibernateDialect() {

    return this.hibernateDialect;
  }

  /**
   * @param hibernateDialect is the new value of {@link #getHibernateDialect()}.
   */
  public void setHibernateDialect(String hibernateDialect) {

    this.hibernateDialect = hibernateDialect;
  }

  /**
   * @return the dialect
   */
  public Dialect getDialect() {

    if (this.dialect == null) {
      if (this.hibernateDialect != null) {
        Properties properties = new Properties();
        properties.setProperty(AvailableSettings.DIALECT, this.hibernateDialect);
        this.dialect = Dialect.getDialect(properties);
      }
    }
    return this.dialect;
  }

  /**
   * @param dialect is the new value of {@link #getDialect()}.
   */
  public void setDialect(Dialect dialect) {

    this.dialect = dialect;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String createNextValueSql(Sequence sequence) {

    Dialect d = getDialect();
    if (d == null) {
      throw new ResourceMissingException("hibernateDialect");
    }
    return d.getSequenceNextValString(getSequenceAsString(sequence));
  }

}
