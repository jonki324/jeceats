package resource.response.mapper;

import java.util.List;

import dto.ItemDTO;
import resource.response.ItemGetListResponse;

public class ItemGetListResponseMapper {
    public ItemGetListResponse mapToResponse(List<ItemDTO> dto) {
        return new ItemGetListResponse().setItems(dto);
    }
}
