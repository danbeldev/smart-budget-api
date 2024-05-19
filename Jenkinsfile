pipeline {
    agent {
        docker {
            image 'gradle:6.8.3-jdk17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {

        stage('Create .env File') {
            steps {
                script {
                    def envContent = """
                    POSTGRES_HOST=danbel.ru
                    POSTGRES_USERNAME=postgres
                    POSTGRES_PASSWORD=danbelZzAa6190
                    POSTGRES_DATABASE=smart-budget
                    POSTGRES_SCHEMA=public
                    REDIS_HOST=danbel.ru
                    REDIS_PASSWORD=ggtt1234redis
                    JWT_ACCESS_SECRET=qBTmv4oXFFR2GwjexDJ4t6fsIUIUhhXqlktXjXdkcyygs8nPVEwMfo29VDRRepYDVV5IkIxBMzr7OEHXEHd37w
                    JWT_REFRESH_SECRET='zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGyla Z8fDeoONSTJP/wAzKawB8F9rOMNg=='
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
