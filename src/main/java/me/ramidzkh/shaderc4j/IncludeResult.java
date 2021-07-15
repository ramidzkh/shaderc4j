/*
 * Copyright 2019, 2020 grondag
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package me.ramidzkh.shaderc4j;

import java.util.Objects;

public class IncludeResult {

	private final String path, content;

	public IncludeResult(String path, String content) {
		this.path = path;
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public String getContent() {
		return content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		IncludeResult that = (IncludeResult) o;
		return Objects.equals(path, that.path) &&
				Objects.equals(content, that.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(path, content);
	}

	@Override
	public String toString() {
		return "IncludeResult{" +
				"path='" + path + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
