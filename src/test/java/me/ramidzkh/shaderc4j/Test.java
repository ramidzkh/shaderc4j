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

public class Test {

    public static void main(String[] args) {
        try (ShaderCompiler compiler = new ShaderCompiler()) {
            compiler.addMacroDefinition("EP", "main");
            compiler.setTargetEnvironment(TargetEnvironment.OPENGL, 450);
            compiler.setSourceLanguage(SourceLanguage.GLSL);
            compiler.setIncludeCallback((requestedSource, type, requestingSource, depth) -> {
                if (requestedSource.equals("epic.glsl")) {
                    return new IncludeResult(requestedSource, "double epic;\n");
                }

                return new IncludeResult(requestedSource, "");
            });

            CompilationArtifact artifact = compiler.compileIntoSpirvAssembly(
                    "#version 450\n" +
                    "#include <epic.glsl>\n" +
                    "\n" +
                    "void EP() {}", ShaderKind.VERTEX_SHADER, "shader.hlsl", "main");
            System.out.println(artifact.getString());
        }
    }
}
