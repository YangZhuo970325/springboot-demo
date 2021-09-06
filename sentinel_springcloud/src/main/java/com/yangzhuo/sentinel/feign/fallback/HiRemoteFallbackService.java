package com.yangzhuo.sentinel.feign.fallback;

import com.yangzhuo.sentinel.feign.HiRemoteService;
import org.springframework.stereotype.Component;

@Component
public class HiRemoteFallbackService implements HiRemoteService {

    @Override
    public String hello() {
        return "系统繁忙，请稍后！";
    }
}
