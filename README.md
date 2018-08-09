# Boosterm backend

### Correr en local
Parado en la raiz del repo, `mvn spring-boot:run` levanta la aplicación en el puerto configurado en `application.yaml` (puse 5000 para que coincida con el de AWS, pero se puede cambiar para probar en local).
### Subir a amazon
Parado en la raiz del repo, `mvn package` genera una carpeta `target` con un archivo `xxxx.jar`. Ese archivo hay que subirlo a la consola de amazon > Services > Elastic Beanstalk > boosterm-backend-env > Upload and Deploy.
### Borrar archivos de más
`mvn clean` saca las carpetas con los jars y todo lo que se genera al correr la aplicación.
