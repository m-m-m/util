/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.statement.orientdb;

import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

import net.sf.mmm.util.query.base.path.Alias;

/**
 * Helper class with {@link OrientDbDialect OrientDB} specific functions.
 *
 * @author hohwille
 * @since 8.0.0
 */
public final class ODB {

  private ODB() {
  }

  /**
   * @param oClass the {@link OClass OrientDB Class}.
   * @return the corresponding {@link Alias}.
   */
  public static Alias<ODocument> alias(OClass oClass) {

    return new Alias<>(oClass.getName(), null, ODocument.class);
  }

}
