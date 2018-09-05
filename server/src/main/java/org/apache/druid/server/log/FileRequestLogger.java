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

package org.apache.druid.server.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.lifecycle.LifecycleStart;
import org.apache.druid.java.util.common.lifecycle.LifecycleStop;
import org.apache.druid.server.RequestLogLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ScheduledExecutorService;

/**
 */
public class FileRequestLogger extends AbstractFileRequestLogger implements RequestLogger
{
  private final ObjectMapper objectMapper;

  public FileRequestLogger(ObjectMapper objectMapper, ScheduledExecutorService exec, File baseDir)
  {
    super(exec, baseDir);
    this.objectMapper = objectMapper;
  }

  @LifecycleStart
  @Override
  public void start()
  {
    super.start();
  }

  @Override
  protected OutputStreamWriter getFileWriter() throws FileNotFoundException
  {
    return new OutputStreamWriter(
        new FileOutputStream(new File(baseDir, currentDay.toString("yyyy-MM-dd'.log'")), true),
        StandardCharsets.UTF_8
    );
  }

  @LifecycleStop
  @Override
  public void stop()
  {
    super.stop();
  }

  @Override
  public void log(RequestLogLine requestLogLine) throws IOException
  {
    String message = StringUtils.format("%s%n", requestLogLine.getLine(objectMapper));
    logToFile(message);
  }

  @Override
  public String toString()
  {
    return "FileRequestLogger{" +
           "baseDir=" + baseDir +
           '}';
  }
}
