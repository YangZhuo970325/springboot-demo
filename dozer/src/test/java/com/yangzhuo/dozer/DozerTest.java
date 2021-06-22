package com.yangzhuo.dozer;

import com.yangzhuo.dozer.dto.JeDocCreateDTO;
import com.yangzhuo.dozer.dto.JeDocCreateRespDTO;
import com.yangzhuo.dozer.dto.JeHeaderCreateDTO;
import com.yangzhuo.dozer.dto.JeLineCreateDTO;
import com.yangzhuo.dozer.utils.BeanUtils;
import org.dozer.DozerBeanMapper;
import org.junit.Test;

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
       
       return jeDocCreateDTO;
       
       
   }
}
