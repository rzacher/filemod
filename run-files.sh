#!/bin/bash
#FILES=/path/to/*
#FILES=`find /home/bob/Eng/brandsure/test -name \*.xml `
FILES=`find /home/bob/Eng/brandsure/test-files -name \*\*\1\*.xml `
# For testing one file at a time
#FILES=`find /home/bob/Eng/brandsure/test-files -name \*\*\10-10008.xml `

for f in $FILES
do
  echo "Processing $f file..."
  # take action on each file. $f store current file name
  ./run-filemod.sh $f output
  echo "Processing $f-fixed file..."
  sed -i '/^[[:space:]]*$/d'  $f-fixed
done
