package top.sdaily.core.mybatis;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

/**
 *
 * 基于mybatis注解的通用mapper
 *  在列 CRUDTemplate 针对 单表的增删改查进行sql拼装
 *  Conditions 参数需调用含有class入参的构造函数进行实例化
 * Created by soya on 2016/10/31.
 */
public interface BaseMapper<T> {

    @SelectProvider(type = CRUDTemplate.class,method = "findList")
    List<T> findList(Conditions conditions);

    @SelectProvider(type = CRUDTemplate.class,method = "findOne")
    T findOne(Conditions conditions);

    @InsertProvider(type = CRUDTemplate.class,method = "insert")
    int insert(T eneity);

    @InsertProvider(type = CRUDTemplate.class,method = "insertBatch")
    int insertBatch(List<T> entity);

    @UpdateProvider(type = CRUDTemplate.class,method = "update")
    int update(T entity);

    @DeleteProvider(type = CRUDTemplate.class,method = "delete")
    int delete(Conditions conditions);
}
