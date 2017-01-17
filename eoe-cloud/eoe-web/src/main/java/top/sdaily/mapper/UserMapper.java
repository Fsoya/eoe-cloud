package top.sdaily.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.sdaily.core.mybatis.BaseMapper;
import top.sdaily.web.model.User;

/**
 * Created by soya on 2016/10/28.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}
