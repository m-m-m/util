pipeline {
  agent any
  
  stages {
    stage('build') {
      steps {
        sh 'mvn clean install'
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