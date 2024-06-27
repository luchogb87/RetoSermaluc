# RetoSermaluc
API RESTFull para usuario

Diagrama de solución
Descripción de la imagen



Link para ver la base datos
http://localhost:8080/h2-console/

Probar registro de usuario:
consideraciones: Para el password deberá contener como mínimo 8 carácteres, empezar por una mayúscula, tener número y carácteres especiales.
Puede usar el password ejemplo: Qwerty@123

curl --location 'http://localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=60B9C69B115B671EF9D459D8B3855305' \
--data-raw '{
    "name": "Luis Chivito",
    "email": "luis@correo.org",
    "password": "Qwerty@123",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}'

Obtener todos los usuarios:
curl --location 'http://localhost:8080/api/users' \
--header 'Cookie: JSESSIONID=60B9C69B115B671EF9D459D8B3855305'

Busqueda de usuario: curl --location 'http://localhost:8080/api/users/1' \
--header 'Cookie: JSESSIONID=60B9C69B115B671EF9D459D8B3855305'