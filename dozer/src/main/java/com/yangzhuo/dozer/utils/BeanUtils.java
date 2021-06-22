package com.yangzhuo.dozer.utils;


/**
 * @desc 实体类转换工具
 * 
 * @author zhuo.yang@hand-china.com
 */

public class BeanUtils<Dto, Do> {

	/**
	 * dto 转换为 entity 工具类
	 * 
	 * @param dtoEntity
	 * @param doClass
	 * @return do
	 */
	public static <Do> Do dtoToDo(Object dtoEntity, Class<Do> doClass) {
		// 判断dto是否为空!
		if (dtoEntity == null) {
			return null;
		}
		// 判断DoClass 是否为空
		if (doClass == null) {
			return null;
		}
		try {
			Do newInstance = doClass.newInstance();
			org.springframework.beans.BeanUtils.copyProperties(dtoEntity, newInstance);
			// Dto转换Do
			return newInstance;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * entity 转换为 Dto 工具类
	 * 
	 * @param doEntity
	 * @param dtoClass
	 * @return dto
	 */
	public static <Dto> Dto doToDto(Object doEntity, Class<Dto> dtoClass) {
		// 判断dto是否为空!
		if (doEntity == null) {
			return null;
		}
		// 判断DoClass 是否为空
		if (dtoClass == null) {
			return null;
		}
		try {
			Dto newInstance = dtoClass.newInstance();
			org.springframework.beans.BeanUtils.copyProperties(doEntity, newInstance);
			// Dto转换Do
			return newInstance;
		} catch (Exception e) {
			return null;
		}
	}
}
