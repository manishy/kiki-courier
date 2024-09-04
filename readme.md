**Supported Java Version:** 1.8

**Gradle Version:** 8.0

## Building the Project

To build the project, use the following command:

`./gradlew build`

## Running the Application
The application takes input through a provided file. To run the application, execute the following commands:

``
./gradlew clean build -x test --no-daemon
java -jar ./build/libs/KikiCourier.jar <absolute_path_to_input_file>
``

Alternatively, you can use the provided shell script to run the application. Execute the following command:

`/bin/bash run.sh`