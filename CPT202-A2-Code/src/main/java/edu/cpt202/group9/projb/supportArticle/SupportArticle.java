package edu.cpt202.group9.projb.supportArticle;

public class SupportArticle {
    private String fileName;
    private String url;

    public SupportArticle() {}

    public SupportArticle(String fileName, String url) {
        this.fileName = fileName;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
}
