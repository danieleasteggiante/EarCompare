package it.gend.domain;

/**
 * @author Daniele Asteggiante
 */
public class CustomFileTmp {
    private String path;
    private String name;
    private byte[] content;
    private long size;
    private long lastModified;

    public CustomFileTmp(String path,
                         String name,
                         byte[] content,
                         long size,
                         long lastModified) {
        this.path = path;
        this.name = name;
        this.content = content;
        this.size = size;
        this.lastModified = lastModified;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }

    public String getSize() {
        return size;
    }

    public String getLastModified() {
        return lastModified;
    }
}
