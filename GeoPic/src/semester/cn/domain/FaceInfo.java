package semester.cn.domain;

import java.util.List;

public class FaceInfo {
    private String facePath;
    private List<String>faceLabels;
    private List<String>faceTokens;

    public FaceInfo(){

    }

    public List<String> getFaceLabels() {
        return faceLabels;
    }

    public List<String> getFaceTokens() {
        return faceTokens;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFaceLabels(List<String> faceLabels) {
        this.faceLabels = faceLabels;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public void setFaceTokens(List<String> faceTokens) {
        this.faceTokens = faceTokens;
    }
}
