package com.wcs.base.dict.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.Query;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wcs.base.dict.model.Dict;
import com.wcs.base.entity.BaseEntity;
import com.wcs.base.service.StatelessEntityService;

/**
 *
 */
@Stateless
public class DictService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    StatelessEntityService entityService;

	/**
     *  <p>Description:
	 * 通过 ParentCode 查询该parentCode下的所有子项，以 List 形式返回
     *  如：
     *  数据表结构dict为：
     * Code	Name	Value	ParentCode
     *   N01	   性别
     *   N0101	男	  1	N01
     *   N0102	女	  0	N01
     *
     *   Bean代码如下：
     *
     *   prvate Integer gender;
     *   private List<Dict> genderList = null;
     *   ... ...
     *   genderList = dictService.findWithList("N01");
	 *
     *  </p>
     *
	 * @param parentCode  字典元素的parent code
	 * @return  包含所有Dict子项
	 */
	public List<Dict> findWithList(String parentCode) {
		List<Dict> sonDicts = entityService.findList(Dict.class, "parentCode",
				parentCode);
		return sonDicts;
	}

	/**
     *  <p>Description:
	 * 通过 ParentCode 查询该parentCode下的所有子项，以 Map 形式返回
     *  如：
     *  数据表结构dict为：
     * Code	Name	Value	ParentCode
     *   N01	   性别
     *   N0101	男	  1	N01
     *   N0102	女	  0	N01
     *
     *   Bean代码如下：
     *
     *   prvate Integer gender;
     *   private Map<String, String> genderMap = null;
     *   ... ...
     *   genderMap = dictService.findWithMap("N01");
	 *
     *  页面代码为：
     *
     *  <h:selectOneMenu value="#{bookBean.gender}">
     *      <f:selectItems value="#{bookBean.genderMap}"></f:selectItems>
     *  </h:selectOneMenu>
     *
     *  </p>
     *
	 * @param parentCode  字典元素的parent code
	 * @return  Map的 key 为 SelectBox 的caption，Map的 value 为 SelectIBox 的 value，供标签 <f:selectItem> 和 <f:selectItems> 使用
	 */
	public Map<String, String> findWithMap(String parentCode) {
		List<Dict> dicts = entityService.findList(Dict.class, "parentCode",
				parentCode);
		Map<String, String> map = Maps.newHashMapWithExpectedSize(3);

		for (Dict d : dicts) {
			map.put(d.getName(), d.getValue());
		}
		return map;
	}
	
	/**
     *  <p>Description:
	 * 通过 ParentCode 查询该parentCode下的所有子项，以 List<SelectItem> 形式返回
     *  如：
     *  数据表结构dict为：
     * Code	Name	Value	ParentCode
     *   N01	   性别
     *   N0101	男	  1	N01
     *   N0102	女	  0	N01
     *
     *   Bean代码如下：
     *
     *   prvate Integer gender;
     *   private List<SelectItem> genderList = null;
     *   ... ...
     *   genderList = dictService.findWithSelectItem("N01");
	 *
     *  页面代码为：
     *
     *  <h:selectOneMenu value="#{bookBean.gender}">
     *      <f:selectItems value="#{bookBean.genderList}"></f:selectItems>
     *  </h:selectOneMenu>
     *
     *  </p>
     *
	 * @param parentCode  字典元素的parent code
	 * @return  List<SelectItem>中的 SelectItem 为JSF 的类，它包含 SelectBox 的 value/caption，供标签 <f:selectItem> 和 <f:selectItems> 使用
	 */
	public List<SelectItem> findWithSelectItem(String parentCode) {
		List<Dict> dicts = entityService.findList(Dict.class, "parentCode",
				parentCode);
		List<SelectItem> items = Lists.newArrayList();

		for (Dict d : dicts) {
			items.add(new SelectItem(d.getCode(), d.getName()));
		}
		return items;
	}
	
	/**
	 * 根据hql，查询该parentCode下的所有子项 以 List<SelectItem> 形式返回
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> findSelectItemByHql(String hql) {
		Query query = entityService.createQuery(hql);
		List<Dict> dicts = query.getResultList();
		if(dicts == null || dicts.size() == 0) {
			return null;
		}
		
		List<SelectItem> items = Lists.newArrayList();
		for (Dict d : dicts) {
			items.add(new SelectItem(d.getCode(), d.getName()));
		}
		return items;
	}
	
	/**
	 * <p>Description:
     *  获取一个实体类两个属性，将其放在一个List中，其结构为 List<Object[2]>
     *
     * </p>
	 * @param clazz 实体类的类型
	 * @param property1 实体类的属性名
	 * @param property2 实体类的属性名
	 * @return  返回值的结构为 List<Object[2]>，其中 Object[0] 为property1，Object[1] 为property2
	 */
	public List findTablePropertiesWithList(Class clazz,
			String property1, String property2) {
		StringBuilder sb = new StringBuilder("select ");
		sb.append(property1).append(",").append(property2).append(" from ")
				.append(clazz.getSimpleName());

		return entityService.createQuery(sb.toString()).getResultList();
	}

	/**
	 * <p>Description:
     *  获取一个实体类两个属性，将其放在一个Map中，其结构为 Map<property1, property2>
     *
     * </p>
	 * @param clazz 实体类的类型
	 * @param property1 实体类的属性名
	 * @param property2 实体类的属性名
	 * @return  返回值的结构为 Map<property1, property2>
	 */
	public Map<Object, Object> findTablePropertiesWithMap(
			Class<? extends BaseEntity> clazz, String property1,
			String property2) {
		List dicts = findTablePropertiesWithList(clazz, property1, property2);

        Map<Object, Object> map = arrayToMap(dicts);

		return map;
	}

	/**
	 * <p>Description:
     *  获取一个实体类两个属性，将其放在一个Map中，其结构为 List<SelectItem>
     *
     * </p>
	 * @param clazz 实体类的类型
	 * @param property1 实体类的属性名
	 * @param property2 实体类的属性名
	 * @return  List<SelectItem>中的 SelectItem 为JSF 的类，它包含 SelectBox 的 value/caption，供标签 <f:selectItem> 和 <f:selectItems> 使用
	 */
	public List<SelectItem> findTablePropertiesWithSelectItem(
			Class clazz, String property1,
			String property2) {
		List dicts = findTablePropertiesWithList(clazz, property1, property2);
		List<SelectItem> items = new ArrayList<SelectItem>();
		if(dicts != null){
		     items = arrayToListSelectItem(dicts);
	        
		}
		return items;
	}

    /**
     *  将 Dict 实体列表转为一个 List<SelectItem>中的 SelectItem 为JSF 的类，它包含 SelectBox 的 value/caption，供标签 <f:selectItem> 和 <f:selectItems> 使用
     * @param dicts  Dict 实体列表
     * @return  List<SelectItem>对象
     */
    public static List<SelectItem> arrayToListSelectItem(List dicts) {
        List<SelectItem> items = Lists.newArrayList();
        if(dicts != null){
            for (int i = 0; i < dicts.size(); i++) {
                Object[] row = (Object[]) dicts.get(i);
                items.add(new SelectItem(row[0], row[1]+""));
            }
        }
       
        return items;
    }

    /**
     *  将 Dict 实体列表转为一个Map， Map的 key 为 SelectBox 的caption，Map的 value 为 SelectIBox 的 value，供标签 <f:selectItem> 和 <f:selectItems> 使用
     * @param dicts
     * @return
     */
    public static Map<Object, Object> arrayToMap(List dicts) {
        Map<Object, Object> map = Maps.newHashMapWithExpectedSize(3);

        for (int i = 0; i < dicts.size(); i++) {
            Object[] row = (Object[]) dicts.get(i);
            map.put(row[0], row[1]);
        }
        return map;
    }
    
	public String getDictString(String parentCode,String value){
		List<Dict> dictList = this.findWithList(parentCode);
		
		for(Dict dict:dictList){
			if(null != dict.getValue() && value.trim().equals(dict.getValue().trim())){
				return dict.getName();
			}
		}
		return "";
	}
}
