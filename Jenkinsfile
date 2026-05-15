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
                bat '''
                    set CATALINA_HOME=C:\\software\\apache-tomcat-10.1.54
                    set JAVA_HOME=C:\\Program Files\\Java\\jdk-21.0.10
                    call C:\\software\\apache-tomcat-10.1.54\\bin\\shutdown.bat
                '''
                // Wait 5 seconds
                bat 'ping -n 6 127.0.0.1 > nul'
                // Delete old WAR and folder
                bat '''
                    if exist C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT.war (
                        del C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT.war
                    )
                    if exist C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT (
                        rmdir /s /q C:\\software\\apache-tomcat-10.1.54\\webapps\\SpringBoot_CRUD-0.0.1-SNAPSHOT
                    )
                '''
                // Copy new WAR
                bat '''
                    copy /Y target\\SpringBoot_CRUD-0.0.1-SNAPSHOT.war C:\\software\\apache-tomcat-10.1.54\\webapps\\
                '''
                // Start Tomcat
                bat '''
                    set CATALINA_HOME=C:\\software\\apache-tomcat-10.1.54
                    set JAVA_HOME=C:\\Program Files\\Java\\jdk-21.0.10
                    call C:\\software\\apache-tomcat-10.1.54\\bin\\startup.bat
                '''
                echo 'Deployed successfully!'
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