#!/bin/sh

SCRIPT="$0"

while [ -h "$SCRIPT" ] ; do
  ls=`ls -ld "$SCRIPT"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    SCRIPT="$link"
  else
    SCRIPT=`dirname "$SCRIPT"`/"$link"
  fi
done

if [ ! -d "${APP_DIR}" ]; then
  APP_DIR=`dirname "$SCRIPT"`/..
  APP_DIR=`cd "${APP_DIR}"; pwd`
fi

executable="./modules/swagger-codegen-cli/target/swagger-codegen-cli.jar"

if [ ! -f "$executable" ]
then
  mvn clean package
fi

# if you've executed sbt assembly previously it will use that instead.
export JAVA_OPTS="${JAVA_OPTS} -XX:MaxPermSize=256M -Xmx1024M -DloggerPath=conf/log4j.properties"
ags="$@ generate -i /Volumes/SaSSDT1/Development/zocAALo/loc8r/app_api/doc/swagger.json -l android -c bin/android-loc8r-volley.json -o /Volumes/SaSSDT1/Development/zocAALo/Android/Swagger/loc8r-api -t /Volumes/SaSSDT1/Development/zocAALo/swagger-codegen/modules/swagger-codegen/src/main/resources/android/libraries/volley"

java $JAVA_OPTS -jar $executable $ags
