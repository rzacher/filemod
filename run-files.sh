#!/bin/bash
#FILES=/path/to/*
FILES=`find /home/bob/Eng/brandsure/test -name \*.xml `
#FILES=~/Eng/brandsure/10.Commission/*.xml


for f in $FILES
do
  echo "Processing $f file..."
  # take action on each file. $f store current file name
  ./run-filemod.sh $f output
  echo "Processing $f-fixed file..."
  sed -i '/^[[:space:]]*$/d'  $f-fixed
done
