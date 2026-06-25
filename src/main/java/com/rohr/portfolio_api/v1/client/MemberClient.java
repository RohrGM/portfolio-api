package com.rohr.portfolio_api.v1.client;

import com.rohr.portfolio_api.v1.client.response.MemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "memberClient",
        url = "${services.member-api.url}"
)
public interface MemberClient {
    @GetMapping(
            value = "/api/v1/member/find",
            consumes = "application/json",
            produces = "application/json"
    )
    List<MemberResponse> findMemberByIds(@RequestParam(name = "ids") List<Long> ids);
}
