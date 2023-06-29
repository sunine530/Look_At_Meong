package com.su.look_at_meong.model.member.entity;

import com.su.look_at_meong.model.member.constant.Role;
import com.su.look_at_meong.model.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    private LocalDateTime verifyExpiredAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateMember(ModifyMember modifyMember) {
        this.setName(modifyMember.getName());
        this.setPhone(modifyMember.getPhone());
    }
}
