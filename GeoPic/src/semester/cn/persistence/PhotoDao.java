package semester.cn.persistence;

import com.sun.org.apache.xerces.internal.xs.StringList;
import semester.cn.domain.PhotoInfo;

import java.util.HashMap;
import java.util.Map;

public interface PhotoDao {
    public PhotoInfo getPhotoByPath(PhotoInfo photoInfo);
    public boolean insertPhotoInfoToDB(PhotoInfo photoInfo);
    public HashMap<String,String> getPhotoGPSAndPath();
}
