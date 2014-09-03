package com.changhong.yupan.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.update.domain.ProductUpdate;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午4:37
 */
public interface UpdateDao extends EntityObjectDao {

    public List<ProductUpdate> findProductUpdate(String model, String updateWay, String version);
}
