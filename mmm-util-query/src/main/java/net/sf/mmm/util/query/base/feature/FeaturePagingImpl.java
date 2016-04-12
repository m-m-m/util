/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.feature;

import net.sf.mmm.util.query.api.feature.FeaturePaging;
import net.sf.mmm.util.query.api.feature.FeatureWhere;
import net.sf.mmm.util.query.base.statement.SqlBuilder;

/**
 * Implementation of {@link AbstractFeature} for {@link FeatureWhere}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FeaturePagingImpl extends AbstractFeature implements FeaturePaging<FeaturePagingImpl> {

  private int limit;

  private long offset;

  /**
   * The constructor.
   */
  public FeaturePagingImpl() {
    super(SORT_INDEX_PAGING);
    this.limit = Integer.MAX_VALUE;
    this.offset = 0;
  }

  @Override
  public FeaturePagingImpl limit(int newLimit) {

    this.limit = newLimit;
    return this;
  }

  /**
   * @return the limit
   */
  public int getLimit() {

    return this.limit;
  }

  @Override
  public FeaturePagingImpl offset(long newOffset) {

    this.offset = newOffset;
    return this;
  }

  /**
   * @return the offset
   */
  public long getOffset() {

    return this.offset;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.offset != 0) {
      String sqlOffset = builder.getDialect().offset();
      if (!sqlOffset.isEmpty()) {
        builder.getBuffer().append(sqlOffset);
        builder.addParameter(Long.valueOf(this.offset));
      }
    }
    if (this.limit != Integer.MAX_VALUE) {
      String sqlLimit = builder.getDialect().limit();
      if (!sqlLimit.isEmpty()) {
        builder.getBuffer().append(sqlLimit);
        builder.addParameter(Integer.valueOf(this.limit));
      }
    }
  }

}
