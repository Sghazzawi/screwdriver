package com.semperos.screwdriver.pipeline;

import com.semperos.screwdriver.Config;

import java.io.File;
import java.util.ArrayList;

/**
 * Environment for managing paths to various assets to be processed
 * as part of a build.
 */
public class PipelineEnvironment {
    private JsAssetSpec jsAssetSpec;
    private CssAssetSpec cssAssetSpec;
    private ImageAssetSpec imageAssetSpec;
    private ServerTemplateAssetSpec serverTemplateAssetSpec;

    public JsAssetSpec getJsAssetSpec() {
        return jsAssetSpec;
    }

    public void setJsAssetSpec(JsAssetSpec jsAssetSpec) {
        this.jsAssetSpec = jsAssetSpec;
    }

    public CssAssetSpec getCssAssetSpec() {
        return cssAssetSpec;
    }

    public void setCssAssetSpec(CssAssetSpec cssAssetSpec) {
        this.cssAssetSpec = cssAssetSpec;
    }

    public ImageAssetSpec getImageAssetSpec() {
        return imageAssetSpec;
    }

    public void setImageAssetSpec(ImageAssetSpec imageAssetSpec) {
        this.imageAssetSpec = imageAssetSpec;
    }

    public ServerTemplateAssetSpec getServerTemplateAssetSpec() {
        return serverTemplateAssetSpec;
    }

    public void setServerTemplateAssetSpec(ServerTemplateAssetSpec serverTemplateAssetSpec) {
        this.serverTemplateAssetSpec = serverTemplateAssetSpec;
    }

    /**
     * @todo Unhardcode as needed
     */
    public PipelineEnvironment(Config cfg) {
        File jsAssetPath = new File(cfg.getAssetDirectory(), "javascripts");
        File jsOutputPath = new File(cfg.getOutputDirectory(), "javascripts");
        ArrayList<String> jsAssetExtensions = new ArrayList<String>();
        jsAssetExtensions.add("js");
        jsAssetExtensions.add("coffee");
        jsAssetSpec = new JsAssetSpec(jsAssetPath, jsAssetExtensions, jsOutputPath);
        if (cfg.getJsFileFilter() != null) {
            jsAssetSpec.setAssetFileFilter(cfg.getJsFileFilter());
        }
        if (cfg.getJsDirFilter() != null) {
            jsAssetSpec.setAssetDirFilter(cfg.getJsDirFilter());
        }
        if (cfg.getJsIncludes() != null && cfg.getJsIncludes().size() > 0) {
            jsAssetSpec.setAssetIncludes(cfg.getJsIncludes());
        } else {
            jsAssetSpec.setAssetExcludes(cfg.getJsExcludes());
        }

        File cssAssetPath = new File(cfg.getAssetDirectory(), "stylesheets");
        File cssOutputPath = new File(cfg.getOutputDirectory(), "stylesheets");
        ArrayList<String> cssAssetExtensions = new ArrayList<String>();
        cssAssetExtensions.add("css");
        cssAssetExtensions.add("less");
        cssAssetExtensions.add("sass");
        cssAssetExtensions.add("styl");
        cssAssetSpec = new  CssAssetSpec(cssAssetPath, cssAssetExtensions, cssOutputPath);
        if (cfg.getCssFileFilter() != null) {
            cssAssetSpec.setAssetFileFilter(cfg.getCssFileFilter());
        }
        if (cfg.getCssDirFilter() != null) {
            cssAssetSpec.setAssetDirFilter(cfg.getCssDirFilter());
        }
        if (cfg.getCssIncludes() != null && cfg.getCssIncludes().size() > 0) {
            cssAssetSpec.setAssetIncludes(cfg.getCssIncludes());
        } else {
            cssAssetSpec.setAssetExcludes(cfg.getCssExcludes());
        }


        File imageAssetPath = new File(cfg.getAssetDirectory(), "images");
        File imageOutputPath = new File(cfg.getOutputDirectory(), "images");
        ArrayList<String> imageAssetExtensions = new ArrayList<String>();
        imageAssetExtensions.add("bmp");
        imageAssetExtensions.add("gif");
        imageAssetExtensions.add("jpg");
        imageAssetExtensions.add("jpeg");
        imageAssetExtensions.add("png");
        imageAssetExtensions.add("svg");
        imageAssetSpec = new ImageAssetSpec(imageAssetPath, imageAssetExtensions, imageOutputPath);
        if (cfg.getImageFileFilter() != null) {
            imageAssetSpec.setAssetFileFilter(cfg.getImageFileFilter());
        }
        if (cfg.getImageDirFilter() != null) {
            imageAssetSpec.setAssetDirFilter(cfg.getImageDirFilter());
        }
        if (cfg.getImageIncludes() != null && cfg.getImageIncludes().size() > 0) {
            imageAssetSpec.setAssetIncludes(cfg.getImageIncludes());
        } else {
            imageAssetSpec.setAssetExcludes(cfg.getImageExcludes());
        }

        File templateAssetPath = new File(cfg.getAssetDirectory(), "server_templates");
        File templateOutputPath = cfg.getOutputDirectory();
        ArrayList<String> templateExtensions = new ArrayList<>();
        templateExtensions.add("jade");
        serverTemplateAssetSpec = new ServerTemplateAssetSpec(templateAssetPath, templateExtensions, templateOutputPath);
        serverTemplateAssetSpec.setAssetLocals(cfg.getServerTemplateLocals());
        if (cfg.getServerTemplateFileFilter() != null) {
            serverTemplateAssetSpec.setAssetFileFilter(cfg.getServerTemplateFileFilter());
        }
        if (cfg.getServerTemplateDirFilter() != null) {
            serverTemplateAssetSpec.setAssetDirFilter(cfg.getServerTemplateDirFilter());
        }
        if (cfg.getServerTemplateIncludes() != null && cfg.getServerTemplateIncludes().size() > 0) {
            serverTemplateAssetSpec.setAssetIncludes(cfg.getServerTemplateIncludes());
        } else {
            serverTemplateAssetSpec.setAssetExcludes(cfg.getServerTemplateExcludes());
        }
    }

}
