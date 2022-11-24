package eci.edu.co
package ports

trait ForJobETL {

  def createJob(jobName:String, scriptLocation: String, language: String, output_bucket_url: String): Either[Throwable, String]

  def startRunJob(jobName:String, output_bucket_url: String): Either[Throwable, String]
}
