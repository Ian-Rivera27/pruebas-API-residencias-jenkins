Lanzada por el usuario admin
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins  in C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Clonar el repositorio)
[Pipeline] git
The recommended git tool is: NONE
No credentials specified
 > git.exe rev-parse --resolve-git-dir C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba\.git # timeout=10
Fetching changes from the remote Git repository
 > git.exe config remote.origin.url https://github.com/tearkive/api-residencias # timeout=10
Fetching upstream changes from https://github.com/tearkive/api-residencias
 > git.exe --version # timeout=10
 > git --version # 'git version 2.46.0.windows.1'
 > git.exe fetch --tags --force --progress -- https://github.com/tearkive/api-residencias +refs/heads/*:refs/remotes/origin/* # timeout=10
 > git.exe rev-parse "refs/remotes/origin/master^{commit}" # timeout=10
Checking out Revision 6de09d5dad143404063725d6fc2f089f9d85c5a4 (refs/remotes/origin/master)
 > git.exe config core.sparsecheckout # timeout=10
 > git.exe checkout -f 6de09d5dad143404063725d6fc2f089f9d85c5a4 # timeout=10
 > git.exe branch -a -v --no-abbrev # timeout=10
 > git.exe branch -D master # timeout=10
 > git.exe checkout -b master 6de09d5dad143404063725d6fc2f089f9d85c5a4 # timeout=10
