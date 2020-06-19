package semester.cn.persistence;

import semester.cn.domain.FaceInfo;
import semester.cn.domain.PhotoInfo;

public interface FaceDao {
    public boolean insertFaceInfoToDB(FaceInfo faceInfo);
    public boolean updateFaceLabelToDB(FaceInfo faceInfo);
}
