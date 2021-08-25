package entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class Order extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "status")
    private OrderStatus status;

    @OneToMany
    @JoinColumn(name = "order_detail_id")
    private List<OrderDetail> details;

    @OneToMany
    @JoinColumn(name = "order_status_logs_id")
    private List<OrderStatusLog> statusLogs;
}
