package org.casadocodigo.loja.infra;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ObjectMetadata;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

@RequestScoped
public class FileSaver {
    @Inject
    private AmazonS3Client s3;
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String FILENAME_KEY = "filename=";

    public String write(Part multipartFile) {
        String fileName = extractFileName(multipartFile.getHeader(CONTENT_DISPOSITION));
        try {
            s3.putObject("casadocodigo", fileName, multipartFile.getInputStream(), new ObjectMetadata());
            return "https://s3.amazonaws.com/casadocodigo/" + fileName + "?noAuth=true";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String extractFileName(String contentDisposition) {
        if(contentDisposition == null){
            return null;
        }
        int startIndex = contentDisposition.indexOf(FILENAME_KEY);
        if(startIndex == -1) {
            return null;
        }
        String filename = contentDisposition.substring(startIndex + FILENAME_KEY.length());
        if(filename.startsWith("\"")) {
            int endIndex = filename.indexOf("\"", 1);
            if(endIndex != -1) {
                return filename.substring(1, endIndex);
            }
        } else {
            int endIndex = filename.indexOf(";");
            if(endIndex != -1) {
                return filename.substring(0, endIndex);
            }
        }
        return filename;
    }
}
