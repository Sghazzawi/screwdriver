package com.semperos.screwdriver.build;

import com.semperos.screwdriver.FileUtil;
import com.semperos.screwdriver.js.CoffeeScriptCompiler;
import com.semperos.screwdriver.js.rhino.RhinoEvaluatorException;
import com.semperos.screwdriver.pipeline.AssetSpec;
import com.semperos.screwdriver.pipeline.PipelineEnvironment;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Build assets that compile to JavaScript
 */
public class BuildJs extends BuildAssetWithRhino {
    private static Logger logger = Logger.getLogger(BuildJs.class);
    private PipelineEnvironment pe;
    private CoffeeScriptCompiler coffeeScriptCompiler;

    public BuildJs(PipelineEnvironment pe, AssetSpec assetSpec) {
        super(assetSpec);
        this.pe = pe;
        this.coffeeScriptCompiler = new CoffeeScriptCompiler();
    }

    private String compile(File sourceFile) throws IOException, RhinoEvaluatorException {
        String sourceFileName = sourceFile.toString();
        if (FilenameUtils.isExtension(sourceFileName, "js")) {
            return FileUtil.readFile(sourceFile);
        } else if (FilenameUtils.isExtension(sourceFileName, "coffee")) {
            return this.coffeeScriptCompiler.compile(sourceFile);
        } else {
            throw new RuntimeException("File of type " + sourceFileName + " is not supported by JavaScript compilation at this time.");
        }
    }

    @Override
    public boolean processFile(File sourceFile, File outputFile) throws IOException, RhinoEvaluatorException {
        String sourceFileName = sourceFile.toString();
        boolean processed = false;
        if (assetSpec.getAssetExtensions().contains(FilenameUtils.getExtension(sourceFileName))) {
            logger.info("Compiling file " + sourceFileName + " to JavaScript.");
            FileUtil.writeFile(compile(sourceFile), outputFile);
            processed = true;
            // Now go off and make source maps if that's been enabled
            if (pe.getJsSourceMapAssetSpec() != null) {
                BuildJsSourceMap build = new BuildJsSourceMap(pe, pe.getJsSourceMapAssetSpec());
                build.build(sourceFile);
            }
        } else if (pe != null && pe.getTemplateAssetSpec().getAssetExtensions().contains(FilenameUtils.getExtension(sourceFileName))) {
            // JavaScript templates are ignored here, because they are handled as part of
            // the BuildTemplate workflow.
            logger.trace("Ignoring JavaScript template " + sourceFileName + " as part of standard JavaScript compilation. Will be compiled in template compilation phase.");
        } else {
            logger.info("Copying file " + sourceFileName + " from the JavaScript directory.");
            FileUtil.copyFile(sourceFile, outputFile);
            processed = true;
        }
        return processed;
    }

}
