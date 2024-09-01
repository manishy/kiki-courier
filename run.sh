#!/bin/bash
./gradlew clean build -x test --no-daemon
echo ""
echo ""
echo "SAMPLE INPUT-OUTPUT 1"
echo ""
java -jar ./build/libs/KikiCourier.jar ./sample_input/input1.txt
