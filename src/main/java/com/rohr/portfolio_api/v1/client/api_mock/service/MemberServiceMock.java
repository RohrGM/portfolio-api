package com.rohr.portfolio_api.v1.client.api_mock.service;

import com.rohr.portfolio_api.v1.client.api_mock.domain.dto.MemberDTO;
import com.rohr.portfolio_api.v1.client.api_mock.domain.entity.MemberEntity;
import com.rohr.portfolio_api.v1.client.api_mock.domain.form.MemberForm;
import com.rohr.portfolio_api.v1.client.api_mock.mapper.MemberMapper;
import com.rohr.portfolio_api.v1.client.api_mock.repository.MemberRepositoryMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceMock {

    @Autowired
    private MemberRepositoryMock memberRepository;

    public MemberDTO createMember(MemberForm form){
        MemberEntity memberEntity = this.memberRepository.save(
                MemberMapper.toEntity(form)
        );

        return MemberMapper.toDTO(memberEntity);
    }

    public List<MemberDTO> findMembers(List<Long> ids){
        return this.memberRepository.findAllById(ids).stream()
                .map(MemberMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<MemberDTO> listMembers(){
        return this.memberRepository.findAll().stream()
                .map(MemberMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
