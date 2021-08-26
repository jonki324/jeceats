package dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class OrderDetailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String itemName;

    private BigDecimal price;

    private Integer quantity;

    private ItemDTO item;
}
