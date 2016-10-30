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
 * @since 8.4.0
 */
public class FeaturePagingImpl extends AbstractFeature implements FeaturePaging<FeaturePagingImpl> {

  private Integer limit;

  private Long offset;

  /**
   * The constructor.
   */
  public FeaturePagingImpl() {
    super(SORT_INDEX_PAGING);
  }

  @Override
  public FeaturePagingImpl limit(int newLimit) {

    if (newLimit < 0) {
      throw new IllegalArgumentException(Long.toString(newLimit));
    }
    if (newLimit == Integer.MAX_VALUE) {
      this.limit = null;
    } else {
      this.limit = Integer.valueOf(newLimit);
    }
    return this;
  }

  /**
   * @return the {@link #limit(int) limit}-
   */
  public Integer getLimit() {

    return this.limit;
  }

  @Override
  public FeaturePagingImpl offset(long newOffset) {

    if (newOffset < 0) {
      throw new IllegalArgumentException(Long.toString(newOffset));
    }
    if (newOffset == 0) {
      this.offset = null;
    } else {
      this.offset = Long.valueOf(newOffset);
    }
    return this;
  }

  /**
   * @return the {@link #offset(long) offset}.
   */
  public Long getOffset() {

    return this.offset;
  }

  @Override
  public void build(SqlBuilder builder) {

    if (this.offset != null) {
      String sqlOffset = getDialect().offset();
      if (!sqlOffset.isEmpty()) {
        builder.getBuffer().append(sqlOffset);
        builder.addParameter(this.offset);
      }
    }
    if (this.limit != null) {
      String sqlLimit = getDialect().limit();
      if (!sqlLimit.isEmpty()) {
        builder.getBuffer().append(sqlLimit);
        builder.addParameter(this.limit);
      }
    }
  }

}
