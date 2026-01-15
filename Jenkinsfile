pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "marjahan19/java-springboot-app:latest"
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Cloning source code from GitHub'
                git branch: 'main', url: 'https://github.com/marjahan19/java-application.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project with Maven Wrapper'
                sh '''
                    sed -i 's/\r$//' mvnw
                    chmod +x mvnw
                    ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('Unit Test') {
            steps {
                echo 'Running unit tests'
                sh '''
                    sed -i 's/\r$//' mvnw
                    chmod +x mvnw
                    ./mvnw test
                '''
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image'
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Docker Push') {
            steps {
                echo 'Pushing Docker image to Docker Hub'
                withCredentials([
                    usernamePassword(
                        credentialsId: 'docker-hub',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $DOCKER_IMAGE
                    '''
                }
            }
        }
    }
}
