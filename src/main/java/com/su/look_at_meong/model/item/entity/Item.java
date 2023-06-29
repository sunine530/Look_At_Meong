package com.su.look_at_meong.model.item.entity;

import com.su.look_at_meong.model.BaseEntity;
import com.su.look_at_meong.model.item.constatnt.Category;
import com.su.look_at_meong.model.item.constatnt.ItemStatus;
import com.su.look_at_meong.model.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@AuditOverride(forClass = BaseEntity.class)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "상품 이름을 입력해주세요.")
    private String itemName;
    @NotNull(message = "가격을 입력해주세요.")
    private String price;
    @NotNull(message = "상품 설명을 입력해주세요.")
    private String detail;
    @NotNull(message = "재고를 입력해주세요.")
    private int stockNum;
    private LocalDateTime verifyExpiredAt;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
}
