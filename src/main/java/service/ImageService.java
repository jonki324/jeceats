package service;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import dao.ItemDAO;
import dao.ObjectStorageDAO;
import dto.ImageOutputDTO;

@RequestScoped
public class ImageService extends BaseService {
    @Inject
    protected ObjectStorageDAO objectStorageDAO;

    @Inject
    protected ItemDAO itemDAO;

    public ImageOutputDTO getPresignedObjectUrlForGet(Integer id, String objectName) {
        Long count = itemDAO.countByIdAndObjectName(id, objectName);
        if (count != 1) {
            throw createAppException(msgConfig.GET_SIGNED_URL);
        }
        String url = objectStorageDAO.getPresignedObjectUrlMethodGet(objectName);
        return new ImageOutputDTO(url, objectName);
    }

    public ImageOutputDTO getPresignedObjectUrlForPut() {
        String objectName = UUID.randomUUID().toString();
        String url = objectStorageDAO.getPresignedObjectUrlMethodPut(objectName);
        return new ImageOutputDTO(url, objectName);
    }

    public ImageOutputDTO getPresignedObjectUrlForPut(Integer id, String objectName) {
        Long count = itemDAO.countByIdAndObjectName(id, objectName);
        if (count != 1) {
            throw createAppException(msgConfig.GET_SIGNED_URL);
        }
        String url = objectStorageDAO.getPresignedObjectUrlMethodPut(objectName);
        return new ImageOutputDTO(url, objectName);
    }
}
