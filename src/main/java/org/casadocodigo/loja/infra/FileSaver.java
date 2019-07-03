package org.casadocodigo.loja.infra;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@RequestScoped
public class FileSaver {

    @Inject
    private HttpServletRequest request;
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String FILENAME_KEY = "filename=";

    public String write(String baseFolder, Part multipartFile) {
        String serverPath = request.getServletContext().getRealPath(String.format("/%s", baseFolder));
        String fileName = extractFileName(multipartFile.getHeader(CONTENT_DISPOSITION));
        String path = String.format("%s/%s", serverPath, fileName);
        try {
            multipartFile.write(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return String.format("%s/%s", baseFolder, fileName);
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
