package top.sdaily.core.mybatis;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by soya on 2016/10/31.
 */
public class CRUDTemplate {

    public String findOne(Conditions conditions) {
        String sql = findList(conditions);
        sql += " limit 1";
        return sql;
    }

    public String findList(Conditions conditions) {
        Class entityClazz = conditions.getEnetityClazz();

        String tableName = getTableName(entityClazz);

        StringBuilder sql = new StringBuilder();
        StringBuilder sqlColumn = new StringBuilder();

        Field[] declaredFields = entityClazz.getDeclaredFields();

        for(Field field : declaredFields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(Transient.class)) continue;

            Column column = field.getAnnotation(Column.class);
            if(column != null && !StringUtils.isEmpty(column.name())){
                sqlColumn.append(MessageFormat.format("{0} as {1},",column.name(),field.getName()));
            }else {
                sqlColumn.append(field.getName()).append(",");
            }
        }

        sql.append("select ").append(sqlColumn.substring(0,sqlColumn.length()-1)).append(" from ").append(tableName);

        StringBuilder sqlWhere = joinSqlWhere(conditions);

        sql.append(sqlWhere);
        return sql.toString();
    }


    public String insert(Object eneity) throws IllegalAccessException {
        Class entityClazz = eneity.getClass();

        String tableName = getTableName(entityClazz);

        StringBuilder sql = new StringBuilder();
        sql.append(MessageFormat.format("insert into {0} " , tableName));

        StringBuilder sqlColumns = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        Field[] declaredFields = entityClazz.getDeclaredFields();

        for(Field field : declaredFields){
            field.setAccessible(true);
            String name = field.getName();

            if(field.isAnnotationPresent(Transient.class)) continue;

            Column column = field.getAnnotation(Column.class);
            if(column != null && !StringUtils.isEmpty(column.name())){
                name = column.name();
            }

            sqlColumns.append(name + ",");
            sqlValues.append(MessageFormat.format("#'{'{0}'}',",field.getName()));
        }

        int length = sqlColumns.length();
        if(length > 0) {
            sqlColumns = sqlColumns.replace(length-1,length,"");
            sqlColumns.insert(0," (").append(")");

            sqlValues = sqlValues.replace(sqlValues.length() - 1,sqlValues.length(),"");
            sqlValues.insert(0," values(").append(")");
        }else{
            throw new RuntimeSqlException(entityClazz.getName() + "属性为空，拼装insert语句失败");
        }

        sql.append(sqlColumns).append(sqlValues);
        return sql.toString();
    }


    public String insertBatch(Object obj) throws IllegalAccessException {
        List entities = (List)((Map)obj).get("list");
        Class entityClazz;
        if(entities != null && entities.size() > 0){
            entityClazz = entities.get(0).getClass();
        }else{
            throw new RuntimeException("批量插入失败，集合为空");
        }

        String tableName = getTableName(entityClazz);
        StringBuilder sql = new StringBuilder();
        sql.append(MessageFormat.format("insert into {0} " , tableName));

        StringBuilder sqlColumns = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();

        Field[] declaredFields = entityClazz.getDeclaredFields();
        for(Field field : declaredFields){
            field.setAccessible(true);
            String name = field.getName();

            if(field.isAnnotationPresent(Transient.class)) continue;

            Column column = field.getAnnotation(Column.class);
            if(column != null && !StringUtils.isEmpty(column.name())){
                name = column.name();
            }

            sqlColumns.append(name + ",");
            sqlValues.append(MessageFormat.format("#'''{'''{0}'''}''',","list[{0}]."+field.getName()));
        }

        if(sqlColumns.length() > 0) {
            sqlColumns = sqlColumns.replace(sqlColumns.length()-1,sqlColumns.length(),"");
            sqlColumns.insert(0," (").append(") values");

            sqlValues = sqlValues.replace(sqlValues.length() - 1,sqlValues.length(),"");
            sqlValues.insert(0," (").append(")");
        }else{
            throw new RuntimeSqlException(entityClazz.getName() + "属性为空，拼装insert语句失败");
        }

        MessageFormat messageFormat = new MessageFormat(sqlValues.toString());
        sqlValues = new StringBuilder();
        sqlValues.append("\n").append(messageFormat.format(new Integer[]{0}));
        for (int i=1; i<entities.size(); i++){
            sqlValues.append(",").append("\n").append(messageFormat.format(new Integer[]{i}));
        }

        return sql.append(sqlColumns).append(sqlValues).toString();
    }

    public String update(Object obj){
        Class entityClazz = obj.getClass();
        String tableName = getTableName(entityClazz);

        StringBuilder sql = new StringBuilder();
        sql.append(MessageFormat.format("update {0}",tableName));

        StringBuilder sqlSet = new StringBuilder();
        sqlSet.append(" set ");

        Field[] declaredFields = entityClazz.getDeclaredFields();

        String id = getTableId(entityClazz);
        StringBuilder sqlWhere = new StringBuilder();
        sqlWhere.append(MessageFormat.format("where {0} = #'{'{0}'}'",id));

        for(Field field : declaredFields){
            field.setAccessible(true);
            String name = field.getName();

            if(field.isAnnotationPresent(Id.class)) continue;

            if(field.isAnnotationPresent(Transient.class)) continue;

            Column column = field.getAnnotation(Column.class);
            if(column != null && !StringUtils.isEmpty(column.name())){
                name = column.name();
            }

            sqlSet.append(MessageFormat.format("{0} = #'{'{1}'}' ,",name,field.getName()));
        }

        return sql.append(sqlSet.substring(0,sqlSet.length()-1)).append(sqlWhere).toString();
    }

    public String updateBatch(Object obj){
        // todo 批量实体update语句生成
        return null;
    }


    public String delete(Conditions conditions){
        String tableName = getTableName(conditions.getEnetityClazz());

        StringBuilder sql = new StringBuilder();
        sql.append(MessageFormat.format("delete from {0} " , tableName));

        StringBuilder sqlWhere = joinSqlWhere(conditions);

        return sql.append(sqlWhere).toString();
    }

    private StringBuilder joinSqlWhere(Conditions conditions){
        StringBuilder sqlWhere = new StringBuilder();
        sqlWhere.append(" where 1=1 ");

        StringBuilder orderBy = new StringBuilder();
        for (Object entry : conditions.entrySet()) {
            String key = ((Map.Entry<String, Object>) entry).getKey();
            Object value = ((Map.Entry<String, Object>) entry).getValue();
            if(key.startsWith("_eq_")){
                if (value instanceof String) {
                    Pattern p = Pattern.compile("(\\d){4}-(\\d){2}-(\\d){2}");
                    Matcher matcher = p.matcher(value.toString());
                    if (matcher.matches()) {
                        sqlWhere.append(MessageFormat.format(" and DATE_FORMAT({0}, ''%Y-%m-%d'') = #'{'{1}'}'", key.substring(4), key));
                    } else {
                        sqlWhere.append(MessageFormat.format(" and {0} = #'{'{1}'}'", key.substring(4), key));
                    }
                } else {
                    sqlWhere.append(MessageFormat.format(" and {0} = #'{'{1}'}'", key.substring(4), key));
                }
            }else if(key.startsWith("_neq_")){
                if (value instanceof String) {
                    Pattern p = Pattern.compile("(\\d){4}-(\\d){2}-(\\d){2}");
                    Matcher matcher = p.matcher(value.toString());
                    if (matcher.matches()) {
                        sqlWhere.append(MessageFormat.format(" and DATE_FORMAT({0}, ''%Y-%m-%d'') != #'{'{1}'}'", key.substring(5), key));
                    } else {
                        sqlWhere.append(MessageFormat.format(" and {0} != #'{'{1}'}'", key.substring(5), key));
                    }
                } else {
                    sqlWhere.append(MessageFormat.format(" and {0} != #'{'{1}'}'", key.substring(5), key));
                }
            }else if(key.equals("_id")){
                String id = getTableId(conditions.getEnetityClazz());
                sqlWhere.append(MessageFormat.format(" and {0} = #'{'{1}'}'", id, key));
            }else if(key.startsWith("_gt_")){
                sqlWhere.append(MessageFormat.format(" and {0} > #'{'{1}'}'", key.substring(4), key));
            }else if(key.startsWith("_gte_")){
                sqlWhere.append(MessageFormat.format(" and {0} >= #'{'{1}'}'", key.substring(5), key));
            }else if(key.startsWith("_lt_")){
                sqlWhere.append(MessageFormat.format(" and {0} < #'{'{1}'}'", key.substring(4), key));
            }else if(key.startsWith("_lte_")){
                sqlWhere.append(MessageFormat.format(" and {0} <= #'{'{1}'}'", key.substring(5), key));
            }else if(key.startsWith("_isnull")){
                sqlWhere.append(MessageFormat.format(" and {0} is null", key.substring(7)));
            }else if(key.startsWith("_like_")){
                sqlWhere.append(MessageFormat.format(" and {0} like ''%{1}%''", key.substring(6), value));
            }else if(key.startsWith("_in_")){
                Object[] ins = (Object[])value;
                if(ins.length == 1) {
                    sqlWhere.append(MessageFormat.format(" and {0} in ({1})", key.substring(4), ins[0]));
                }else if (ins.length > 1){
                    StringBuilder values = new StringBuilder();
                    if (ins[0] instanceof String) {
                        for (Object oval : ins) {
                            values.append(MessageFormat.format("''{0}'',", oval.toString()));
                        }
                    } else {
                        for (Object oval : ins) {
                            values.append(MessageFormat.format("{0},", oval.toString()));
                        }
                    }

                    String lastValue = "";
                    if(values.length() > 0){
                        lastValue = values.substring(0,values.length() - 1);
                    }
                    sqlWhere.append(MessageFormat.format(" and {0} in ({1})'", key.substring(4), lastValue));
                }
            }else if(key.startsWith("_order_by")){
                orderBy.append(MessageFormat.format(" order by {0}",value));
                if(conditions.containsKey("_sort_rule")){
                    orderBy.append(" desc");
                }
            }
        }

        return sqlWhere.append(orderBy);
    }

    private String getTableName(Class clazz){
        if(clazz == null) throw new RuntimeException("使用通用mapper需传入实体class");
        String tableName = clazz.getSimpleName();
        Table annoTable = (Table) clazz.getAnnotation(Table.class);
        if(annoTable != null){
            tableName = annoTable.name();
        }

        return tableName;
    }

    private String getTableId(Class clazz){
        if(clazz == null) throw new RuntimeException("使用通用mapper需传入实体class");
        String id = null;
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Id.class)){
                id = field.getName();
                break;
            }
        }

        if(id == null) throw new RuntimeException(MessageFormat.format("类{0}中@Id不存在",clazz.getName()));

        return id;
    }

    private String getTransientProperties(Class entityClazz){
        StringBuilder transients = new StringBuilder();
        for(Field field : entityClazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Transient.class))
                transients.append(field.getName()).append(";");
        }

        return transients.toString();
    }


}
