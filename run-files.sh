#!/bin/bash
#FILES=/path/to/*
FILES=~/Eng/brandsure/10.Commission/*.xml

for f in $FILES
do
  echo "Processing $f file..."
  # take action on each file. $f store current file name
  f-fixed=$f+"-fixed"
  echo "Processing $f-fixed file..."
  #sed -i '/^[[:space:]]*$/d'
done
