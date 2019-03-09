#!/bin/sh

export SPARK_HOME=your/path/to/spark/direcotry

if [ -z "$1" ]
  then
    echo "No argument supplied"
fi

while getopts c:j:e:f:d option
do
 case "${option}"
 in
 c) class=${OPTARG};;
 j) jar=${OPTARG};;
 e) executors=${OPTARG};;
 f) file=${OPTARG};;
 d) deploy=${OPTARG};;
 esac
done

if [ ! -z "$file" ]; then
  ${SPARK_HOME}/bin/spark-submit --class ${class} --deploy-mode ${deploy} --num-executors ${executors} ${jar} ${file}
else
  ${SPARK_HOME}/bin/spark-submit --class ${class} --deploy-mode ${deploy} --num-executors ${executors} ${jar}
fi

