package semester.cn.domain;

import java.util.ArrayList;
import java.util.List;

public class FaceInfo {
    private String facePath;
    private ArrayList<String> faceLabels;
    private ArrayList<String>faceTokens;

    public FaceInfo(){

    }

    public ArrayList<String> getFaceLabels() {
        return faceLabels;
    }

    public ArrayList<String> getFaceTokens() {
        return faceTokens;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFaceLabels(ArrayList<String> faceLabels) {
        this.faceLabels = faceLabels;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public void setFaceTokens(ArrayList<String> faceTokens) {
        this.faceTokens = faceTokens;
    }
}
