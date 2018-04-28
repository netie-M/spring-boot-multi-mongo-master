package com.quark.cobra.posp.service.impl;

import com.quark.cobra.posp.domain.CreatAccBindBo;
import com.quark.cobra.posp.enums.ApplicationCode;
import com.quark.cobra.posp.enums.BankCardType;
import com.quark.cobra.posp.enums.BuCode;
import com.quark.cobra.posp.enums.CheckType;
import com.quark.cobra.posp.enums.GatewayType;
import com.quark.cobra.posp.enums.InterfaceSync;
import com.quark.cobra.posp.enums.OperAccountType;
import com.quark.cobra.posp.enums.UserRole;
import com.quark.cobra.posp.service.IPospService;
import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class PospServiceImpl implements IPospService{
    @Resource(name="defaultRestTemplate")
    private RestTemplate restTemplate;

    @Override
    public void creatAccBind() {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(""
            ,CreatAccBindBobuilder("","","","","","","","")
            ,String.class);
        System.out.println(responseEntity.getBody());

    }


    private CreatAccBindBo CreatAccBindBobuilder(String idName,String idNo,String bankCode,String bankName,String bankCardNo,String mobile,String returnUrl,String notifyUrl){
        DateTime now = DateTime.now();
        String interfaceId =  "QF-POSP-DEPO-CREATE-ACC-BIND-0001";
        String merchantCode = "10" ;
        BuCode buCode = BuCode.quark;
        String operAccount = "C10";
        ApplicationCode applicationCode = ApplicationCode.posp;
        String tradeNo = new StringBuffer()
            .append(merchantCode)
            .append(buCode.getCode())
            .append(applicationCode.getCode())
            .append(interfaceId.replaceAll("[^0-9]",""))
            .append(RandomUtils.nextInt(1,9999))
            .toString();
        return  CreatAccBindBo.builder()
            .interfaceId(interfaceId)
            .gatewayType(GatewayType.HenFeng)  //TODO
            .merchantCode(merchantCode)
            .buCode(buCode)
            .applicationCode(applicationCode)
            .interfaceSync(InterfaceSync.gateway)
            .tradeNo(tradeNo)
            .tradeDate(now.toString("yyyy-MM-dd"))
            .tradeTime(now.toString("HH:mm:ss"))
            .name(idName)
            .certType("101")
            .certNo(idNo)
            .bankCode(bankCode)
            .bankName(bankName)
            .bankCardNo(bankCardNo)
            .bankCardType(BankCardType.debit)
            .reservedPhone(mobile)
            .userRole(UserRole.borrower) //TODO
            .operAccount(operAccount)
            .operAccountType(OperAccountType.open)
            .checkType(CheckType.four_factors)
//            .authList(null)
//            .authDate()
//            .authAmount()
            .returnUrl(returnUrl)
            .notifyUrl(notifyUrl)
//            .remark()
            .build();
    }
}
