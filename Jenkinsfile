pipeline {
    agent any

    environment {
        POSTGRES_HOST = 'danbel.ru'
        POSTGRES_USERNAME = 'postgres'
        POSTGRES_PASSWORD = 'danbelZzAa6190'
        POSTGRES_DATABASE = 'smart-budget'
        POSTGRES_SCHEMA = 'public'
        REDIS_HOST = 'danbel.ru'
        REDIS_PASSWORD = 'ggtt1234redis'
        JWT_ACCESS_SECRET = 'qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w'
        JWT_REFRESH_SECRET = 'zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGyla Z8fDeoONSTJP/wAzKawB8F9rOMNg=='
    }

    stages {
        stage('Create .env File') {
            steps {
                script {
                    def envContent = """
                    POSTGRES_HOST=${env.POSTGRES_HOST}
                    POSTGRES_USERNAME=${env.POSTGRES_USERNAME}
                    POSTGRES_PASSWORD=${env.POSTGRES_PASSWORD}
                    POSTGRES_DATABASE=${env.POSTGRES_DATABASE}
                    POSTGRES_SCHEMA=${env.POSTGRES_SCHEMA}
                    REDIS_HOST=${env.REDIS_HOST}
                    REDIS_PASSWORD=${env.REDIS_PASSWORD}
                    JWT_ACCESS_SECRET=${env.JWT_ACCESS_SECRET}
                    JWT_REFRESH_SECRET=${env.JWT_REFRESH_SECRET}
                    """
                    writeFile file: '.env', text: envContent
                }
            }
        }

        stage('Flyway Migrate') {
            steps {
                sh './gradlew flywayMigrate'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: true
                junit 'build/test-results/test/*.xml'
            }
        }

        stage('Run Application') {
            steps {
                 sh 'screen -S smart-budget java -jar build/libs/smart-budget-0.0.1-SNAPSHOT.jar'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build, tests, and application startup succeeded!'
        }
        failure {
            echo 'Build, tests, or application startup failed!'
        }
    }
}
