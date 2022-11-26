# Arquitectura de eventos para la ingesta de datos en tiempo real

Maestría en Informática

Escuela Colombiana de ingeniería Julio Garavito

laura.ramos-b@mail.escuelaing.edu.co

#### Video https://youtu.be/xjlQiVb_aTg

### I. Resumen
Hoy en día las aplicaciones modernas utilizan diferentes fuentes de información como redes sociales, bases de datos relacionales, 
métricas de uso de aplicación, etc, esto genera altos volúmenes de datos que deben ser procesados con el objetivo de unificar 
su almacenamiento y explotar la información para generar acciones en tiempo real que ayuden al negocio.

### II. Problema
Actualmente estoy trabajando en el desarrollo del proyecto de grado que tiene como objetivo la implementación de un
sistema Big Data que permita alertar en tiempo real posibles irregularidades en la contratación de gasto publico y 
licitaciones publicas. Una parte muy importante para la implementación de la arquitectura del proyecto es el almacenamiento y
limpieza previa de los datos que son necesarias para realizar las alertas o banderas rojas de corrupción sobre los contratos.
Para esto se plantea una arquitectura orientada a eventos con el objetivo de procesar esta información en tiempo real 
y almacenarla en un único punto para su futura explotación.

A demás, de acuerdo a la investigación realizada previamente en el proyecto de grado se requiere los procesos de 
contratación y los contratos licitados que son publicados en www.datos.gov.co

<img width="378" alt="Datos" src="https://user-images.githubusercontent.com/26145773/204081639-a404d8b0-eb05-4e9c-adb8-27136134341d.png">

### III. Arquitectura
A continuación se propone la arquitectura:

• Productor: Mediante una aplicación en Scala se validara
la fecha de actualización de los datos publicados en SECOP I y II, en dado caso de que la fecha sea actualizada
publicara un evento en un tópico diferente por cada fuente de información.

• Consumidor: Una aplicación en Scala que consuma los eventos publicados, descargue, almacene los datos en
Amazon S3 en la zona curated para su posterior limpieza con Jobs de AWS Glue utilizando Pyspark y ademas guarde su schema de datos por cada
fuente en catalogo de AWS Glue.

<img width="413" alt="Diagrama Proyecto" src="https://user-images.githubusercontent.com/26145773/204081622-7f350247-e347-4cfb-a312-7395cd3ec918.png">

