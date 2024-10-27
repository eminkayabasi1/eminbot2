package com.app.fku.Utils;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;

import java.io.IOException;

public class EminPatentTest {

    public static void main(String[] args) throws IOException {

        /**
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("https://patentfilessharing.blob.core.windows.net")
                .sasToken("si=vbt&spr=https&sv=2020-08-04&sr=c&sig=8GmYUg12KAKEx5v3pixegTSyvpyFvHGV%2FI5C%2FZvM%2FtE%3D")
                .buildClient();
         */
        /**
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .endpoint("https://patentfilessharing.blob.core.windows.net")
                .sasToken("si=vbt&spr=https&sv=2020-08-04&sr=c&sig=8GmYUg12KAKEx5v3pixegTSyvpyFvHGV%2FI5C%2FZvM%2FtE%3D")
                .containerName("patentfiles")
                .buildClient();
         */
        /**
        BlobClient blobClient = new BlobClientBuilder()
                .endpoint("https://patentfilessharing.blob.core.windows.net")
                .sasToken("si=vbt&spr=https&sv=2020-08-04&sr=c&sig=8GmYUg12KAKEx5v3pixegTSyvpyFvHGV%2FI5C%2FZvM%2FtE%3D")
                .containerName("patentfiles")
                .blobName("Dokumanlar")
                .buildClient();
        BinaryData content = blobClient.downloadContent();
         */
        test1();
        System.out.println("");
    }

    public static void test1() throws IOException {
        String containerName = "patentfiles";
        String sasToken =  "si=vbt&spr=https&sv=2020-08-04&sr=c&sig=8GmYUg12KAKEx5v3pixegTSyvpyFvHGV%2FI5C%2FZvM%2FtE%3D";

        BlobServiceClient storageClient = new BlobServiceClientBuilder()
                .endpoint("https://patentfilessharing.blob.core.windows.net")
                .sasToken(sasToken).buildClient();

        BlobContainerClient blobContainerClient =
                storageClient.getBlobContainerClient(containerName);

        PagedIterable<BlobItem> blobs = blobContainerClient.listBlobs();
        BlobClient blobClient = null;
        for (BlobItem blobItem : blobs) {
            blobClient = blobContainerClient.getBlobClient(blobItem.getName());

            if (blobClient != null) {
                BinaryData content = blobClient.downloadContent();
                byte[] fileByteArray = content.toBytes();
                String fileName = blobItem.getName();
            }
        }
    }
}
