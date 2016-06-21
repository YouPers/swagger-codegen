package com.youpers.zocaalo.codegen;

import io.swagger.codegen.*;
import io.swagger.codegen.languages.AndroidClientCodegen;
import io.swagger.models.properties.*;

import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;

import java.util.*;
import java.io.File;

public class AndroidSyncClientCodeGen extends AndroidClientCodegen {

    // source folder where to write the files
    protected String apiVersion = "1.0.0";

    protected String syncPackage = "io.swagger.client.sync";
    protected String observerPackage = "io.swagger.client.observer";
    protected String contentPackage = "io.swagger.client.content";

    /**
     * Configures a friendly name for the generator.  This will be used by the generator
     * to select the library with the -l flag.
     *
     * @return the friendly name for the generator
     */
    public String getName() {
        return "AndroidSyncClient";
    }

    /**
     * Returns human-friendly help for the generator.  Provide the consumer with help
     * tips, parameters here
     *
     * @return A string value for the help message
     */
    public String getHelp() {
        return "Generates a AndroidSyncClient client library.";
    }

    public AndroidSyncClientCodeGen() {
        super();

        // set the output folder here
        outputFolder = "generated-code/AndroidSyncClient";

        /**
         * Template Location.  This is the location which templates will be read from.  The generator
         * will use the resource stream to attempt to read the templates.
         */
//        templateDir = "AndroidSyncClientCodegen";
//        embeddedTemplateDir = templateDir = "AndroidSyncClientCodegen/volley";

        /**
         * Additional Properties.  These values can be passed to the templates and
         * are available in models, apis, and supporting files
         */
        additionalProperties.put("apiVersion", apiVersion);

    }

    @Override
    public void processOpts() {
        super.processOpts();
        supportingFiles.add(new SupportingFile("sync/syncAdapter.mustache",
                (sourceFolder + File.separator + syncPackage).replace(".", File.separator), "SyncAdapter.java"));
        supportingFiles.add(new SupportingFile("sync/syncService.mustache",
                (sourceFolder + File.separator + syncPackage).replace(".", File.separator), "SyncService.java"));
        supportingFiles.add(new SupportingFile("observer/observer.mustache",
                (sourceFolder + File.separator + observerPackage).replace(".", File.separator), "Observer.java"));
        supportingFiles.add(new SupportingFile("content/contract.mustache",
                (sourceFolder + File.separator + contentPackage).replace(".", File.separator), "Contract.java"));
    }

    @Override
    public CodegenOperation fromOperation(String resourcePath, String httpMethod, Operation operation, Map<String, Model> definitions, Swagger swagger) {
        CodegenOperation op = super.fromOperation(resourcePath, httpMethod, operation, definitions, swagger);
        return op;
    }

}