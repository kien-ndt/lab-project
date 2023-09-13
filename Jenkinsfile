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
    stage('Deploy') {
      steps {
          sshagent (credentials: ['ssh_credential_web-admin']) {
            sh 'ssh -o StrictHostKeyChecking=no -p 30022 -l kienndt 127.0.0.1 date'
          }
      }
    }
  }
}