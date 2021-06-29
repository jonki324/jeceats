package dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ItemInputDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotBlank
    private String name;

    @DecimalMin("0")
    @DecimalMax("9999")
    private BigDecimal price;

    @NotBlank
    private String description;

    private Integer version;
}
