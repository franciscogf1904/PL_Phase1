#!/bin/sh

rm *.class
javacc ParserL1PP.jj
javac *.java

