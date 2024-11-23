package it.gend.domain;

/**
 * @author Daniele Asteggiante
 */
public class CustomFileTmp {
    private String path;
    private String name;
    private byte[] content;

    public CustomFileTmp(String path, String name, byte[] content) {
        this.path = path;
        this.name = name;
        this.content = content;
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
}
