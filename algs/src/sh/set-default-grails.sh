#!/bin/bash

# Change default grails framework used by me

if [ -z $1 ]; then
    echo "Please specify the path to Grails!"
    exit 1;
fi

if [ ! -d $1 ]; then
    echo "Specified Grails path doesn't exist!"
    exit 1;
fi

grailsSymlink=~/IThings/tools/default-grails
if [ -d "$grailsSymlink" ]; then
    rm $grailsSymlink
fi

ln -s $1 $grailsSymlink
echo "Done!"
