# documentstorage

The project for storing documents in the following format:
```json
{
"path" : "SomePath",
"subject" : "Subject",
"data" : "Text"
}
```
The `path` represents hierarchy of documents with specified path-separator
The default separator is `.`.


Endpoints:
- GET  /documents/{document_id} # retrieve document by id and all its descendants
- POST /documents/              # save documents and returns document with its id

Load testing by `ab`:
```shell script
ab -n 4000 -c 1000 https://localhost:443/documents/{document_id}
```

In order to run the application execute the following command:
```shell script
mvn clean install -DskipTests && docker-compose up -d (detached mode)
```
By default, it will boot-up the next services:
- 3 instances of `app`-service
- 1 instance of `db`-service for mongodb
- 1 instance of `nginx`-service which represents proxy-server and load-balancer
Nginx serves 433-port with `localhost`-hostname.

Or it can be run with different number of app-instances (detached mode):
```shell script
mvn clean install -DskipTests && docker-compose up --scale app=<number_of_instances> -d
```