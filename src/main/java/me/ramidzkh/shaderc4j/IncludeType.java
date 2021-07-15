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

import org.lwjgl.util.shaderc.Shaderc;

/**
 * Identifies the type of include directive
 */
public enum IncludeType {

	/**
	 * Include directives of the form <code>#include "..."</code>
	 */
	RELATIVE(Shaderc.shaderc_include_type_relative),

	/**
	 * Include directives of the form <code>#include &lt;...&gt;</code>
	 */
	STANDARD(Shaderc.shaderc_include_type_standard);

	private final int internal;

	IncludeType(int internal) {
		this.internal = internal;
	}

	static IncludeType from(int type) {
		if (type == RELATIVE.internal) {
			return RELATIVE;
		} else if (type == STANDARD.internal) {
			return STANDARD;
		} else {
			throw new IllegalArgumentException("Unknown include type: " + type);
		}
	}
}
