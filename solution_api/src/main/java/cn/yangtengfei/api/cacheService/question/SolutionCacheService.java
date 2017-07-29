package cn.yangtengfei.api.cacheService.question;


import cn.yangtengfei.service.question.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SolutionCacheService {

    @Autowired
    private SolutionService solutionService;


}
