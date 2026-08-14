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

        
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        docker run --rm \
                          -v jenkins_home:/var/jenkins_home \
                          -w /var/jenkins_home/workspace/air-quality-api \
                          -e SONAR_HOST_URL="$SONAR_HOST_URL" \
                          -e SONAR_TOKEN="$SONAR_AUTH_TOKEN" \
                          maven:3.9-eclipse-temurin-17 \
                          mvn sonar:sonar \
                          -Dsonar.projectKey=air-quality-api \
                          -Dsonar.projectName="Air Quality API" \
                          -Dsonar.host.url="$SONAR_HOST_URL" \
                          -Dsonar.token="$SONAR_TOKEN"
                    '''
                }
            }
        }

    }
}