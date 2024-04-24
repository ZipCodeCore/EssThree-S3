import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import java.io.File;

public class S3Examples {

  private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

  // Create an S3 bucket
  public void createBucket(String bucketName) {
    s3.createBucket(bucketName);
  }

  // Upload an object to S3
  public void uploadObject(String bucketName, String key, File file) {
    s3.putObject(bucketName, key, file);
  }

  // Download an object from S3
  public void downloadObject(String bucketName, String key, File file) {
    s3.getObject(new GetObjectRequest(bucketName, key), file);
  }

  // List objects in a bucket
  public ObjectListing listObjects(String bucketName) {
    return s3.listObjects(bucketName);
  }

  // Generate pre-signed URL to share an S3 object
  public URL generatePresignedUrl(String bucketName, String key) {
    Date expiration = new Date();
    long expTimeMillis = expiration.getTime();
    expTimeMillis += 1000 * 60 * 60; // Add 1 hour.
    expiration.setTime(expTimeMillis);

    GeneratePresignedUrlRequest generatePresignedUrlRequest = 
        new GeneratePresignedUrlRequest(bucketName, key)
            .withMethod(HttpMethod.GET)
            .withExpiration(expiration);

    return s3.generatePresignedUrl(generatePresignedUrlRequest);
  }
}
// The key steps are:

// 1. Create an S3Client object to interact with S3
// 2. Call createBucket() to create a new S3 bucket
// 3. Upload objects using putObject(), specifying bucket name, key, and file
// 4. Download objects using getObject(), specifying bucket name, key, and target file
// 5. List objects in a bucket using listObjects()
// 6. Generate a pre-signed URL using generatePresignedUrl() by specifying bucket, 
// key, expiration date, and HTTP method.

// The pre-signed URL allows temporary access to private S3 objects without requiring AWS credentials. 
// The URL is only valid until the specified expiration date/time.