FROM eclipse-temurin:8-jdk
RUN apt-get update && apt-get install -y curl && apt-get install -y cron vim-tiny
# Script de inicio
COPY horticalneo.sh /horticalneo.sh
RUN chmod +x /horticalneo.sh
# Copiar cron
COPY mycron /etc/cron.d/mycron
# Permisos correctos para cron
RUN chmod 0644 /etc/cron.d/mycron
# Registrar crontab
RUN crontab /etc/cron.d/mycron
# Crear carpeta de logs
RUN touch /var/log/cron.log
# Ejecutar cron en primer plano para que el contenedor no se detenga
CMD ["cron", "-f"]