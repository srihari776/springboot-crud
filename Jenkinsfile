groovy
pipeline {
    agent any
    tools {
        maven 'Maven-3.9'
    }
    stages {
        // ==================== STAGE 1 ====================
        stage('Checkout') {
            steps {
                echo 'Pulling code from GitHub...'
                git branch: 'master',
                    url: 'https://github.com/srihari776/springboot-crud.git'
                echo 'Code pulled successfully!'
            }
        }
        // ==================== STAGE 2 ====================
        stage('Build') {
            steps {
                echo 'Building the project...'
                bat 'mvn clean package -DskipTests'
                echo 'Build successful!'
            }
        }
        // ==================== STAGE 3 ====================
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                bat '''
                    :: Kill existing app on port 9090
                    for /f "tokens=5" %%a in ('netstat -aon ^| find ":9090" ^| find "LISTENING"') do (
                        taskkill /f /pid %%a
                    )
                    echo old instance killed
                '''
                bat '''
                    start /min java -jar target\\SpringBoot_CRUD-0.0.1-SNAPSHOT.jar
                '''
                echo 'Application deployed!'
            }
        }
    }
    // ==================== POST BUILD ====================
    post {
        success {
            echo '========================================='
            echo 'BUILD AND DEPLOY SUCCESS! ✅'
            echo 'App running on http://localhost:9090'
            echo '========================================='
        }
        failure {
            echo '========================================='
            echo 'BUILD FAILED! ❌'
            echo 'Check console output for errors'
            echo '========================================='
        }
    }
}