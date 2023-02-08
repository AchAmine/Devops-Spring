pipeline {
    agent any
    tools {
        maven 'Maven 3.8.5'
    }
    environment { 
        NEXUS_CREDENTIALS=credentials('nexus-credentials')


    }
    stages {
        stage('Git fetch') {
            steps {
                git branch: 'main', 
                url: 'https://github.com/AchAmine/Devops-Spring',
                credentialsId: 'git-credentials'
            }
        }

        stage('BUILD') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
            
        }

		stage('Build App Image') {
            steps {
				sh 'docker build -t 10.0.0.4:5000/repository/spring-repo .'
				sh 'docker tag 10.0.0.4:5000/repository/spring-repo 10.0.0.4:5000/repository/spring-repo:$BUILD_NUMBER'
			//	sh 'docker tag $BUILD_NUMBER 10.0.0.4:5000/repository/spring-repo'


			}
        }
       	stage('Login to Nexus Registry ') {
            steps {
				sh 'docker login -u $NEXUS_CREDENTIALS_USR -p $NEXUS_CREDENTIALS_PSW 10.0.0.4:5000'
			}
        }
        
        stage('Push Image to Nexus Registry ') {
            steps {
                sh 'docker push 10.0.0.4:5000/repository/spring-repo:$BUILD_NUMBER'
				sh 'docker push 10.0.0.4:5000/repository/spring-repo:latest'

			}
        }
        
        stage('Remove Unused docker image') {
          steps{
            sh 'docker rmi 10.0.0.4:5000/repository/spring-repo:$BUILD_NUMBER'
            sh 'docker rmi 10.0.0.4:5000/repository/spring-repo:latest'
          }
        }
        
        stage('Clean'){
            steps{
             cleanWs()
            }
        } 
     	 
    }
    
}
