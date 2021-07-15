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
 * The environment semantics to compile with
 */
public enum TargetEnvironment {

	/**
	 * Compile under Vulkan semantics
	 */
	VULKAN(Shaderc.shaderc_target_env_vulkan),

	/**
	 * Compile under OpenGL semantics
	 */
	OPENGL(Shaderc.shaderc_target_env_opengl),

	/**
	 * Compile under OpenGL semantics, including compatibility profile functions
	 */
	OPENGL_COMPATIBILITY(Shaderc.shaderc_target_env_opengl_compat),

	/**
	 * Compile under WebGPU semantics
	 */
	WEB_GPU(Shaderc.shaderc_target_env_webgpu);

	final int internal;

	TargetEnvironment(int internal) {
		this.internal = internal;
	}
}
