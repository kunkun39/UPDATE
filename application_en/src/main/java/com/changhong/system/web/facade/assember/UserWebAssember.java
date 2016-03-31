package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.update.domain.ProductCategory;
import com.changhong.update.web.facade.assember.ProductWebAssember;
import com.changhong.update.web.facade.dto.CategoryDTO;
import com.changhong.system.domain.User;
import com.changhong.system.web.facade.dto.UserDTO;
import com.changhong.system.web.facade.dto.UserPasswordDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:58
 */
public class UserWebAssember {

    public static User toUserDomain(UserDTO userDTO) {
        User user = null;
        if(userDTO == null) return null;

        String[] ids = StringUtils.delimitedListToStringArray(userDTO.getCategoryIds(), "|");
        List<ProductCategory> categories = EntityLoadHolder.getUserDao().findByIds(ids, ProductCategory.class);

        if (userDTO.getId() > 0) {
            user = (User) EntityLoadHolder.getUserDao().findById(userDTO.getId(), User.class);
            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setContactWay(userDTO.getContactWay());

            user.setResponsibleCategories(null);
            user.setResponsibleCategories(categories);
        } else {
            user = new User(userDTO.getName(), userDTO.getContactWay(), userDTO.getUsername(), userDTO.getPassword());
            user.setResponsibleCategories(categories);
        }
        return user;
    }

    public static UserDTO toUserDTO(User user, boolean addSubInfo) {
        final int id = user.getId();
        final String name = user.getName();
        final String contactWay = user.getContactWay();
        final String username = user.getUsername();
        final String password = user.getPassword();
        final boolean enabled = user.isEnabled();

        UserDTO dto =  new UserDTO(id, name, contactWay, username, password, enabled);
        if (addSubInfo) {
            List<CategoryDTO> categoryDTOs = ProductWebAssember.toCategoryDTOListWithFullPath(user.getResponsibleCategories());
            dto.setCategories(categoryDTOs);
        }

        return dto;
    }

    public static List<UserDTO> toUserDTOList(List<User> users) {
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        if (users != null) {
            for (User user : users) {
                dtos.add(toUserDTO(user, false));
            }
        }
        return dtos;
    }

    public static UserPasswordDTO toPasswordDTO(User user) {
        final int userId = user.getId();
        final String name = user.getName();
        final String userName = user.getUsername();
        return new UserPasswordDTO(userId, name, userName);
    }
}
