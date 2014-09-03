package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.system.domain.SystemActionLog;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:32
 */
public interface SystemDao extends EntityObjectDao {

    List<SystemActionLog> loadSystemActionLog(int year, int month, int startPosition, int pageSize);

    int loadSystemActionLogSize(int year, int month);
}
