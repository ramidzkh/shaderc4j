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

import java.nio.charset.StandardCharsets;

public class CompilationArtifact {

	private final boolean isBinary;
	private final byte[] binary;
	private final long warningCount;
	private final String warning;

	public CompilationArtifact(boolean isBinary, byte[] binary, long warningCount, String warning) {
		this.isBinary = isBinary;
		this.binary = binary;
		this.warningCount = warningCount;
		this.warning = warning;
	}

	public boolean isBinary() {
		return isBinary;
	}

	public long getWarningCount() {
		return warningCount;
	}

	public String getWarning() {
		return warning;
	}

	public byte[] getBinary() {
		if (!isBinary) {
			throw new AssertionError();
		}

		return binary;
	}

	public String getString() {
		if (isBinary) {
			throw new AssertionError();
		}

		return new String(binary, StandardCharsets.UTF_8);
	}
}
