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
import org.apache.druid.query.Query;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.Objects;

public class RequestLogLine extends RequestLogLineBase
{
  private static final Joiner JOINER = Joiner.on("\t");

  private final Query query;

  public RequestLogLine(DateTime timestamp, String remoteAddr, Query query, QueryStats queryStats)
  {
    super(timestamp, remoteAddr, queryStats);
    this.query = query;
  }

  public String getLine(ObjectMapper objectMapper) throws JsonProcessingException
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

  @JsonProperty("query")
  public Query getQuery()
  {
    return query;
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
    if (!super.equals(o)) {
      return false;
    }
    RequestLogLine that = (RequestLogLine) o;
    return Objects.equals(query, that.query);
  }

  @Override
  public int hashCode()
  {

    return Objects.hash(super.hashCode(), query);
  }

  @Override
  public String toString()
  {
    return "RequestLogLine{" +
           "timestamp=" + timestamp +
           ", remoteAddr='" + remoteAddr + '\'' +
           ", query=" + query +
           ", queryStats=" + queryStats +
           '}';
  }
}
