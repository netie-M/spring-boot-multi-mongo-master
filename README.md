# spring-boot-multi-mongo-master

// 添加索引

@CompoundIndexes({
    @CompoundIndex(name = "mobile_client_index", def = "{client : 1, mobile : 1}")
})

 @Indexed(name="mobile_index")
 
 
  // where mobile = "186*" and (id = null or id = "123456")
  
 Query query = Query.query(Criteria.where("mobile").is("186*").orOperator(Criteria.where("idNo").is("123456"),Criteria.where("idNo").is(null)));
 
    
     /**
     *  去掉_class列
     * @param mongoDbFactory
     * @param mappingContext
     * @return
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory mongoDbFactory,
                                                       MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext){
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory),mappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
     //http://dawn-sky.iteye.com/blog/1343659
     @Bean
    public MongoClientOptions mongoClientOptions(){
        return MongoClientOptions.builder()
            .connectionsPerHost(100)//设置每个主机的最大连接数
            .connectTimeout(10000)//设置连接超时
            .maxConnectionIdleTime(0)//设置池连接的最大空闲时间
            .maxConnectionLifeTime(0)//设置池连接的最大生命时间
            .maxWaitTime(120000)//设置的最长时间，线程阻塞等待连接
            .minConnectionsPerHost(0)//设置每个主机的最小连接数
            .socketTimeout(0)//设置套接字超时
            .threadsAllowedToBlockForConnectionMultiplier(5)//设置允许阻塞等待连接的线程数量的倍数
            .socketKeepAlive(true)
            .build();
    
 
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

{
public static void main(String[] args) {
        String tradeNo =  "10C1010001234201804191128428304538";
        System.out.println(tradeNo);
        System.out.println(tradeNo.replaceAll("(\\w{2})(\\w{32})","$1"));
        System.out.println(tradeNo.replaceAll("(\\w{2})(\\w{3})(\\w{29})","$2"));
        System.out.println(tradeNo.replaceAll("(\\w{5})(\\w{4})(\\w{25})","$2"));
        System.out.println(tradeNo.replaceAll("(\\w{13})(\\w{8})(\\w{13})","$2"));
        System.out.println(tradeNo.replaceAll("(\\w{21})(\\w{6})(\\w{7})","$2"));

        System.out.println(tradeNo.replaceAll("(\\w{2})(\\w{32})","$1"));
        System.out.println(tradeNo.replaceAll("\\w{2}(\\w{3})\\w{29}","$1"));
        System.out.println(tradeNo.replaceAll("\\w{5}(\\w{4})\\w{25}","$1"));
        System.out.println(tradeNo.replaceAll("\\w{13}(\\w{8})\\w{13}","$1"));
        System.out.println(tradeNo.replaceAll("\\w{21}(\\w{6})\\w{7}","$1"));
    }
    }
