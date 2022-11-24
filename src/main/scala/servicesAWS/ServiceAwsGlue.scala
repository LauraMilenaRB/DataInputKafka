package eci.edu.co
package servicesAWS

import ports.ForJobETL

import com.amazonaws.{AmazonClientException, AmazonServiceException}
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.glue.{AWSGlue, AWSGlueClientBuilder}
import com.amazonaws.services.glue.model.{CreateJobRequest, DeleteJobRequest,  JobCommand, StartJobRunRequest}
import com.typesafe.scalalogging.Logger

import java.util.Map

object ServiceAwsGlue extends ForJobETL{
  private val LOGGER = Logger("ServiceJobAwsGlue")
  private val awsGlue = credentialsAwsGlue

  private def credentialsAwsGlue : Either[Throwable, AWSGlue] = {
    val AWS_ACCESS_KEY = "AKIAVFHJMDPBWQJUS5SP"
    val AWS_SECRET_KEY = "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"
    try {
      val awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
      val awsGlueClient = AWSGlueClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion("us-east-1").build()
      LOGGER.info("Connect AWSGlueClient")
      Right(awsGlueClient)
    }catch {
      case ase: AmazonServiceException => LOGGER.error("Exception: " + ase.toString)
        Left(ase)
      case ace: AmazonClientException => LOGGER.error("Exception: " + ace.toString)
        Left(ace)
    }
  }


  override def createJob(jobName: String,scriptLocation: String, language: String, output_bucket_url:String): Either[Throwable, String] = {
    awsGlue match {
      case Right(glue) =>
        try {
          val commandOfGlue = new JobCommand().withName("glueetl").withScriptLocation(scriptLocation)
          val configJobGlue = new CreateJobRequest().withName(jobName)
            .withWorkerType("Standard").withGlueVersion("3.0").withNumberOfWorkers(6)
            .withRole("arn:aws:iam::354824231875:role/AWSGlueServiceRoleDefault")
            .withCommand(commandOfGlue)
            .withDefaultArguments(Map.of("--job-language", language,"--enable-job-insights","true","--output_bucket_url", output_bucket_url))
          glue.createJob(configJobGlue)
          Right(s"Created Glue Job $jobName")
        }catch {
          case e:Exception => LOGGER.info("Exception: " + e.toString)
            Left(e)
        }
      case Left(e) =>
        LOGGER.info("Exception: " + e.toString)
        Left(e)
    }
  }

  override def startRunJob(jobName: String, output_bucket_url:String): Either[Throwable, String] = {
    awsGlue match {
      case Right(glue) =>
        try {
          val startJobRun = new StartJobRunRequest().withJobName(jobName).withArguments(Map.of("--output_bucket_url", output_bucket_url))
          glue.startJobRun(startJobRun)
          Right(s"startRunJob Glue Job $jobName")
        }catch {
          case e:Exception => LOGGER.info("Exception: " + e.toString)
            Left(e)
        }finally {
          //val configDeletedJob = new DeleteJobRequest().withJobName(jobName)
          //glue.deleteJob(configDeletedJob)
          LOGGER.info(s"Deleted Glue Job $jobName")
        }
      case Left(e) =>
        LOGGER.info("Exception: " + e.toString)
        Left(e)
    }
  }

  def createStartRunJobPython(jobName: String,scripLocation: String, output_bucket_url: String): Either[Throwable, String]  ={
    awsGlue match {
      case Right(glue) => (createJob(jobName,scripLocation,"python",output_bucket_url),startRunJob(jobName,output_bucket_url)) match {
        case (Right(s1),Right(s2)) => Right(s"$s1 and $s2")
        case (Left(e1),_) => Left(e1)
        case (_,Left(e2)) => Left(e2)
      }
      case Left(e) => LOGGER.info("Exception: " + e.toString)
        Left(e)
    }

  }
}
