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
              withCredentials([
                string(
                    credentialsId: 'sonarqube-token',
                    variable: 'SONAR_TOKEN'
                )
            ]) {
                sh '''
                    docker run --rm \
                      --network air-quality-network \
                      -v jenkins_home:/var/jenkins_home \
                      -w /var/jenkins_home/workspace/air-quality-api \
                      -e SONAR_HOST_URL="$SONAR_HOST_URL" \
                      -e SONAR_TOKEN="$SONAR_TOKEN" \
                      maven:3.9-eclipse-temurin-17 \
                      mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                      -Dsonar.projectKey=air-quality-api \
                      -Dsonar.projectName="Air Quality API" \
                      -Dsonar.host.url="$SONAR_HOST_URL" \
                      -Dsonar.token="$SONAR_TOKEN"
                '''
            }
        }
    }
}
        stage('Quality Gate') {
    steps {
        timeout(time: 5, unit: 'MINUTES') {
            waitForQualityGate abortPipeline: true
        }
    }
}
        stage('Docker Build') {
    steps {
        script {
            def imageTag = "${env.BUILD_NUMBER}-${env.GIT_COMMIT.take(7)}"

            sh """
                docker build \
                  -t air-quality-api:${imageTag} \
                  .
            """
        }
    }
}

        stage('Trivy Scan') {
    steps {
        sh '''
            docker run --rm \
              -v /var/run/docker.sock:/var/run/docker.sock \
              aquasec/trivy:latest \
              image \
              --severity HIGH,CRITICAL \
              --exit-code 1 \
              ${IMAGE_NAME}:${IMAGE_TAG}
        '''
    }
}

    }
}
