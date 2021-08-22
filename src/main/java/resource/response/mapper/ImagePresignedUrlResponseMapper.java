package resource.response.mapper;

import dto.ImagePresignedUrlDTO;
import resource.response.ImagePresignedUrlResponse;

public class ImagePresignedUrlResponseMapper {
    public ImagePresignedUrlResponse mapToResponse(ImagePresignedUrlDTO dto) {
        return new ImagePresignedUrlResponse().setPresignedObjectUrl(dto.getPresignedObjectUrl())
                .setObjectName(dto.getObjectName());
    }
}
