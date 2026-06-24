package com.rohr.portfolio_api.v1.mapper;

import com.rohr.portfolio_api.v1.client.response.MemberResponse;
import com.rohr.portfolio_api.v1.domain.dto.MemberDTO;

public class MemberMapper {
    public static MemberDTO toDTO(MemberResponse memberResponse){
        return new MemberDTO(
                memberResponse.getId(),
                memberResponse.getNome(),
                memberResponse.getCargo()
        );
    }
}
