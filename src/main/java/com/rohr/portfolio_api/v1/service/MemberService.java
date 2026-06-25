package com.rohr.portfolio_api.v1.service;

import com.rohr.portfolio_api.v1.client.MemberClient;
import com.rohr.portfolio_api.v1.client.response.MemberResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberClient memberClient;

    @Retryable(
            retryFor = FeignException.InternalServerError.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public List<MemberResponse> findMemberByIds(List<Long> ids) {
        return memberClient.findMemberByIds(ids);
    }
}
