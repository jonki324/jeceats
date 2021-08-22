package dto.mapper;

import dto.ImagePresignedUrlDTO;

public class ImagePresignedUrlDTOMapper {
    public ImagePresignedUrlDTO mapToDTO(String url, String objectName) {
        return new ImagePresignedUrlDTO().setPresignedObjectUrl(url).setObjectName(objectName);
    }
}
