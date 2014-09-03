package com.changhong.yupan.service;

import com.changhong.yupan.repository.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午3:24
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    public void updateClientInfo(String username, String productModel, String guJianVersion, String guJianVersionAfter) {
        clientDao.updateClientInfoByUsername(username, productModel, guJianVersion, guJianVersionAfter);
    }
}
