pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Maven Test') {
    steps {
        sh '''
            docker run --rm \
              -v "$WORKSPACE:/workspace" \
              -w /workspace \
              maven:3.9-eclipse-temurin-17 \
              mvn clean verify
        '''
           }
        }

    }
}