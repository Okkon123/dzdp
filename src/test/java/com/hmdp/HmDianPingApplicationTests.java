package com.hmdp;

import com.hmdp.service.IShopService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HmDianPingApplicationTests {
    @Autowired
    IShopService shopService;
    @Test
    void testSaveShop() {
        shopService.saveShop2Redis(2L, 10L);
    }
}
