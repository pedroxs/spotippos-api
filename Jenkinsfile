#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "./mvnw clean"
    }

    stage('unit tests') {
        try {
            sh "./mvnw test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('stress test') {
        try {
            sh "./mvnw gatling:execute"
        } catch (err) {
            throw err
        } finally {
            gatlingArchive()
        }
    }

    stage('packaging') {
        sh "./mvnw package -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    // Uncomment the following block to add Sonar analysis.
//    stage('quality analysis') {
//        withSonarQubeEnv('Sonar Server') {
//            sh "./mvnw sonar:sonar"
//        }
//    }

}
