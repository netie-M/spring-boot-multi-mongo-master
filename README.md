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
    
        spring.mail.host=mail.quarkfinance.com
        spring.mail.port=587
        spring.mail.username=cobra-monitor
        spring.mail.password=)A71ZMxb=7
        spring.mail.test-connection=false
        spring.mail.properties.mail.debug=false
        #spring.mail.properties.mail.smtp.auth=true
        #spring.mail.properties.mail.smtp.starttls.enable=true
        #spring.mail.properties.mail.smtp.starttls.required=true
        #spring.mail.properties.mail.event.executor=
        spring.mail.properties.mail.smtp.connecttimeout=5000
        spring.mail.properties.mail.smtp.timeout=3000
        spring.mail.properties.mail.smtp.writetimeout=5000

   
       config.posp_private_key:       
       "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAINZLTEg94hR1OzSPV3yJF5ueInF8lzErIrrspS1ELMY0G6JF6vz9OK0p9Ds0QX0S8Mah/hRHqpqrfh9+nDHbjqlsul20TgRnq9zDVf4f+llXxcWiv6lfR1WqnLM1ZQLJtbPlXDzymj6FFsdyEt8CSNIwQ7h4BUvgi7pFJ8ZEZ9VAgMBAAECgYAD9EpVAU4Sb4w+ePHaAzEvGppMY4YxXsZnBEODEJRpH+UefsgUqCqGLLQkqQx95mXlgMX5JtAKh12nbnt8q7RYypKjLmIKg77E+4ORZBpI3RRVpSJ/B5/cyUidGGagZTYsAZ0ZKjYNZsc55A4Rz1bpZtbT75rl1zyBLhpeC2XJoQJBAMazkdzJpD5NdnBkZs7Bqaj2R8GpS5HkvBO5matuSagMJnX7ZH4rtkp/aSl4ZNNtXrNhtbwNXd6C/CbvARZHjf0CQQCpOYC69Sp+rq3hPgn9BVt0oYwPL8nlrVitzeVTQ6KjpZ/mPSzbcl+Je6++mZ4TCnrJgLk8z4tQLG11AODdDao5AkAijryBj7g29bXxmiSfNONS9XJJZi40c3maXJ8zR30b8vEFd/FkWCneDwa1JbEzbEQaIpY+3HxE5LcB9nNT8qHpAkB5fdIg52Dh4HDvdAXhxJXbTxvpz0po/aHb7iVFORqr2H3K44Kv7hYO82DCOzGUxAJRZnwW335KSgsvZDkMGwJpAkEAvxJhxIofvGCNBXYTLB+Xl/gwl0GcRBlbBuv0dlQ9Q4U/59vtY8sIe0Rh7d8+qc1LGWbMFImkhQoVfAtuDEShBg=="
   
      var Jsrsasign = require('jsrsasign');
      var rsa = Jsrsasign.KEYUTIL.getKeyFromPlainPrivatePKCS8PEM("-----BEGIN PRIVATE KEY-----"+config.posp_private_key+"-----END PRIVATE KEY-----");
    var sign = Jsrsasign.hex2b64(rsa.sign(clearText,"sha1"));
    
    var signature = new Jsrsasign.Signature({"alg": "SHA1withRSA", "prov": "cryptojs/jsrsa"});
    signature.init(prvKey);
    var sign = Jsrsasign.hex2b64(signature.signString(clearText));
    
    
    let rsa_private = new Jsrsasign.RSAKey();
    rsa_private.readPKCS8PrvKeyHex(Jsrsasign.b64tohex(config.posp_private_key));
    let rsa_public =  new Jsrsasign.RSAKey();
    rsa_public.readPKCS8PubKeyHex(Jsrsasign.b64tohex(config.pospForBorrowerLite_public_key));

    const sign = function (clearText) {
        return Jsrsasign.hex2b64(rsa_private.sign(clearText,"sha1"));
    }
    const verify = function (clearText,sign) {
        return rsa_public.verify(clearText,Jsrsasign.b64tohex(sign));
    }
    
      以上方式获得的签名是一致的;
      
      JS 加密
      var Jsrsasign = require('jsrsasign');
      let JSEncrypt = new jsencrypt.JSEncrypt();
      JSEncrypt.setPublicKey(config.publickey);
      let cipher = JSEncrypt.encrypt(clearText);
      
      java解密
      RSA/ECB/PKCS1Padding
      
      
      this.setState({"page":"<h1>ERROR</h1>"})
      var page={__html:this.state.page};
      <div dangerouslySetInnerHTML={page} ></div>
      
      react 页面跳转并传参数
            this.props.history.push( {
                pathname:'/transfer',
                state:response.data,
            });
            
            react 页面加载新元素
            ReactDOM.render(<iframe frameBorder="0" scrolling="auto" height="100%" width="100%" srcDoc={html}></iframe>,document.getElementById(elementId));
            
            
            
            @JsonIgnore注解用来忽略某些字段，可以用在Field或者Getter方法上，用在Setter方法时，和Filed效果一样。这个注解只能用在POJO存在的字段要忽略的情况，不能满足现在需要的情况。
        @JsonIgnoreProperties(ignoreUnknown = true)，将这个注解写在类上之后，就会忽略类中不存在的字段，可以满足当前的需要。这个注解还可以指定要忽略的字段。使用方法如下：
        @JsonIgnoreProperties({ "internalId", "secretKey" })
            正确在class上加
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class tes
 
        或者代码控制
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.readValue(json,cls);
        
        http://san-yun.iteye.com/blog/2065732
