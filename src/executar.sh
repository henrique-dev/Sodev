#!/bin/bash

echo "E X E C U T A N D O"

java -Dpi4j.debug -Dpi4j.linking=dynamic -cp .:classes:/opt/pi4j/lib/'*' br/com/phdev/sodev/Sodev