package top.sdaily.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.sdaily.core.mybatis.BaseMapper;
import top.sdaily.web.dto.UserMenu;
import top.sdaily.web.model.Menu;

import java.util.List;

/**
 * Created by soya on 2016/10/28.
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<UserMenu> findUserMenus(String parentId);
}
