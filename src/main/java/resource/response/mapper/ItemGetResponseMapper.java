package resource.response.mapper;

import dto.ItemDTO;
import resource.response.ItemGetResponse;

public class ItemGetResponseMapper {
    public ItemGetResponse mapToResponse(ItemDTO dto) {
        return new ItemGetResponse().setItem(dto);
    }
}
