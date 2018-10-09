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

package org.apache.druid.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.apache.druid.query.Query;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

public class RequestLogLine
{
  private static final Joiner JOINER = Joiner.on("\t");

  private final Query query;
  private final String sql;
  private final DateTime timestamp;
  private final String remoteAddr;
  private final QueryStats queryStats;

  private RequestLogLine(
      @Nullable Query query,
      @Nullable String sql,
      DateTime timestamp,
      @Nullable String remoteAddr,
      QueryStats queryStats
  )
  {
    this.query = query;
    this.sql = sql;
    this.timestamp = Preconditions.checkNotNull(timestamp, "timestamp");
    this.remoteAddr = remoteAddr;
    this.queryStats = Preconditions.checkNotNull(queryStats, "queryStats");
  }

  public static RequestLogLine forNative(Query query, DateTime timestamp, String remoteAddr, QueryStats queryStats)
  {
    return new RequestLogLine(query, null, timestamp, remoteAddr, queryStats);
  }

  public static RequestLogLine forSql(String sql, DateTime timestamp, String remoteAddr, QueryStats queryStats)
  {
    return new RequestLogLine(null, sql, timestamp, remoteAddr, queryStats);
  }

  public String getNativeQueryLine(ObjectMapper objectMapper) throws JsonProcessingException
  {
    return JOINER.join(
        Arrays.asList(
            timestamp,
            remoteAddr,
            objectMapper.writeValueAsString(query),
            objectMapper.writeValueAsString(queryStats)
        )
    );
  }

  public String getSqlQueryLine(ObjectMapper objectMapper) throws JsonProcessingException
  {
    return "# " + String.valueOf(timestamp) + " "
           + remoteAddr + " "
           + objectMapper.writeValueAsString(queryStats) + "\n"
           + sql;
  }

  @JsonProperty("query")
  public Query getQuery()
  {
    return query;
  }

  @JsonProperty("sql")
  public String getSql()
  {
    return sql;
  }

  @JsonProperty("timestamp")
  public DateTime getTimestamp()
  {
    return timestamp;
  }

  @JsonProperty("remoteAddr")
  public String getRemoteAddr()
  {
    return remoteAddr;
  }

  @JsonProperty("queryStats")
  public QueryStats getQueryStats()
  {
    return queryStats;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RequestLogLine)) {
      return false;
    }
    RequestLogLine that = (RequestLogLine) o;
    return Objects.equals(query, that.query) &&
           Objects.equals(sql, that.sql) &&
           Objects.equals(timestamp, that.timestamp) &&
           Objects.equals(remoteAddr, that.remoteAddr) &&
           Objects.equals(queryStats, that.queryStats);
  }

  @Override
  public int hashCode()
  {

    return Objects.hash(query, sql, timestamp, remoteAddr, queryStats);
  }

  @Override
  public String toString()
  {
    return "RequestLogLine{" +
           "query=" + query +
           ", sql='" + sql + '\'' +
           ", timestamp=" + timestamp +
           ", remoteAddr='" + remoteAddr + '\'' +
           ", queryStats=" + queryStats +
           '}';
  }
}
