# spring-boot-multi-mongo-master

// 添加索引

@CompoundIndexes({
    @CompoundIndex(name = "mobile_client_index", def = "{client : 1, mobile : 1}")
})

 @Indexed(name="mobile_index")
 
 
  // where mobile = "186*" and (id = null or id = "123456")
  
 Query query = Query.query(Criteria.where("mobile").is("186*").orOperator(Criteria.where("idNo").is("123456"),Criteria.where("idNo").is(null)));
 
oauth token 有效期总结

设置access_token 有效时间为100 refresh_token 有效时间为150

spring_boot_oauth机制如下:
1. 首次登陆
返回报文:{"access_token":"d3014832-057a-45bc-bd26-e4ad13ead324","token_type":"bearer","refresh_token":"65b548b4-768a-4d17-b890-9dbec5c80299","expires_in":99,"scope":"read write openid"}
2.在100秒内再次登陆
返回报文:{"access_token":"d3014832-057a-45bc-bd26-e4ad13ead324","token_type":"bearer","refresh_token":"65b548b4-768a-4d17-b890-9dbec5c80299","expires_in":77,"scope":"read write openid"}
注意事项: access_token与refresh_token相同,expires_in会减少
3.在100秒内使用access_token,交易正常
4.在100~150秒内使用access_token,返回报文:{"error": "invalid_token","error_description": "Invalid access token: d3014832-057a-45bc-bd26-e4ad13ead324"}
5.在100~150秒内刷新token,返回报文:{"access_token":"8c7d9e56-069f-4941-824b-d7c40a982e02","token_type":"bearer","refresh_token":"65b548b4-768a-4d17-b890-9dbec5c80299","expires_in":99,"scope":"read write openid"}
注意事项:access_token 有更新,但refresh_token未改变
6.在150秒之后刷新token,返回报文:{"error": "invalid_grant","error_description": "Invalid refresh token: 65b548b4-768a-4d17-b890-9dbec5c80299"}
7.在刷新token完成后的100秒使用access_token,交易正常
8. 刷新token完成后100秒,再次登陆返回报文:{"access_token":"8c7d9e56-069f-4941-824b-d7c40a982e02","token_type":"bearer","refresh_token":"65b548b4-768a-4d17-b890-9dbec5c80299","expires_in":65,"scope":"read write openid"}
注意事项:返回报文与刷新token报文一致,仅有效时间减少,但极有可能refresh_token已经失效;若失效在此次access_token失效后,不可再次刷新;
9.若需要新的access_token需要在原有的失效以后再次登陆;


