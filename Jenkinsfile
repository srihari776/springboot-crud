groovy
pipeline {
    agent any
    tools {
        maven 'Maven-3.9'
    }
    stages {
        stage('Checkout') {
            steps {
                echo 'Pulling code from GitHub...'
                git branch: 'master',
                    url: 'https://github.com/srihari776/springboot-crud.git'
                echo 'Code pulled successfully!'
            }
        }
        stage('Build') {
            steps {
                echo 'Building WAR file...'
                bat 'mvn clean package -DskipTests'
                echo 'WAR built successfully!'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying to Tomcat...'
                // Stop Tomcat
                bat 'C:\\software\\apache-tomcat-10.1.54\\bin\\shutdown.bat'
                // Wait for Tomcat to stop
                bat 'timeout /t 5'
                // Delete old WAR and deployed folder
                bat '''
                    if exist C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT.war (
                        del C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT.war
                    )
                    if exist C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT (
                        rmdir /s /q C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT
                    )
                '''
                // Copy new WAR to Tomcat webapps
                bat '''
                    copy /Y target\\SpringBoot_CRUD-0.0.1-SNAPSHOT.war C:\\software\\apache-tomcat-10.1.54\\webapps\\
                '''
                // Start Tomcat
                bat 'C:\\software\\apache-tomcat-10.1.54\\bin\\startup.bat'
                echo 'Deployed to Tomcat successfully!'
            }
        }
    }
    post {
        success {
            echo '========================================='
            echo 'BUILD AND DEPLOY SUCCESS!'
            echo 'App running on http://localhost:2026/SpringBoot_CRUD-0.0.1-SNAPSHOT'
            echo '========================================='
        }
        failure {
            echo '========================================='
            echo 'BUILD FAILED!'
            echo 'Check console output for errors'
            echo '========================================='
        }
    }
}