# hortifrut-calidad-horticalneo
Api de consumo de registros desde neolitics hacia hortical
`horticalneo.jar`

## 📌 Descripción
API para el traspaso de información desde los servidores de neolitics hacia el sistema de calidad de Hortifrut (Hortical)
---

## ⚙️ Tecnologías utilizadas
- Maven
- java 1.8
- Docker 28.5.2

## 🚀 Instalación

1. Instalar Docker
2. Generar archivo .jar `horticalneo.jar` 
3. Subir archvio al servidor hortical `https://syscalidad.hortifrut.com/hana/files/origen/neolitics/horticalneo.jar"`
4. Ejecutar archivo Dockerfile en la carpeta docker/
5. Ejecutar contenedor docker

## 📋 Requisitos
solo es sposible conectarse a las APIS de neolitics dese sus servidores, para ejecutar en una instancia de docker fuera de sus servidores es necesario una conexión via VPN

## 🔄 Flujo del sistema

```mermaid  
flowchart LR  
subgraph Neolitics
horticalneo-docker-->|GET|API-neolitics  
end 
  
subgraph Apache Hortical
CORE[API-Hortical]
CORE[API-Hortical]-->|SAVE| BACKUP[(Backup files)] 
end  

subgraph SAP XSEngine
horticalneo-docker-->|POST| CORE  
HANA[(HANA)]  
CORE[API-Hortical]-->|POST|API-HANA[API-xsjs]
API-HANA[API-xsjs]-->|INSERT| HANA[(HANA)]  
end



