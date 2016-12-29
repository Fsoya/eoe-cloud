package top.sdaily.core.mybatis;

import java.util.HashMap;

/**
 * 查询条件支持类
 * Created by soya on 2016/10/29.
 */
@SuppressWarnings("unchecked")
public class Conditions<String,Object> extends HashMap {

    private Class enetityClazz ;

    public Conditions(){
        super();
    }

    public Conditions(Class clazz) {
        super();
        this.enetityClazz = clazz;
    }

    /**
     * @Id = value
     * @param value
     * @return
     */
    public Conditions<String,Object> id(String value){
        this.put("_id",value);
        return this;
    }

    /**
     * a = b
     * @param key
     * @param value
     * @return
     */
    public Conditions<String,Object> eq(String key,Object value){
        this.put("_eq_" + key,value);
        return this;
    }

    /**
     * a > b
     * @param columnA
     * @param value
     * @return
     */
    public Conditions<String,Object> gt(String columnA,Object value){
        this.put("_gt_" + columnA,value);
        return  this;
    }

    /**
     * a >= b
     * @param columnA
     * @param value
     * @return
     */
    public Conditions<String ,Object> gte(String columnA,Object value){
        this.put("_gte_" + columnA,value);
        return  this;
    }

    /**
     * a < b
     * @param columnA
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    public Conditions<String,Object> lt(String columnA,Object value){
        this.put("_lt_" + columnA,value);
        return  this;
    }


    /**
     * a <= b
     * @param columnA
     * @param value
     * @return
     */
    public Conditions<String ,Object> lte(String columnA,Object value){
        this.put("_lte_" + columnA,value);
        return  this;
    }

    /**
     * columnA like '%value%'
     * @param columnA
     * @param value
     * @return
     */
    public Conditions<String ,Object> like(String columnA,String value){
        this.put("_like_" + columnA,value);
        return  this;
    }

    /**
     * columnA in (value..)
     * @param columnA
     * @param value
     * @return
     */
    public Conditions<String ,Object> in(String columnA,Object... value){
        this.put("_in_" + columnA,value);
        return  this;
    }


    /**
     * order by
     * @param column
     * @return
     */
    public Conditions<String,Object> addOrderBy(String column){
        this.put("_order_by",column);
        return this;
    }

    /**
     * desc
     * @return
     */
    public Conditions<String,Object> sortDesc(){
        this.put("_sort_rule","desc");
        return this;
    }

    /**
     * column is null
     * @param column
     * @return
     */
    public Conditions<String,Object> isNull(String column){
        this.put("_isnull",column);
        return this;
    }

    /**
     * limit ...
     * @param page
     * @return
     */
    public Conditions<String,Object> page(Page page){
        this.put("_page",page);
        return this;
    }

    public Class getEnetityClazz() {
        return enetityClazz;
    }

    public void setEnetityClazz(Class enetityClazz) {
        this.enetityClazz = enetityClazz;
    }
}
