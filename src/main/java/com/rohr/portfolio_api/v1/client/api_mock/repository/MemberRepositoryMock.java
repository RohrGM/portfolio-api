package com.rohr.portfolio_api.v1.client.api_mock.repository;

import com.rohr.portfolio_api.v1.client.api_mock.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryMock extends JpaRepository<MemberEntity, Long> {
}
