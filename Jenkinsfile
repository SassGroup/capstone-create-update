pipeline {
    agent any
    environment {
        //imagename = "vincentstrife/capstone-create-update"
        //registryCredential = "dockerVincentStrifeCred"
        dockerImage = ''
        //devSystemAddress="devops@192.168.1.45"
        statusLoop=''
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -DskipTests clean install'
            }
        }
        stage('Build Docker Image'){
            steps {
                script{
                    dockerImage = docker.build("${env.imagename}:v${env.BUILD_NUMBER}")
                }
                // sh """docker build . -t vincentstrife/capstone-create-update:v${env.BUILD_NUMBER}"""
            }
        }
        stage('Push Built Image to Docker Hub'){
            steps{
                script{
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                        dockerImage.push('latest')
                    }
                }
            }
        }
        stage('Remove Unused docker image') {
            steps{
                sh "docker rmi $imagename:v$BUILD_NUMBER"
                sh "docker rmi $imagename:latest"
            }
        }
        stage('SSH create tmp folder at ~ location'){
            steps{
                sh 'ssh -o StrictHostKeyChecking=no $devSystemAddress "mkdir -p ~/tmp"'
            }
        }
        stage('SSH copy deployment files to dev server'){
            parallel {
                stage("scp deployment yaml") {
                    steps{
                        dir("Deployment") {
                            sh "sed -i 's/{version}/$BUILD_NUMBER/' ./capstone-create-update-deployment.yaml"
                            sh 'scp ./capstone-create-update-deployment.yaml $devSystemAddress:~/tmp'
                        }
                    }
                }
                stage("scp service yaml") {
                    steps{
                        dir("Deployment") {
                            sh 'scp ./capstone-create-update-service.yaml $devSystemAddress:~/tmp'
                        }
                    }
                }
            }
        }
        stage('SSH kubectl tmp deploy files dev server'){
            parallel {
                stage("kubectl apply deployment") {
                    steps{
                        sh 'ssh -o StrictHostKeyChecking=no $devSystemAddress "kubectl apply -f ~/tmp/capstone-create-update-deployment.yaml"'
                    }
                }
                stage("kubectl apply service") {
                    steps{
                        sh 'ssh -o StrictHostKeyChecking=no $devSystemAddress "kubectl apply -f ~/tmp/capstone-create-update-service.yaml"'
                    }
                }
            }
        }
        stage('Kubernetes Pods status check') {
            steps{
                script{
                    statusLoop=["ViewStatus":true,"Rollback":false]
                    while(statusLoop["ViewStatus"]){
                        sh 'ssh -o StrictHostKeyChecking=no $devSystemAddress "kubectl get pods -o wide --all-namespaces"'
                        statusLoop=input(message: 'Check radio button and perform the following action', ok: 'Submit',
                            parameters: [
                                booleanParam(name: 'ViewStatus', defaultValue: false, description: 'Check the radio button to once again view the pods status'),
                                booleanParam(name: 'Rollback', defaultValue: false, description: 'Check the radio button to rollback to previous deployment')
                            ]
                        )
                    }
                }
            }
        }
        stage('Kube Deployment Rollback'){
            when{
                expression {statusLoop["Rollback"]==true}
            }
            steps{
                echo "Need to check for the rollback command"
            }
        }
    }
}