Commit message: "Modificación de putCareer, había una coma de más"
 > git.exe rev-list --no-walk 8ad622a93b97880b6a436c6528e40c83e73b10b7 # timeout=10
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Construir Imagen Docker)
[Pipeline] script
[Pipeline] {
[Pipeline] isUnix
[Pipeline] withEnv
[Pipeline] {
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>docker build -t "api-residencias" . 
#0 building with "default" instance using docker driver

#1 [internal] load build definition from Dockerfile
#1 transferring dockerfile: 179B 0.0s done
#1 DONE 0.0s

#2 [internal] load metadata for docker.io/library/node:latest
#2 DONE 2.1s

#3 [internal] load .dockerignore
#3 transferring context: 67B 0.0s done
#3 DONE 0.0s

#4 [internal] load build context
#4 transferring context: 19.32kB 0.1s done
#4 DONE 0.1s

#5 [1/6] FROM docker.io/library/node:latest@sha256:840dad0077213cadd2d734d542ae11cd0f648200be29504eb1b6e2c995d2b75a
#5 resolve docker.io/library/node:latest@sha256:840dad0077213cadd2d734d542ae11cd0f648200be29504eb1b6e2c995d2b75a 0.1s done
#5 DONE 0.1s

#6 [2/6] WORKDIR /app
#6 CACHED

#7 [3/6] RUN npm install -g nodemon
#7 CACHED

#8 [4/6] COPY package*.json ./
#8 CACHED

#9 [5/6] RUN npm install
#9 CACHED

#10 [6/6] COPY . /app
#10 DONE 0.2s

#11 exporting to image
#11 exporting layers
#11 exporting layers 0.2s done
#11 exporting manifest sha256:c918619bb76bf6e05a5fb4cf2674257d07862be30834403e338057eacf3c968b 0.0s done
#11 exporting config sha256:c9ef13676fc2736beb17b63deab82d141275a8c35c8df257a6bf61587610408c 0.0s done
#11 exporting attestation manifest sha256:0ae14328228f519cfd65c1e5d123d1e774da8249c96f24c3095af2d029c1e825 0.1s done
#11 exporting manifest list sha256:89ca8d970b7cd3aca4a1127a95b39df65e72366a9c2cc49640b15521b890d57d
#11 exporting manifest list sha256:89ca8d970b7cd3aca4a1127a95b39df65e72366a9c2cc49640b15521b890d57d 0.0s done
#11 naming to docker.io/library/api-residencias:latest done
#11 unpacking to docker.io/library/api-residencias:latest 0.1s done
#11 DONE 0.5s
[Pipeline] }
[Pipeline] // withEnv
[Pipeline] }
[Pipeline] // script
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Correr Contenedor para Pruebas)
[Pipeline] script
[Pipeline] {
[Pipeline] isUnix
[Pipeline] bat
[Pipeline] sleep
Sleeping for 5 Seg
[Pipeline] }
[Pipeline] // script
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Ejecutar Pruebas)
[Pipeline] script
[Pipeline] {
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -f http://localhost:3001/carrera/getCareer 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
  0     0    0     0    0     0      0      0 --:--:--  0:00:01 --:--:--     0
100   693  100   693    0     0    322      0  0:00:02  0:00:02 --:--:--   322
100   693  100   693    0     0    322      0  0:00:02  0:00:02 --:--:--   322
{"success":true,"data":[{"id":1,"nombre":"Ingeniería en Gestión Empresarial","turno":"M","coordinadorId":1},{"id":2,"nombre":"Ingeniería en Logística","turno":"M","coordinadorId":2},{"id":3,"nombre":"Ingeniería Industrial","turno":"M","coordinadorId":3},{"id":4,"nombre":"Ingeniería en Sistemas Computacionales","turno":"M","coordinadorId":4},{"id":5,"nombre":"Ingeniería en Tecnologías de la Información y Comunicaciones","turno":"V","coordinadorId":4},{"id":6,"nombre":"Ingeniería Electromecánica","turno":"M","coordinadorId":5},{"id":7,"nombre":"Ingeniería Mecatrónica","turno":"M","coordinadorId":6},{"id":8,"nombre":"Ingeniería Electrónica","turno":"M","coordinadorId":6}]}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -X POST http://localhost:3001/carrera/postCareer                         -H "Content-Type: application/json"                         -d "{\"nombre\": \"Logistica (PRUEBA)\", \"turno\": \"M\"}" 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100    46    0     0  100    46      0    207 --:--:-- --:--:-- --:--:--   207
100   198  100   152  100    46    182     55 --:--:-- --:--:-- --:--:--   237
{"success":true,"data":{"fieldCount":0,"affectedRows":1,"insertId":15,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -X PUT http://localhost:3001/carrera/putcareer/15                         -H "Content-Type: application/json"                         -d "{\"nombre\": \"Ingeniería en Logística (EXITOSA)\", \"turno\": \"V\"}" 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100   261  100   192  100    69    178     64  0:00:01  0:00:01 --:--:--   242
100   261  100   192  100    69    177     63  0:00:01  0:00:01 --:--:--   242
{"success":true,"data":{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"(Rows matched: 1  Changed: 1  Warnings: 0","protocol41":true,"changedRows":1}}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -f http://localhost:3001/carrera/getCoorCarre 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100   828  100   828    0     0   1225      0 --:--:-- --:--:-- --:--:--  1224
{"success":true,"data":[{"id":1,"nombre":"Virginia Rodríguez Moreno","email":"coordinacion.ige@leon.tecnm.mx","telefono":"4771900932"},{"id":2,"nombre":"Guillermina Hernández Hernández","email":"coordinacion.log@leon.tecnm.mx","telefono":"47793867938"},{"id":3,"nombre":"Irma Yareni Gómez Fuentes","email":"coordinacion.ind@leon.tecnm.mx","telefono":"4772529787"},{"id":4,"nombre":"Carlos Rafael Levy Rojas","email":"coordinacion.isx_tix@leon.tecnm.mx","telefono":"4779128281"},{"id":5,"nombre":"Iván Aguilar Carrillo","email":"coordinacion.electro@leon.tecnm.mx","telefono":"4771919293"},{"id":6,"nombre":"Luz Adriana Nicasio Collazo","email":"coordinacion.mcx_elx@leon.tecnm.mx","telefono":"4773663673"},{"id":7,"nombre":"Luz Adriana Nicasio Collazo","email":"coordinacioncampus2@leon.tecnm.mx","telefono":"4779786540"}]}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -X POST http://localhost:3001/carrera/postCoorCarre                         -H "Content-Type: application/json"                             -d "{\"nombre\": \"Prueba de POST\", \"email\": \"prueba\", \"telefono\": \"000000000\"}" 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100    72    0     0  100    72      0    339 --:--:-- --:--:-- --:--:--   338
100   224  100   152  100    72    200     94 --:--:-- --:--:-- --:--:--   295
{"success":true,"data":{"fieldCount":0,"affectedRows":1,"insertId":13,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0}}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -X PUT http://localhost:3001/carrera/putCoorCarre/13                         -H "Content-Type: application/json"                             -d "{\"nombre\": \"PRUEBA EXITOSA\", \"email\": \"PRUEBA@leon.tecnm.mx\", \"telefono\": \"4779786540\"}" 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100   279  100   192  100    87    314    142 --:--:-- --:--:-- --:--:--   458
{"success":true,"data":{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"(Rows matched: 1  Changed: 1  Warnings: 0","protocol41":true,"changedRows":1}}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -f http://localhost:3001/asesores/asesorRevisor 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100    26  100    26    0     0     44      0 --:--:-- --:--:-- --:--:--    44
{"success":true,"data":[]}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -X POST http://localhost:3001/asesores/asesorRevisor                         -H "Content-Type: application/json"                         -d "{\"IdAsesorExt\": null, \"IdAsesorInt\": null, \"IdRevisor1\": null, \"IdRevisor2\": null}" 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100   154  100    72  100    82    341    388 --:--:-- --:--:-- --:--:--   729
100   154  100    72  100    82    338    385 --:--:-- --:--:-- --:--:--   723
{"success":false,"error":"ReferenceError: ResidenceData is not defined"}
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>curl -X PUT http://localhost:3001/asesores/asesorRevisor/3                         -H "Content-Type: application/json"                         -d "{\"IdAsesorExt\": null, \"IdAsesorInt\": null, \"IdRevisor1\": null, \"IdRevisor2\": null}" 
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed

  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0
100    82    0     0  100    82      0     67  0:00:01  0:00:01 --:--:--    67
100   274  100   192  100    82    117     50  0:00:01  0:00:01 --:--:--   168
100   274  100   192  100    82    117     50  0:00:01  0:00:01 --:--:--   168
{"success":true,"data":{"fieldCount":0,"affectedRows":0,"insertId":0,"serverStatus":2,"warningCount":0,"message":"(Rows matched: 0  Changed: 0  Warnings: 0","protocol41":true,"changedRows":0}}
[Pipeline] }
[Pipeline] // script
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Stop Container)
[Pipeline] script
[Pipeline] {
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>docker stop api-residencias-container   || exit 0 
api-residencias-container
[Pipeline] bat

C:\ProgramData\Jenkins\.jenkins\workspace\api-prueba>docker rm api-residencias-container   || exit 0 
api-residencias-container
[Pipeline] }
[Pipeline] // script
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (Declarative: Post Actions)
[Pipeline] echo
Las pruebas han sido exitosas.
[Pipeline] }
[Pipeline] // stage
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
