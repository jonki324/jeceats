package resource.response;

import java.io.Serializable;
import java.util.List;

import dto.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class ItemGetListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<ItemDTO> items;
}
