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
            bat '''
                curl -X PUT http://localhost:3001/empresa/updateEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"id\\": 2, \\"nombre\\": \\"Tech Solutions Monterrey\\", \\"razonSocial\\": \\"TSM Consulting Group S.A. de C.V.\\", \\"domicilio\\": \\"Paseo de los Leones 1200, Cumbres 4to Sector, Monterrey, NL, 64610\\", \\"giro\\": \\"Consultoría en TI\\"}"

                curl -X PUT http://localhost:3001/empresa/updateEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"id\\": 3, \\"nombre\\": \\"Innovación Industrial\\", \\"razonSocial\\": \\"Innovación y Desarrollo Industrial S.A. de C.V.\\", \\"domicilio\\": \\"Av. Insurgentes 2450, Cuauhtémoc, CDMX, 03100\\", \\"giro\\": \\"Manufactura\\"}"
                
                curl -X POST http://localhost:3001/empresa/createEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Tech Innovators\\", \\"razonSocial\\": \\"Tech Innovators S.A. de C.V.\\", \\"domicilio\\": \\"Calle Industria 123, Parque Tecnológico, 45678\\", \\"giro\\": \\"Tecnología\\"}"

                curl -X POST http://localhost:3001/empresa/createEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Constructora Del Sol\\", \\"razonSocial\\": \\"Constructora y Desarrollos del Sol S.A.\\", \\"domicilio\\": \\"Av. Las Torres 5432, Zona Industrial, 67890\\", \\"giro\\": \\"Construcción\\"}"

                curl -X POST http://localhost:3001/empresa/createEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Financiera Capital\\", \\"razonSocial\\": \\"Capital Financiero de México S.A.\\", \\"domicilio\\": \\"Paseo de la Reforma 333, Centro, 06600\\", \\"giro\\": \\"Financiero\\"}"
                
                curl -X POST http://localhost:3001/empresa/createEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"GlobalSoft Consulting\\", \\"razonSocial\\": \\"GlobalSoft Solutions S.A. de C.V.\\", \\"domicilio\\": \\"Blvd. Miguel Alemán 2201, Torreón, Coahuila, 27000\\", \\"giro\\": \\"Servicios de TI\\"}"

                curl -X POST http://localhost:3001/empresa/createEmpresa ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Centro de Innovación Automotriz\\", \\"razonSocial\\": \\"Automotriz Innovación de México S.A. de C.V.\\", \\"domicilio\\": \\"Av. Universidad 500, C.P. 76000, Querétaro, Qro.\\", \\"giro\\": \\"Automotriz\\"}"
                
                curl -f http://localhost:3001/empresa/getEmpresa ^
                
                // AsesoresExternos
                curl -X POST http://localhost:3001/empresa/createAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Julian de Obregon\\", \\"email\\": \\"julianobregon@gmail.com\\", \\"telefono\\": \\"477-108-2025\\", \\"puesto\\": \\"Director de Proyectos\\", \\"idEmpresa\\": 4}"

                curl -X POST http://localhost:3001/empresa/createAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Mariana Ramírez\\", \\"email\\": \\"marianaramirez@techinnovators.com\\", \\"telefono\\": \\"555-789-1234\\", \\"puesto\\": \\"Gerente de Desarrollo\\", \\"idEmpresa\\": 1}"

                curl -X POST http://localhost:3001/empresa/createAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Luis Mendoza\\", \\"email\\": \\"lmendoza@consultoriadelsol.com\\", \\"telefono\\": \\"871-123-4567\\", \\"puesto\\": \\"Analista de Proyectos\\", \\"idEmpresa\\": 2}"

                curl -X POST http://localhost:3001/empresa/createAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Ana López\\", \\"email\\": \\"ana.lopez@capitalfinanciero.com\\", \\"telefono\\": \\"444-567-8901\\", \\"puesto\\": \\"Asesora Financiera\\", \\"idEmpresa\\": 3}"

                curl -X POST http://localhost:3001/empresa/createAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"nombre\\": \\"Carlos Torres\\", \\"email\\": \\"carlos.torres@globalsoft.com\\", \\"telefono\\": \\"229-555-7788\\", \\"puesto\\": \\"Ingeniero de Software\\", \\"idEmpresa\\": 4}"

                curl -X PUT http://localhost:3001/empresa/updateAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"id\\": 1, \\"nombre\\": \\"Julian de Obregon\\", \\"email\\": \\"julianobregon@gmail.com\\", \\"telefono\\": \\"477-999-9999\\", \\"puesto\\": \\"Director de Proyectos\\", \\"idEmpresa\\": 4}"

                curl -X PUT http://localhost:3001/empresa/updateAsesorExterno ^
                -H "Content-Type: application/json" ^
                -d "{\\"id\\": 2, \\"nombre\\": \\"Mariana Ramírez\\", \\"email\\": \\"m.ramirez@techinnovators.com\\", \\"telefono\\": \\"555-456-7890\\", \\"puesto\\": \\"Gerente de Desarrollo\\", \\"idEmpresa\\": 1}"

                curl -f http://localhost:3001/empresa/getAsesorExterno ^

            '''
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