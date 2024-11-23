package it.gend.domain;

/**
 * @author Daniele Asteggiante
 */
public class InputUser {
    private String pathEar1;
    private String pathEar2;

    public InputUser(String pathEar1, String pathEar2) {
        this.pathEar1 = pathEar1;
        this.pathEar2 = pathEar2;
    }

    public String getPathEar1() {
        return pathEar1;
    }

    public String getPathEar2() {
        return pathEar2;
    }
}
