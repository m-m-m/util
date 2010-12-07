#!/usr/bin/env bash
cd `dirname $0`
. configuration.inc
cd ..

CLASSPATH=classes
for FILE in lib/*.jar
do
  CLASSPATH=$CLASSPATH:$FILE
done

JAVA_CMD=java
if [ -n "$JAVA_HOME" ]
then
  JAVA_CMD=$JAVA_HOME/bin/java
fi

$JAVA_CMD $JAVA_OPTS -cp $CLASSPATH net.sf.mmm.search.indexer.impl.SearchIndexerMain $@