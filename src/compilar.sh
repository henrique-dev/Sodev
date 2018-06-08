#!/bin/bash

echo "COMPILANDO"

javac -cp .:classes:/opt/pi4j/lib/'*' br/com/phdev/sodev/*.java br/com/phdev/sodev/driver/*.java br/com/phdev/sodev/connection/*.java 
