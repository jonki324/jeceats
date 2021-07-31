package service;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import dao.ObjectStorageDAO;
import dto.ImageOutputDTO;

@RequestScoped
public class ImageService extends BaseService {
    @Inject
    private ObjectStorageDAO objectStorageDAO;

    public ImageOutputDTO getPresignedObjectUrlForGet(String objectName) {
        String url = objectStorageDAO.getPresignedObjectUrlMethodGet(objectName);
        return new ImageOutputDTO(url, objectName);
    }

    public ImageOutputDTO getPresignedObjectUrlForPut() {
        String objectName = UUID.randomUUID().toString();
        String url = objectStorageDAO.getPresignedObjectUrlMethodPut(objectName);
        return new ImageOutputDTO(url, objectName);
    }

    public ImageOutputDTO getPresignedObjectUrlForPut(Integer id, String objectName) {
        String url = objectStorageDAO.getPresignedObjectUrlMethodPut(id, objectName);
        return new ImageOutputDTO(url, objectName);
    }
}
