package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hmdp.utils.RedisConstants.CACHE_SHOPTYPE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryTypeList() {
        String key = CACHE_SHOPTYPE_KEY;
        String shopTypeJson = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(shopTypeJson)) {
            List<ShopType> res = JSONUtil.toList(shopTypeJson, ShopType.class);
            return Result.ok(res);
        }
        List<ShopType> res = query().orderByAsc("sort").list();
        if (res == null || res.size() == 0) {
            return Result.fail("无店铺种类");
        }
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(res));
        return Result.ok(res);
    }
}
