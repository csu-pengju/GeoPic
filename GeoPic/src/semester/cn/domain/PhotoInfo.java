package semester.cn.domain;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoInfo {
    private Timestamp takenTime; //照片的拍摄时间
    private String takenPlace;//照片的拍摄地点
    private ArrayList<String> photoLabels;//照片的标签
    private ArrayList<Integer> facesId;//照片中人物的id
    private String photoPath;//照片的本地存储路径
    private String geo;//照片拍摄的GPS坐标
    private String thumbsPath;
    private  int photoId;

    private Map<String, List<String>> photoTimeAndPath = new HashMap<String,List<String>>();
//    private List<String> photoPath

    public PhotoInfo(){
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public ArrayList<Integer> getFacesId() {
        return facesId;
    }

    public ArrayList<String>getPhotoLabels() {
        return photoLabels;
    }

    public String getGeo() {
        return geo;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getTakenPlace() {
        return takenPlace;
    }

    public Timestamp getTakenTime() {
        return takenTime;
    }

    public Map<String, List<String>> getPhotoTimeAndPath() {
        return photoTimeAndPath;
    }

    public String getThumbsPath() {
        return thumbsPath;
    }

    public void setThumbsPath(String thumbsPath) {
        this.thumbsPath = thumbsPath;
    }

    public void setFacesId(ArrayList<Integer>facesId) {
        this.facesId = facesId;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public void setPhotoLabels(ArrayList<String> photoLabels) {
        this.photoLabels = photoLabels;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setTakenPlace(String takenPlace) {
        this.takenPlace = takenPlace;
    }

    public void setTakenTime(Timestamp takenTime) {
        this.takenTime = takenTime;
    }

    public void setPhotoTimeAndPath(Map<String, List<String>> photoTimeAndPath) {
        this.photoTimeAndPath = photoTimeAndPath;
    }
}
