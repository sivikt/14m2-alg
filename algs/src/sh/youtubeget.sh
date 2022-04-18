#!/bin/bash

if [ -z "$1" ]; then
   echo "Specify uri"
   exit 1
fi

exec youtube-dl --max-quality 22 $1

case "$?" in
   0)
      echo SUCCESS
     ;;
   1)
      echo FAILED
      exit 1
      ;;
esac
