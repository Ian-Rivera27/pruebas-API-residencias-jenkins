pipeline {
    agent any
    
    stages {
        stage('Clonar el repositorio') {
            steps {
                git 'https://github.com/tearkive/api-residencias'
            }
        }

        stage('Construir Imagen Docker') {
            steps {
                script {
                    dockerImage = docker.build("api-residencias")
                }
            }
        }

        stage('Correr Contenedor para Pruebas') {
            steps {
                script {
                    dockerContainer = dockerImage.run('--name api-residencias-container -p 3001:3001')
                    sleep(time: 5, unit: 'SECONDS')
                }
            }
        }

        stage('Ejecutar Pruebas') {
            steps {
                script {
                    // Módulo de alumnos
                    bat 'curl -f http://localhost:3001/alumnos/getStudents'
                    bat '''
                        curl -X POST http://localhost:3001/alumnos/insertStudent ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240510\\", \\"email\\": \\"20240510@leon.tecnm.mx\\", \\"nombre\\": \\"Víctor Omar\\", \\"apellidos\\": \\"Hernández Gómez\\", \\"carreraId\\": \\"4\\"}"
                    '''
                    bat '''
                        curl -X POST http://localhost:3001/alumnos/insertStudent ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240764\\", \\"email\\": \\"20240764@leon.tecnm.mx\\", \\"nombre\\": \\"Fabian\\", \\"apellidos\\": \\"Pozuelos Rivera\\", \\"carreraId\\": \\"4\\"}"
                    '''
                    bat '''
                        curl -X POST http://localhost:3001/alumnos/insertStudent ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240454\\", \\"email\\": \\"20240454@leon.tecnm.mx\\", \\"nombre\\": \\"Diego Antonio\\", \\"apellidos\\": \\"Becerra Picón\\", \\"carreraId\\": \\"4\\"}"
                    '''
                    bat 'curl -f http://localhost:3001/alumnos/getStudents'
                    bat '''
                        curl -X PUT http://localhost:3001/alumnos/updateStudent/20240764 ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240764\\", \\"email\\": \\"20240764@leon.tecnm.mx\\", \\"nombre\\": \\"Fabian\\", \\"apellidos\\": \\"Pozuelos Rivera\\", \\"carreraId\\": \\"5\\"}"
                    '''
                    bat 'curl -f http://localhost:3001/alumnos/getStudents'

                    // Módulo de maestros
                    bat 'curl -f http://localhost:3001/maestros/getTeachers'
                    bat '''
                        curl -X POST http://localhost:3001/maestros/insertTeacher ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240510\\", \\"email\\": \\"20240510@leon.tecnm.mx\\", \\"nombre\\": \\"Víctor Omar\\", \\"apellidos\\": \\"Hernández Gómez\\", \\"carreraId\\": \\"4\\"}"
                    '''
                    bat '''
                        curl -X POST http://localhost:3001/maestros/insertTeacher ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240764\\", \\"email\\": \\"20240764@leon.tecnm.mx\\", \\"nombre\\": \\"Fabian\\", \\"apellidos\\": \\"Pozuelos Rivera\\", \\"carreraId\\": \\"4\\"}"
                    '''
                    bat 'curl -f http://localhost:3001/maestros/getTeachers'
                    bat '''
                        curl -X PUT http://localhost:3001/maestros/updateTeacher/1 ^
                        -H "Content-Type: application/json" ^
                        -d "{\\"numeroControl\\": \\"20240764\\", \\"email\\": \\"20240764@leon.tecnm.mx\\", \\"nombre\\": \\"Fabian\\", \\"apellidos\\": \\"Pozuelos Rivera\\", \\"carreraId\\": \\"5\\"}"
                    '''
                    bat 'curl -f http://localhost:3001/maestros/getTeachers'
                }
            }
        }
        stage('Stop Container') {
            steps {
                script {
                    bat 'docker stop api-residencias-container || exit 0'
                    bat 'docker rm api-residencias-container || exit 0'
                }
            }
        }
    }

    post {
        success {
            echo 'Las pruebas han sido exitosas.'
        }
        failure {
            echo 'Hubo errores en las pruebas.'
        }
    }
}