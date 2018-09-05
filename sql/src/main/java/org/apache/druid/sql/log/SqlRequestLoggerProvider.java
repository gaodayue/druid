/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.sql.log;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.inject.Provider;
import org.apache.druid.guice.annotations.ExtensionPoint;

/**
 * A Marker interface for things that can provide a SqlRequestLogger.  This can be combined with jackson polymorphic serde
 * to provide new SqlRequestLogger implementations as plugins.
 */
@ExtensionPoint
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = NoopSqlRequestLoggerProvider.class)
@JsonSubTypes(value = {
    @JsonSubTypes.Type(name = "noop", value = NoopSqlRequestLoggerProvider.class),
    @JsonSubTypes.Type(name = "file", value = FileSqlRequestLoggerProvider.class)
})
public interface SqlRequestLoggerProvider extends Provider<SqlRequestLogger>
{
}
