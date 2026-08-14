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
                      -v jenkins_home:/var/jenkins_home \
                      -w /var/jenkins_home/workspace/air-quality-api \
                      maven:3.9-eclipse-temurin-17 \
                      mvn clean verify
                '''
            }
        }

    }
}