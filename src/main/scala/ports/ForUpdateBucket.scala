package eci.edu.co
package ports

trait ForUpdateBucket {

  def createBucket(bucketName: String): Either[Throwable, String]

  def uploadFileBucket(bucketName: String, fileName: String, file: String): Either[Throwable, String]

}
