package com.yangzhuo.dozer;

import com.yangzhuo.dozer.dto.JeDocCreateDTO;
import com.yangzhuo.dozer.dto.JeDocCreateRespDTO;
import com.yangzhuo.dozer.dto.JeHeaderCreateDTO;
import com.yangzhuo.dozer.dto.JeLineCreateDTO;
import com.yangzhuo.dozer.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DozerTest {

    DozerBeanMapper dozer = new DozerBeanMapper();
    
   @Test
   public void testDozer() throws ParseException {
       JeDocCreateDTO jeDocCreateDTO = createDTO();

       JeDocCreateRespDTO jeDocCreateRespDTO = dozer.map(jeDocCreateDTO, JeDocCreateRespDTO.class);

       System.out.println(jeDocCreateRespDTO.toString());
   }

    @Test
    public void testBeanUtil() throws ParseException {
        JeDocCreateDTO jeDocCreateDTO = createDTO();

        JeDocCreateRespDTO jeDocCreateRespDTO = BeanUtils.dtoToDo(jeDocCreateDTO, JeDocCreateRespDTO.class);

        System.out.println("123");
    }
    
    @Test
    public void test1() throws ParseException {
        JeDocCreateDTO jeDocCreateDTO = createDTO();
        String field = getFieldValueByFieldName("Operation", jeDocCreateDTO);
        if (StringUtils.isNotEmpty(field)) {
            System.out.println("123");
        } else {
            System.out.println("321");
        }
        System.out.println(field);
    }

    @Test
    public void test2() throws ParseException {
        List<Map<String, Object>> mapList =  createMap();
        for (int i = 0; i < mapList.size(); i++) {
            String field = String.valueOf(mapList.get(i).get("name"));
            System.out.println("field: " + field);
        }
        
    }
    
    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param object
     * @return
     */
    private String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            
            return  (String)field.get(object);
        } catch (Exception e) {
          
            return null;
        } 
    }
   
   
   
    JeDocCreateDTO createDTO() throws ParseException {
        JeDocCreateDTO jeDocCreateDTO = new JeDocCreateDTO();
        JeHeaderCreateDTO jeHeaderCreateDTO = new JeHeaderCreateDTO();
        List<JeLineCreateDTO> jeLineCreateDTOList = new ArrayList<>();
        jeHeaderCreateDTO.setLedgerCode("1234");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date = simpleDateFormat.parse("2019-09-02");
        jeHeaderCreateDTO.setGlDate(date);
        Map<String,String> addFields = new HashMap<>();
        addFields.put("name", "yz");
        addFields.put("age", "25");
        jeHeaderCreateDTO.setAddFields(addFields);
        jeDocCreateDTO.setJeHeader(jeHeaderCreateDTO);
       
        JeLineCreateDTO jeLineCreateDTO1 = new JeLineCreateDTO();
        jeLineCreateDTO1.setEnteredCr(BigDecimal.valueOf(100.0));
        jeLineCreateDTO1.setEnteredDr(BigDecimal.valueOf(0.0));
        Map<String,String> addFields1 = new HashMap<>();
        addFields1.put("name", "lr");
        addFields1.put("age", "25");
        jeLineCreateDTO1.setAddFields(addFields1);
        jeLineCreateDTOList.add(jeLineCreateDTO1);

        JeLineCreateDTO jeLineCreateDTO2 = new JeLineCreateDTO();
        jeLineCreateDTO2.setEnteredCr(BigDecimal.valueOf(0.0));
        jeLineCreateDTO2.setEnteredDr(BigDecimal.valueOf(100.0));
        Map<String,String> addFields2 = new HashMap<>();
        addFields2.put("name", "zz");
        addFields2.put("age", "26");
        jeLineCreateDTO2.setAddFields(addFields2);
        jeLineCreateDTOList.add(jeLineCreateDTO2);
        
        jeDocCreateDTO.setJeLineList(jeLineCreateDTOList);

        jeDocCreateDTO.setOperation("create");
        
        return jeDocCreateDTO;
    }
    
    public List<Map<String, Object>> createMap() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> params1 = new HashMap<>(16);
        Object object1 = new Object();
        object1 = "yz";
        params1.put("name", object1);
        Object object2 = new Object();
        object2 = "25";
        params1.put("age", object2);
        Object object3 = new Object();
        object3 = "male";
        params1.put("sex", object3);
        mapList.add(params1);

        Map<String, Object> params2 = new HashMap<>(16);
        Object object4 = new Object();
        object4 = "lr";
        params2.put("name", object4);
        Object object5 = new Object();
        object5 = "25";
        params2.put("age", object5);
        Object object6 = new Object();
        object6 = "female";
        params2.put("sex", object6);
        mapList.add(params2);

        Map<String, Object> params3 = new HashMap<>(16);
        Object object7 = new Object();
        object7 = "zz";
        params3.put("name", object7);
        Object object8 = new Object();
        object8 = "26";
        params3.put("age", object8);
        Object object9 = new Object();
        object9 = "male";
        params3.put("sex", object9);
        mapList.add(params3);
        
        return mapList;
        
    }
    
    
}
