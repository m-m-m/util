/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.api.statement;

import net.sf.mmm.util.query.api.feature.FeatureDelete;
import net.sf.mmm.util.query.api.feature.FeatureSelect;
import net.sf.mmm.util.query.api.feature.FeatureUpdate;

/**
 * This is the interface for the factory used to create SQL {@link Statement}s using a type-safe and fluent builder API.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface StatementFactory extends FeatureSelect, FeatureUpdate, FeatureDelete {

}
