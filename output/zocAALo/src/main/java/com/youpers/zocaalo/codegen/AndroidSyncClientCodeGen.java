package com.youpers.zocaalo.codegen;

import io.swagger.codegen.*;
import io.swagger.codegen.languages.AndroidClientCodegen;
import io.swagger.models.properties.*;

import io.swagger.models.Info;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Swagger;

import io.swagger.util.Json;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;
import java.io.File;
import java.io.IOException;

public class AndroidSyncClientCodeGen extends AndroidClientCodegen {

    // source folder where to write the files
    protected String apiVersion = "1.0.0";

    protected String syncPackage = "io.swagger.client.sync";
    protected String observerPackage = "io.swagger.client.observer";
    protected String contentPackage = "io.swagger.client.content";
    protected String authenticationPackage = "io.swagger.client.authentication";

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

        System.out.println("x-android-sync (processOpts)");
    }

    @Override
    public void preprocessSwagger(Swagger swagger) {

        System.out.println("x-android-sync (preprocessSwagger)");

        /**
         * Flatten the x-android-structure in order to allow mustache access to the single elements
         */

        Map<String, Object> ve = swagger.getVendorExtensions();
        ObjectMapper om = Json.mapper();

        if (null != ve) {
            Object o = ve.get("x-android-sync");
            if (null != o) {
                String s = o.toString();
                System.out.println("x-android-sync (complete): " + s);
                try {
                    Sync sync = om.readValue(s, Sync.class);
                    System.out.println("x-android-sync (contentAuthority): " + sync.getContentAuthority());
                    System.out.println("x-android-sync (Author): " + sync.getAuthor());
                    swagger.setVendorExtension("x-android-sync-content-authority", sync.getContentAuthority());
                    swagger.setVendorExtension("x-android-sync-package-name", sync.getPackageName());
                    swagger.setVendorExtension("x-android-sync-db-name", sync.getDbName());
                    swagger.setVendorExtension("x-android-sync-db-version", sync.getDbVersion());
                    swagger.setVendorExtension("x-android-sync-account-name", sync.getAccountName());
                    swagger.setVendorExtension("x-android-sync-account-type", sync.getAccountType());
                    swagger.setVendorExtension("x-android-sync-use-local-base-path", sync.isUseLocalBasePath());
                    swagger.setVendorExtension("x-android-sync-base-path-remote-address", sync.getBasePathRemoteAddress());
                    swagger.setVendorExtension("x-android-sync-base-path-remote-port", sync.getBasePathRemotePort());
                    swagger.setVendorExtension("x-android-sync-base-path-local-port", sync.getBasePathLocalPort());

                    this.setInvokerPackage(sync.getPackageName());
                    additionalProperties.put(CodegenConstants.INVOKER_PACKAGE, invokerPackage);
                    requestPackage = sync.getPackageName() + ".request";
                    authPackage = sync.getPackageName() + ".auth";
                    apiPackage = sync.getPackageName() + ".api";
                    modelPackage = sync.getPackageName() + ".model";
                    additionalProperties.put("modelPackage", sync.getPackageName() + ".model");

                    syncPackage = sync.getPackageName() + ".sync";
                    observerPackage = sync.getPackageName() + ".observer";
                    contentPackage = sync.getPackageName() + ".content";
                    authenticationPackage = sync.getPackageName() + ".authentication";

                    /**
                     * we need first to remove already added supporting files as they have the wrong package
                     * name
                     */

                    List<String> supportingFilesToBeReplaced =
                            Arrays.asList("apiInvoker.mustache",
                                    "jsonUtil.mustache",
                                    "apiException.mustache",
                                    "Pair.mustache",
                                    "request/getrequest.mustache",
                                    "request/postrequest.mustache",
                                    "request/putrequest.mustache",
                                    "request/deleterequest.mustache",
                                    "request/patchrequest.mustache",
                                    "auth/apikeyauth.mustache",
                                    "auth/httpbasicauth.mustache",
                                    "auth/authentication.mustache");

                    for (ListIterator<SupportingFile> iter = supportingFiles.listIterator(); iter.hasNext();) {
                        SupportingFile supportingFile = iter.next();
                        if (supportingFilesToBeReplaced.contains(supportingFile.templateFile)) {
                            iter.remove();
                            System.out.println("x-android-sync (supporting file removed): " + supportingFile.templateFile);
                        }
                    }

                    supportingFiles.add(new SupportingFile("apiInvoker.mustache",
                            (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiInvoker.java"));
                    supportingFiles.add(new SupportingFile("jsonUtil.mustache",
                            (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "JsonUtil.java"));
                    supportingFiles.add(new SupportingFile("apiException.mustache",
                            (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "ApiException.java"));
                    supportingFiles.add(new SupportingFile("Pair.mustache",
                            (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "Pair.java"));
                    supportingFiles.add(new SupportingFile("request/getrequest.mustache",
                            (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "GetRequest.java"));
                    supportingFiles.add(new SupportingFile("request/postrequest.mustache",
                            (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "PostRequest.java"));
                    supportingFiles.add(new SupportingFile("request/putrequest.mustache",
                            (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "PutRequest.java"));
                    supportingFiles.add(new SupportingFile("request/deleterequest.mustache",
                            (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "DeleteRequest.java"));
                    supportingFiles.add(new SupportingFile("request/patchrequest.mustache",
                            (sourceFolder + File.separator + requestPackage).replace(".", File.separator), "PatchRequest.java"));
                    supportingFiles.add(new SupportingFile("auth/apikeyauth.mustache",
                            (sourceFolder + File.separator + authPackage).replace(".", File.separator), "ApiKeyAuth.java"));
                    supportingFiles.add(new SupportingFile("auth/httpbasicauth.mustache",
                            (sourceFolder + File.separator + authPackage).replace(".", File.separator), "HttpBasicAuth.java"));
                    supportingFiles.add(new SupportingFile("auth/authentication.mustache",
                            (sourceFolder + File.separator + authPackage).replace(".", File.separator), "Authentication.java"));

                    supportingFiles.add(new SupportingFile("sync/syncAdapter.mustache",
                            (sourceFolder + File.separator + syncPackage).replace(".", File.separator), "SyncAdapter.java"));
                    supportingFiles.add(new SupportingFile("sync/syncService.mustache",
                            (sourceFolder + File.separator + syncPackage).replace(".", File.separator), "SyncService.java"));
                    supportingFiles.add(new SupportingFile("observer/observer.mustache",
                            (sourceFolder + File.separator + observerPackage).replace(".", File.separator), "Observer.java"));
                    supportingFiles.add(new SupportingFile("observer/changeListener.mustache",
                            (sourceFolder + File.separator + observerPackage).replace(".", File.separator), "ChangeListener.java"));
                    supportingFiles.add(new SupportingFile("content/contract.mustache",
                            (sourceFolder + File.separator + contentPackage).replace(".", File.separator), "Contract.java"));
                    supportingFiles.add(new SupportingFile("content/tableHelper.mustache",
                            (sourceFolder + File.separator + contentPackage).replace(".", File.separator), "TableHelper.java"));
                    supportingFiles.add(new SupportingFile("content/dbHelper.mustache",
                            (sourceFolder + File.separator + contentPackage).replace(".", File.separator), "DBHelper.java"));
                    supportingFiles.add(new SupportingFile("content/provider.mustache",
                            (sourceFolder + File.separator + contentPackage).replace(".", File.separator), "Provider.java"));
                    supportingFiles.add(new SupportingFile("content/dataUtils.mustache",
                            (sourceFolder + File.separator + contentPackage).replace(".", File.separator), "DataUtils.java"));
                    supportingFiles.add(new SupportingFile("authentication/authenticator.mustache",
                            (sourceFolder + File.separator + authenticationPackage).replace(".", File.separator), "Authenticator.java"));
                    supportingFiles.add(new SupportingFile("authentication/authenticatorService.mustache",
                            (sourceFolder + File.separator + authenticationPackage).replace(".", File.separator), "AuthenticatorService.java"));
                    supportingFiles.add(new SupportingFile("shell.mustache",
                            (sourceFolder + File.separator + invokerPackage).replace(".", File.separator), "Shell.java"));

                    supportingFiles.add(new SupportingFile("valuesstrings.mustache", projectFolder + File.separator + "res" + File.separator + "values", "strings.xml"));
                    supportingFiles.add(new SupportingFile("authenticator.mustache", projectFolder + File.separator + "res" + File.separator + "xml", "authenticator.xml"));
                    supportingFiles.add(new SupportingFile("syncadapter.mustache", projectFolder + File.separator + "res" + File.separator + "xml", "syncadapter.xml"));


                } catch (IOException e) {
                }
            }
        }
        super.preprocessSwagger(swagger);
    }

    @Override
    public CodegenModel fromModel(String name, Model model) {
        CodegenModel mo = super.fromModel(name, model);
        return mo;
    }

    @Override
    public CodegenModel fromModel(String name, Model model, Map<String, Model> allDefinitions) {
        CodegenModel mo = super.fromModel(name, model, allDefinitions);
        return mo;
    }

    @Override
    public CodegenProperty fromProperty(String name, Property p) {
        CodegenProperty prop = super.fromProperty(name, p);
        return prop;
    }

    @Override
    public CodegenOperation fromOperation(String resourcePath, String httpMethod, Operation operation, Map<String, Model> definitions) {
        CodegenOperation op = super.fromOperation(resourcePath, httpMethod, operation, definitions);
        return op;
    }

    @Override
    public CodegenOperation fromOperation(String resourcePath, String httpMethod, Operation operation, Map<String, Model> definitions, Swagger swagger) {
        CodegenOperation op = super.fromOperation(resourcePath, httpMethod, operation, definitions, swagger);
        return op;
    }

}