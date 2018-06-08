#!/bin/bash

echo "EXECUTANDO"

java -Dpi4j.debug -Dpi4j.linking=dynamic  -cp .:classes:/opt/pi4j/lib/'*' br/com/phdev/sodev/Sodev
