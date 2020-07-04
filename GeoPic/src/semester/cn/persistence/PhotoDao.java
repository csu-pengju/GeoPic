package semester.cn.persistence;

import org.opencv.photo.Photo;
import semester.cn.domain.PhotoInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface PhotoDao {
    public PhotoInfo getPhotoByPath(PhotoInfo photoInfo);
    public boolean insertPhotoInfoToDB(PhotoInfo photoInfo);
    public HashMap<String,String> getPhotoGPSAndPath();
    public ArrayList<String> getTimeQueryPhotoPath(String startTime, String endTime);
    public ArrayList<String> getPlaceQueryPhotoPath(String geo,String address);
    public ArrayList<String> getAllPhotoPath();
    public boolean getThumbs();
    public int getPhotoIdAcoordintPhotoPath(PhotoInfo photoInfo);
    public boolean insertPhotoFaceId(PhotoInfo photoInfo);
    public boolean insertPhotoLabel(PhotoInfo photoInfo);
    public HashMap<String,String>getPhotoDetail(String photoPath);

    public ArrayList<String> getSemanticQueryPhotoPath(PhotoInfo photoInfo);

    public ArrayList<String >getIntegratedQueryPhotoPath(PhotoInfo photoInfo,String startTime,String endTime,String geo,String address);

    public HashMap<String,String>getAllTimeLinePhotoPath();



}
