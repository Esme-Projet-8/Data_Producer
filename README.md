## Data Producer
This service generate random social media type of data that are pushed in Google Cloud Pub/Sub.

### How to run the service locally
In order to run the service locally on your machine, you need to download a service account with the right 
permissions to write data into Pub/Sub and pass the json file to the docker container as a volume.  
This can be achieved with the following (replace path_to_service_account.json with the right path).
```bash
docker run -v path_to_service_account.json:/projet-esme-plateforme-bi.json -d --name social_media_producer social_media_producer
```
Important! The Docker image will need to be build before running it with the following command:
```bash
docker build -t social_media_producer .
```