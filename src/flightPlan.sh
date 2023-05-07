#!/bin/bash

javac src/*.java
java -cp src flightPlan $1 $2 $3
