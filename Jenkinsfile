pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    environment {
        IMAGE_NAME = "marjahanapon/java-app"
        IMAGE_TAG = "latest"
        DOCKER_CREDENTIALS = credentials('dockerhub-creds')
    }

    stages {

        stage('Source Code Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/marjahan19/java-application.git'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Testing') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Image Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                sh '''
                echo "$DOCKER_CREDENTIALS_PSW" | docker login -u "$DOCKER_CREDENTIALS_USR" --password-stdin
                docker push $IMAGE_NAME:$IMAGE_TAG
                '''
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed!"
        }
    }
}

