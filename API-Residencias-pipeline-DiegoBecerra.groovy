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
                    try {
                        // Residences Docs
                        bat 'curl -f http://localhost:3001/residenciasDocs/residencesDocs'
                        bat '''
                            curl -X POST http://localhost:3001/residenciasDocs/residencesDocs ^
                            -F "file=@D:/Documentos/DocumentosPrueba/CartadeAceptacion(20240475).txt" ^
                            -F "comentarios=" ^
                            -F "documentoId=1" ^
                            -F "residenciaId=2" ^
                            -F "statusDocumento=1"
                        '''
                        bat '''
                            curl -X PUT http://localhost:3001/residenciasDocs/residencesDocs/2 ^
                            -F "file=@D:/Documentos/DocumentosPrueba/Anteproyecto(20240454).txt" ^
                            -F "comentarios=" ^
                            -F "documentoId=1" ^
                            -F "residenciaId=1" ^
                            -F "statusDocumento=1"
                        '''
                        bat 'curl -f http://localhost:3001/residenciasDocs/residencesDocs'

                        // Residences
                        bat 'curl -f http://localhost:3001/residencias/residences'
                        bat '''
                            curl -X POST http://localhost:3001/residencias/residences ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"alumnoNumControl\\": \\"20240764\\", \\"asesorRevisorId\\": null, \\"empresaId\\": 4, \\"periodoId\\": 2, \\"proyectoId\\": 3, \\"statusResidencia\\": \\"En Proceso\\"}"
                        '''
                        bat '''
                            curl -X PUT http://localhost:3001/residencias/residences/1 ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"alumnoNumControl\\": \\"20240454\\", \\"asesorRevisorId\\": null, \\"empresaId\\": 1, \\"periodoId\\": 1, \\"proyectoId\\": 1, \\"statusResidencia\\": \\"En Revision\\"}"
                        '''
                        bat 'curl -f http://localhost:3001/residencias/residences'

                        // Projects
                        bat 'curl -f http://localhost:3001/proyectos/projects'
                        bat '''
                            curl -X POST http://localhost:3001/proyectos/projects ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"titulo\\": \\"Red Social para Profesionales\\", \\"descripcion\\": \\"Una plataforma que permite a los profesionales conectarse, compartir proyectos y buscar oportunidades laborales.\\"}"
                        '''
                        bat '''
                            curl -X PUT http://localhost:3001/proyectos/projects/3 ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"titulo\\": \\"Gestor de Tareas\\", \\"descripcion\\": \\"Una aplicación web que permite a los usuarios crear, asignar y rastrear tareas.\\"}"
                        '''
                        bat 'curl -f http://localhost:3001/proyectos/projects'

                        // Documents
                        bat 'curl -f http://localhost:3001/documentos/documents'
                        bat '''
                            curl -X POST http://localhost:3001/documentos/documents ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"nombre\\": \\"Carta de Presentación\\"}"
                        '''
                        bat '''
                            curl -X PUT http://localhost:3001/documentos/documents/3 ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"nombre\\": \\"Carta de Compromiso\\"}"
                        '''
                        bat 'curl -f http://localhost:3001/documentos/documents'

                        // Periods
                        bat 'curl -f http://localhost:3001/periodos/periods'
                        bat '''
                            curl -X POST http://localhost:3001/periodos/periods ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"periodo\\": \\"AD\\", \\"año\\": 2025}"
                        '''
                        bat '''
                            curl -X PUT http://localhost:3001/periodos/periods/2 ^
                            -H "Content-Type: application/json" ^
                            -d "{\\"periodo\\": \\"EM\\", \\"año\\": 2023}"
                        '''
                        bat 'curl -f http://localhost:3001/periodos/periods'

                    } catch (err) {
                        error 'Las pruebas han fallado: ${err}'
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                bat 'docker stop api-residencias-container || exit 0'
                bat 'docker rm api-residencias-container || exit 0'
            }
        }
        success {
            echo 'Las pruebas han sido exitosas.'
        }
        failure {
            echo 'Hubo errores en las pruebas.'
        }
    }
}
