package semester.cn.persistence;

import com.sun.org.apache.xerces.internal.xs.StringList;
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
}
