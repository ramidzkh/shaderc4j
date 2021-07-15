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

public enum ShaderKind {

	VERTEX_SHADER(Shaderc.shaderc_vertex_shader),
	FRAGMENT_SHADER(Shaderc.shaderc_fragment_shader),
	COMPUTE_SHADER(Shaderc.shaderc_compute_shader),
	GEOMETRY_SHADER(Shaderc.shaderc_geometry_shader),
	TESS_CONTROL_SHADER(Shaderc.shaderc_tess_control_shader),
	TESS_EVALUATION_SHADER(Shaderc.shaderc_tess_evaluation_shader),
	GLSL_VERTEX_SHADER(Shaderc.shaderc_glsl_vertex_shader),
	GLSL_FRAGMENT_SHADER(Shaderc.shaderc_glsl_fragment_shader),
	GLSL_COMPUTE_SHADER(Shaderc.shaderc_glsl_compute_shader),
	GLSL_GEOMETRY_SHADER(Shaderc.shaderc_glsl_geometry_shader),
	GLSL_TESS_CONTROL_SHADER(Shaderc.shaderc_glsl_tess_control_shader),
	GLSL_TESS_EVALUATION_SHADER(Shaderc.shaderc_glsl_tess_evaluation_shader),
	GLSL_INFER_FROM_SOURCE(Shaderc.shaderc_glsl_infer_from_source),
	GLSL_DEFAULT_VERTEX_SHADER(Shaderc.shaderc_glsl_default_vertex_shader),
	GLSL_DEFAULT_FRAGMENT_SHADER(Shaderc.shaderc_glsl_default_fragment_shader),
	GLSL_DEFAULT_COMPUTE_SHADER(Shaderc.shaderc_glsl_default_compute_shader),
	GLSL_DEFAULT_GEOMETRY_SHADER(Shaderc.shaderc_glsl_default_geometry_shader),
	GLSL_DEFAULT_TESS_CONTROL_SHADER(Shaderc.shaderc_glsl_default_tess_control_shader),
	GLSL_DEFAULT_TESS_EVALUATION_SHADER(Shaderc.shaderc_glsl_default_tess_evaluation_shader),
	SPIRV_ASSEMBLY(Shaderc.shaderc_spirv_assembly),
	RAYGEN_SHADER(Shaderc.shaderc_raygen_shader),
	ANYHIT_SHADER(Shaderc.shaderc_anyhit_shader),
	CLOSESTHIT_SHADER(Shaderc.shaderc_closesthit_shader),
	MISS_SHADER(Shaderc.shaderc_miss_shader),
	INTERSECTION_SHADER(Shaderc.shaderc_intersection_shader),
	CALLABLE_SHADER(Shaderc.shaderc_callable_shader),
	GLSL_RAYGEN_SHADER(Shaderc.shaderc_glsl_raygen_shader),
	GLSL_ANYHIT_SHADER(Shaderc.shaderc_glsl_anyhit_shader),
	GLSL_CLOSESTHIT_SHADER(Shaderc.shaderc_glsl_closesthit_shader),
	GLSL_MISS_SHADER(Shaderc.shaderc_glsl_miss_shader),
	GLSL_INTERSECTION_SHADER(Shaderc.shaderc_glsl_intersection_shader),
	GLSL_CALLABLE_SHADER(Shaderc.shaderc_glsl_callable_shader),
	GLSL_DEFAULT_RAYGEN_SHADER(Shaderc.shaderc_glsl_default_raygen_shader),
	GLSL_DEFAULT_ANYHIT_SHADER(Shaderc.shaderc_glsl_default_anyhit_shader),
	GLSL_DEFAULT_CLOSESTHIT_SHADER(Shaderc.shaderc_glsl_default_closesthit_shader),
	GLSL_DEFAULT_MISS_SHADER(Shaderc.shaderc_glsl_default_miss_shader),
	GLSL_DEFAULT_INTERSECTION_SHADER(Shaderc.shaderc_glsl_default_intersection_shader),
	GLSL_DEFAULT_CALLABLE_SHADER(Shaderc.shaderc_glsl_default_callable_shader),
	TASK_SHADER(Shaderc.shaderc_task_shader),
	MESH_SHADER(Shaderc.shaderc_mesh_shader),
	GLSL_TASK_SHADER(Shaderc.shaderc_glsl_task_shader),
	GLSL_MESH_SHADER(Shaderc.shaderc_glsl_mesh_shader),
	GLSL_DEFAULT_TASK_SHADER(Shaderc.shaderc_glsl_default_task_shader),
	SHADERC_GLSL_DEFAULT_MESH_SHADER(Shaderc.shaderc_glsl_default_mesh_shader);

	final int internal;

	ShaderKind(int internal) {
		this.internal = internal;
	}
}
