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

import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.shaderc.ShadercIncludeResolveI;
import org.lwjgl.util.shaderc.ShadercIncludeResult;
import org.lwjgl.util.shaderc.ShadercIncludeResultReleaseI;

import java.nio.ByteBuffer;

import static org.lwjgl.util.shaderc.Shaderc.*;

/**
 * The entrypoint into compiling a shader from source
 */
public class ShaderCompiler implements AutoCloseable {

    private long compiler;
    private long options;

    private long resolve;
    private long release;

    public ShaderCompiler() {
        compiler = shaderc_compiler_initialize();
        options = shaderc_compile_options_initialize();
    }

    // <editor-fold desc="Options">
    public void setTargetEnvironment(TargetEnvironment target, int version) {
        assertOpen();
        shaderc_compile_options_set_target_env(options, target.internal, version);
    }

    public void setSourceLanguage(SourceLanguage language) {
        assertOpen();
        shaderc_compile_options_set_source_language(options, language.internal);
    }

    public void setIncludeCallback(IncludeCallback callback) {
        assertOpen();

        if (resolve != 0) {
            Callback.free(resolve);
        }

        resolve = ((ShadercIncludeResolveI) (user_data, requested_source, type, requesting_source, include_depth) -> {
            IncludeResult result = callback.getResource(MemoryUtil.memUTF8Safe(requested_source), IncludeType.from(type), MemoryUtil.memUTF8Safe(requesting_source), include_depth);
            ShadercIncludeResult r = ShadercIncludeResult.calloc()
                    .source_name(MemoryUtil.memUTF8Safe(result.getPath()))
                    .content(MemoryUtil.memUTF8(result.getContent(), false))
                    .user_data(user_data);
            return r.address();
        }).address();

        if (release == 0) {
            release = ((ShadercIncludeResultReleaseI) (user_data, include_result) -> {
                ShadercIncludeResult r = ShadercIncludeResult.create(include_result);
                MemoryUtil.memFree(r.source_name());
                MemoryUtil.memFree(r.content());
                r.free();
            }).address();
        }

        // TODO: Make this MemoryUtil.NULL once a fixed version is released
        nshaderc_compile_options_set_include_callbacks(options, resolve, release, 1);
    }

    public void addMacroDefinition(String name, String value) {
        assertOpen();
        shaderc_compile_options_add_macro_definition(options, name, value);
    }
    // </editor-fold>

    // <editor-fold desc="Compiler">
    private static CompilationArtifact wrap(long result, boolean isBinary) {
        int status = shaderc_result_get_compilation_status(result);

        if (status == 0) {
            // noinspection ConstantConditions
            return new CompilationArtifact(isBinary,
                    copy(shaderc_result_get_bytes(result)),
                    shaderc_result_get_num_warnings(result),
                    shaderc_result_get_error_message(result));
        } else {
            long errorCount = shaderc_result_get_num_errors(result);
            String errorMessage = shaderc_result_get_error_message(result);

            throw new ShaderCompilationException(errorCount + " errors: " + errorMessage);
        }
    }

    private static byte[] copy(ByteBuffer buffer) {
        byte[] array = new byte[buffer.remaining()];
        buffer.get(array);
        return array;
    }

    public CompilationArtifact compileIntoSpirv(String text, ShaderKind kind, String inputFileName, String entrypointName) {
        assertOpen();
        return wrap(shaderc_compile_into_spv(compiler, text, kind.internal, inputFileName, entrypointName, options), true);
    }

    public CompilationArtifact compileIntoSpirvAssembly(String text, ShaderKind kind, String inputFileName, String entrypointName) {
        assertOpen();
        return wrap(shaderc_compile_into_spv_assembly(compiler, text, kind.internal, inputFileName, entrypointName, options), false);
    }

    public CompilationArtifact preprocess(String text, ShaderKind kind, String inputFileName, String entrypointName) {
        assertOpen();
        return wrap(shaderc_compile_into_preprocessed_text(compiler, text, kind.internal, inputFileName, entrypointName, options), false);
    }

    public CompilationArtifact assemble(String assembly) {
        assertOpen();
        return wrap(shaderc_assemble_into_spv(compiler, assembly, options), true);
    }
    // </editor-fold>

    private void assertOpen() {
        if (compiler == MemoryUtil.NULL) {
            throw new AssertionError("The compiler is uninitialized");
        } else if (options == MemoryUtil.NULL) {
            throw new AssertionError("The options are uninitialized");
        }
    }

    @Override
    public void close() {
        if (compiler != MemoryUtil.NULL) {
            shaderc_compiler_release(compiler);
            compiler = MemoryUtil.NULL;
        }

        if (options != MemoryUtil.NULL) {
            shaderc_compile_options_release(options);
            options = MemoryUtil.NULL;
        }

        if (resolve != 0) {
            Callback.free(resolve);
        }

        if (release != 0) {
            Callback.free(release);
        }
    }
}
