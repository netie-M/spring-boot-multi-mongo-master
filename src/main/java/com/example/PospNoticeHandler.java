package com.quark.cobra.posp.service;

import com.quark.cobra.posp.constants.NoticeEvent;
import com.quark.cobra.posp.domain.BaseReqBo;
import com.quark.cobra.posp.domain.CreatAccBindReqBo;
import com.quark.cobra.posp.domain.DepoBindCardBo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.ReplyTo;
import reactor.spring.context.annotation.Selector;

@Consumer
public class PospNoticeHandler {
    @Autowired
    private EventBus eventBus;//不可删除!!!

    @Selector(value= NoticeEvent.CREATE_ACC_BIND)
    @ReplyTo(value = "reply")
    public String  onCreatAccBind(BaseReqBo bo){
        System.out.println("CREATE_ACC_BIND:"+bo.getTradeNo()+":"+ Thread.currentThread().getId());
        try {
            System.out.println("CREATE_ACC_BIND:sleep");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("CREATE_ACC_BIND:"+bo.getTradeNo());
        return "hi,i m finishs";
    }

    @Selector(value= NoticeEvent.BIND_CARD)
    public void onBindCard(BaseReqBo bo){
        System.out.println("BIND_CARD:"+bo.getTradeNo() +":"+ Thread.currentThread().getId());
    }

    @Selector(value= "reply")
    public void reply(String msg){
        System.out.println("reply:"+msg +":"+ Thread.currentThread().getId());
    }
}
