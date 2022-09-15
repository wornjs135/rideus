pipeline {
    agent none
    options { skipDefaultCheckout(true) }
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'node:16'
                }
            }
            options { skipDefaultCheckout(false) }
            steps {
              sh 'cd frontend'
                sh 'npm install'
                sh 'CI=false npm run build'
            }
        }
        stage('Docker build') {
            agent any
            steps {
                sh 'docker build -t nginx-react-image:latest .'
            }
        }
        stage('Docker run') {
            agent any
            steps {
                // sh 'docker ps -f name=nginx-react-container -q | xargs --no-run-if-empty docker container stop'
                // sh 'docker container ls -a -fname=nginx-react-container -q | xargs -r docker container rm'
                // sh 'docker rmi $(docker images -f "dangling=true" -q)'
                sh 'docker run --name nginx_react -d -p 3000:80 nginx-react:0.1'
            }
        }
    }
}