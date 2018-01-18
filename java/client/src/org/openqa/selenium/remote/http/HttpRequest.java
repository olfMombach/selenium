// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.openqa.selenium.remote.http;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import java.util.Collection;
import java.util.Objects;

public class HttpRequest extends HttpMessage {

  private final HttpMethod method;
  private final String uri;
  private final Multimap<String, String> queryParameters = Multimaps.newListMultimap(
      Maps.<String, Collection<String>>newHashMap(), Lists::newLinkedList);

  public HttpRequest(HttpMethod method, String uri) {
    this.method = method;
    this.uri = uri;
  }

  public String getUri() {
    return uri;
  }

  public HttpMethod getMethod() {
    return method;
  }

  /**
   * Get a query parameter. The implementation will take care of decoding the from percent encoding.
   */
  public String getQueryParameter(String name) {
    return queryParameters.get(name).stream().findFirst().orElse(null);
  }

  /**
   * Set a query parameter, adding to existing values if present. The implementation will ensure
   * that the name and value are properly encoded.
   */
  public void addQueryParameter(String name, String value) {
    queryParameters.put(
        Objects.requireNonNull(name, "Name must be set"),
        Objects.requireNonNull(value, "Value must be set"));
  }

  public Iterable<String> getQueryParameterNames() {
    return queryParameters.keySet();
  }

  public Iterable<String> getQueryParameters(String name) {
    return queryParameters.get(name);
  }
}
