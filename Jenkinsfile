pipeline {
    agent any

    environment {
        POSTGRES_HOST = ''
        POSTGRES_USERNAME = ''
        POSTGRES_PASSWORD = ''
        POSTGRES_DATABASE = ''
        POSTGRES_SCHEMA = ''
        REDIS_HOST = ''
        REDIS_PASSWORD = ''
        JWT_ACCESS_SECRET = ''
        JWT_REFRESH_SECRET = ''
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
                sh 'nohup java -jar build/libs/smart-budget-0.0.1-SNAPSHOT.jar &'
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
