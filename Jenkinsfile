pipeline {
  agent any
  
  stages {
    stage('compile') {
      steps {
        sh 'mvn compile'
      }
    }
    stage('test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('archive') {
      steps {
        parallel(
          "Junit": {
            junit '**/target/surefire-reports/*.xml'            
          },
          "Archive": {
            archiveArtifacts(artifacts: '**/target/*.jar', onlyIfSuccessful: true, fingerprint: true)
          }
        )
      }
    }
  }
}