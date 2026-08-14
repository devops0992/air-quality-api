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
                script {
                    docker.image('maven:3.9-eclipse-temurin-17').inside {
                        sh 'mvn clean verify'
                    }
                }
            }
        }

    }
}