package com.semperos.screwdriver.build;

import com.semperos.screwdriver.FileUtil;
import com.semperos.screwdriver.pipeline.ImageAssetSpec;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Steps required to "build" an image.
 *
 * For now, images are just copied. However, we could also include tooling
 * for applying automatic optimizations/transformations to images, e.g., to
 * keep them at a certain size or quality level.
 */
public class BuildImage {
    private static Logger logger = Logger.getLogger(BuildImage.class);
    ImageAssetSpec imageAssetSpec;
    public BuildImage(ImageAssetSpec imageAssetSpec) {
        this.imageAssetSpec = imageAssetSpec;
    }

    public void compile(File sourceFile) {
        // This is a stand-in for possible future functionality, e.g., auto-optimization
        // of images for the web
    }

    public void build(File sourceFile) throws IOException {
        File outputFile = imageAssetSpec.outputFile(sourceFile);
        if ((!outputFile.exists()) ||
                (outputFile.exists() && FileUtils.isFileNewer(sourceFile, outputFile))) {
            logger.info("Processing file " + sourceFile.toString() + " as an image.");
            FileUtil.copyFile(sourceFile, imageAssetSpec.outputFile(sourceFile));
        }
    }

    public void buildAll() throws IOException {
        for (File f : imageAssetSpec.findFiles()) {
            build(f);
        }
    }

    public void delete(File sourceFile) {
        imageAssetSpec.outputFile(sourceFile).delete();
    }
}
