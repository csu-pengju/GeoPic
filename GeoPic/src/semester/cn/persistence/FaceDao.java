package semester.cn.persistence;

import semester.cn.domain.FaceInfo;
import semester.cn.domain.PhotoInfo;

import java.util.HashMap;

public interface FaceDao {
    public boolean insertFaceInfoToDB(FaceInfo faceInfo);
    public boolean updateFaceLabelToDB(FaceInfo faceInfo);
    public int getFaceIdAccordingFacePath(FaceInfo faceInfo);
    public int getFaceIdAccordingFaceToken(FaceInfo faceInfo);
    public HashMap<String,String> getFacePathAndLabel(int faceId);
    public int getFaceIdAccordingFaceLabel(FaceInfo faceInfo);
}
