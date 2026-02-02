pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build, Test & Generate Report') {
            steps {
                sh './gradlew clean test generateCucumberReport'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'build/cucumber-report/**/*.html, build/cucumber-report/json/*.json', allowEmptyArchive: true
        }
    }
}