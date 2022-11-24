package eci.edu.co
package kafka

object Constants {
  val BUCKET_NAME_STAGING = "staging-zone-eci"
  val BUCKET_NAME_RAW = "raw-zone-eci"
  val IP = "localhost:9093"
  val SERVER_NAME = "bootstrap.servers"

  val TOPIC_LIST = Map(
    "f789-7hwg" -> "https://www.datos.gov.co/Gastos-Gubernamentales/SECOP-I-Procesos-de-Compra-P-blica/f789-7hwg",
    "qmzu-gj57" -> "https://www.datos.gov.co/Gastos-Gubernamentales/SECOP-II-Proveedores-Registrados/qmzu-gj57",
    "p6dx-8zbt" -> "https://www.datos.gov.co/Gastos-Gubernamentales/SECOP-II-Procesos-de-Contrataci-n/p6dx-8zbt",
    "rpmr-utcd" -> "https://www.datos.gov.co/Gastos-Gubernamentales/SECOP-Integrado/rpmr-utcd",
    "rgxm-mmea" -> "https://www.datos.gov.co/Gastos-Gubernamentales/Tienda-Virtual-del-Estado-Colombiano-Consolidado/rgxm-mmea"
  )

  val API_URL = Map(
    "https://www.datos.gov.co/Gastos-Gubernamentales/SECOP-Integrado/rpmr-utcd" -> "https://www.datos.gov.co/resource/rpmr-utcd.json" ,
    "https://www.datos.gov.co/Gastos-Gubernamentales/Tienda-Virtual-del-Estado-Colombiano-Consolidado/rgxm-mmea" -> "https://www.datos.gov.co/resource/rgxm-mmea.json",
  )
}
