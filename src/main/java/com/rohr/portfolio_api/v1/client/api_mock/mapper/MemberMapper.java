package com.rohr.portfolio_api.v1.client.api_mock.mapper;

import com.rohr.portfolio_api.v1.client.api_mock.domain.dto.MemberDTO;
import com.rohr.portfolio_api.v1.client.api_mock.domain.entity.MemberEntity;
import com.rohr.portfolio_api.v1.client.api_mock.domain.form.MemberForm;

public class MemberMapper {
    public static MemberEntity toEntity(MemberForm form) {
        return new MemberEntity(null, form.getNome(), form.getCargo());
    }

    public static MemberDTO toDTO(MemberEntity entity){
        return new MemberDTO(entity.getId(), entity.getNome(), entity.getCargo());
    }
}
