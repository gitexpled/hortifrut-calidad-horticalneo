#!/bin/bash
export PATH="/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
JAR_URL="https://syscalidad.hortifrut.com/hana/files/origen/neolitics/horticalneo.jar"
echo "Descargando JAR..."
curl -L $JAR_URL -o /app.jar
echo "Ejecutando aplicación..."
exec java -jar /app.jar