package eci.edu.co
package servicesAWS

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.{AmazonClientException, AmazonServiceException}
import ports.ForUpdateBucket
import com.typesafe.scalalogging.Logger

import java.io.File
import sys.process._
import java.net.URL
import scala.language.postfixOps


object ServiceBucketAws extends ForUpdateBucket {
  private val LOGGER = Logger("ServiceBucketAws")
  private val amazonS3 = credentialsS3

  private def credentialsS3 : Either[Throwable, AmazonS3Client] = {
    val AWS_ACCESS_KEY = "AKIAVFHJMDPBXGTIGC64"
    val AWS_SECRET_KEY = "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"
    try {
      val awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
      val amazonS3Client = new AmazonS3Client(awsCredentials)
      LOGGER.info("Connect amazonS3Client")
      Right(amazonS3Client)
    }catch {
      case ase: AmazonServiceException => LOGGER.error("Exception: " + ase.toString)
        Left(ase)
      case ace: AmazonClientException => LOGGER.error("Exception: " + ace.toString)
        Left(ace)
    }
  }

  override def createBucket(bucketName: String): Either[Throwable, String] = {
    amazonS3 match {
      case Right(s3) =>
        if(!s3.doesBucketExistV2(bucketName)) {
          s3.createBucket(bucketName)
          Right(s"Created Bucket $bucketName")
        }else{
          LOGGER.info("Exception: Already Exist Bucket")
          Left(new Exception("Already Exist Bucket"))
        }
      case Left(e) =>
        LOGGER.info("Exception: " + e.toString)
        Left(e)
    }
  }

  override def uploadFileBucket(bucketName: String, fileName: String, url: String): Either[Throwable, String] = {
    val fileNameRefactor = fileName.replace(".","").replace("-","").replace(":","")+".json"
    amazonS3 match {
      case Right(s3) =>
        lazy val file = new File("Files/"+fileNameRefactor)
        new URL(url) #> file !!

        try {
          s3.putObject(bucketName,fileName.split("\\.").head+"/"+fileNameRefactor,file)
          Right(s"Upload Bucket File $fileNameRefactor")
        }catch {
          case e:Exception => LOGGER.info("Exception: " + e.toString)
            Left(e)
        } finally {
          file.delete()

        }
      case Left(e) => LOGGER.info("Exception: " + e.toString)
        Left(e)
    }
  }

}
