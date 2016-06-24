#!/usr/bin/env bash
java -cp output/zocAALo/target/AndroidSyncClient-swagger-codegen-1.0.0.jar:modules/swagger-codegen-cli/target/swagger-codegen-cli.jar io.swagger.codegen.Codegen generate \
  -l AndroidSyncClient \
  -c output/zocAALo/bin/android-loc8r-volley.json \
  -i /Volumes/SaSSDT1/Development/zocAALo/loc8r/app_api/doc/swagger.json \
  -o /Volumes/SaSSDT1/Development/zocAALo/Android/Swagger/loc8r-api \
  -t output/zocAALo/src/main/resources/AndroidSyncClientCodegen/volley