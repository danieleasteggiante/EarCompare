package it.gend.domain;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Daniele Asteggiante
 */
public class EarProperties {
    private String path;
    private String name;
    private String size;
    private String lastModified;
    private List<String> jarsNameList = new ArrayList<>();
    private List<CustomFileTmp> files = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }


    public List<String> getJarsNameList() {
        return jarsNameList;
    }

    public void setJarsNameList(List<String> jarsNameList) {
        this.jarsNameList = jarsNameList;
    }

    public List<CustomFileTmp> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EarProperties)) return false;
        EarProperties that = (EarProperties) o;
        return Objects.equals(getSize(), that.getSize()) && Objects.equals(getLastModified(), that.getLastModified());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getLastModified());
    }
}
