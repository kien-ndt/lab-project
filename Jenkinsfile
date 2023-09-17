def removeBE(USERNAME, PASSWORD, HOST) {
  sh "sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${USERNAME}@${HOST} kill -9 \$(cat web-admin.pid || true) || true"
  sh "sshpass -p ${PASSWORD} ssh -o StrictHostKeyChecking=no ${USERNAME}@${HOST} rm web-admin.pid web-admin.jar || true"
  // '''
  //   sshpass -p "${PASSWORD}" ssh -o StrictHostKeyChecking=no $USERNAME@$HOST "kill -9 `cat web-admin.pid; rm web-admin.pid web-admin.jar`"

  // '''
}


pipeline {
  agent any
  tools {
    maven 'maven-3.9.3'
  }
  environment {
    SONAR_SCANNER = tool "sonarqube_scanner 5.0.1.3006"
  }
  stages {
    stage('Build backend') {

      steps {
        sh '''
          cd lab-project
          mvn clean
        '''
        sh '''
          cd lab-project
          mvn install
        '''
      }
    }
    // stage('Sonar verify') {
    //   steps {

    //     sh '''
    //       cd lab-project
    //       echo "sonar.projectKey=lab-project" >> sonar-project.properties
    //       echo "sonar.language=java" >> sonar-project.properties
    //       echo "sonar.modules=common,security,web-admin,web-user" >> sonar-project.properties
    //       echo "sonar.projectVersion=${BUILD_NUMBER}" >> sonar-project.properties

    //       echo "common.sonar.sources=src/main/java" >> sonar-project.properties
    //       echo "common.sonar.java.binaries=target/classes" >> sonar-project.properties

    //       echo "security.sonar.sources=src/main/java" >> sonar-project.properties
    //       echo "security.sonar.java.binaries=target/classes" >> sonar-project.properties

    //       echo "web-admin.sonar.sources=src/main/java" >> sonar-project.properties
    //       echo "web-admin.sonar.java.binaries=target/classes" >> sonar-project.properties

    //       echo "web-user.sonar.sources=src/main/java" >> sonar-project.properties
    //       echo "web-user.sonar.java.binaries=target/classes" >> sonar-project.properties
                   
    //     '''

    //     withSonarQubeEnv(installationName: 'sonarqube_server', credentialsId: 'sonarqube_token') {
    //         sh '''
    //             cd lab-project
    //             ${SONAR_SCANNER}/bin/sonar-scanner
    //         '''
    //     }
    //   }
    // }    
    stage('Package jar') {
      steps {
        sh '''
          cd lab-project
          mvn package spring-boot:repackage -pl web-admin
          mvn package spring-boot:repackage -pl web-user
        '''
      }
    }
    stage('Deploy') {
      environment {
        SSH_ACCOUNT = credentials('server_username_password')
        HOST = credentials('server_ip')
      }
      steps {
        sh '''
          cd lab-project
          sshpass -p "${SSH_ACCOUNT_PSW}" scp web-admin/target/web-admin.jar $SSH_ACCOUNT_USR@$HOST:/home/kienndt
          sshpass -p "${SSH_ACCOUNT_PSW}" ssh -o StrictHostKeyChecking=no $SSH_ACCOUNT_USR@$HOST "java -jar web-admin.jar > /dev/null && echo $! > web-admin.pid"
        '''
      }
      post {
        failure {
            removeBE('${SSH_ACCOUNT_USR}', '${SSH_ACCOUNT_PSW}', '${HOST}')
        }
      }
    }
  }
}