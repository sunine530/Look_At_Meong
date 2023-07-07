package com.su.look_at_meong.repository;

import com.su.look_at_meong.model.item.entity.Item;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByMemberIdAndItemId(Long memberId, Long itemId);
}
