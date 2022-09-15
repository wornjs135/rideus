pipeline{
    agent any
    environment {
       BACK_CONTAINER_NAME="rideus_back_container"
       BACK_NAME = "rideus_back"

       FRONT_CONTAINER_NAME="rideus_front_container"
       FRONT_NAME = "rideus_front"
    }
    stages {
        stage('Clean'){
            steps{
                script {
                    try{
                        sh "docker stop ${BACK_CONTAINER_NAME}"
                        sh "docker stop ${FRONT_CONTAINER_NAME}"
                        sleep 1
                        sh "docker rm ${BACK_CONTAINER_NAME}"
                        sh "docker rm ${FRONT_CONTAINER_NAME}"
                    }catch(e){
                        sh 'exit 0'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                script{
                    sh "docker build -t ${BACK_NAME} ./Back_end/reniors/."
                    sh "docker build -t ${FRONT_NAME} ./Front_end/reniors/."
                }
            }
        }
        stage('Deploy'){
            steps {
                sh "docker run -d --name=${BACK_CONTAINER_NAME} -p 8080:8080 ${BACK_NAME}"
                sh "docker run -d --name=${FRONT_CONTAINER_NAME} -p 8081:80 ${FRONT_NAME}"

                sh "docker image prune"
            }
        }
    }
}