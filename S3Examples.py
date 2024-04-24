import boto3
from botocore.exceptions import ClientError

s3 = boto3.client('s3')

# Create a bucket
bucket_name = 'my-bucket'
location = {'LocationConstraint':'us-east-1'}
s3.create_bucket(Bucket=bucket_name, CreateBucketConfiguration=location)

# Upload an object to bucket
filename = 'data.csv'
object_name = 'data/data.csv'
s3.upload_file(filename, bucket_name, object_name)

# List objects in bucket
response = s3.list_objects(Bucket=bucket_name)
for object in response['Contents']:
    print(object['Key'])

# Download an object
s3.download_file(bucket_name, object_name, 'data_download.csv')

# Generate pre-signed URL to share an object
url = s3.generate_presigned_url(
    ClientMethod='get_object',
    Params={'Bucket': bucket_name, 'Key': object_name},
    ExpiresIn=3600)

print(url)

# This creates an S3 bucket, uploads a local CSV file to the bucket under object name 'data/data.csv',
# lists all objects in the bucket, downloads the object to a local file, and generates a pre-signed URL
# that allows temporary access to download the object for anyone with the URL.
# The pre-signed URL expires after 3600 seconds (1 hour).
