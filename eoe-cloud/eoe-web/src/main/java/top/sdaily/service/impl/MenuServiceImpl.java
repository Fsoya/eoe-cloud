package top.sdaily.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.sdaily.core.mybatis.Conditions;
import top.sdaily.core.mybatis.Page;
import top.sdaily.core.utils.IdGeneratorUtil;
import top.sdaily.core.web.exception.FailedException;
import top.sdaily.mapper.UserMapper;
import top.sdaily.model.User;
import top.sdaily.service.MenuService;
import top.sdaily.service.UserService;

import java.util.List;

/**
 * Created by soya on 2016/10/29.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

}
