# spring-boot-multi-mongo-master

// 添加索引

@CompoundIndexes({
    @CompoundIndex(name = "mobile_client_index", def = "{client : 1, mobile : 1}")
})

 @Indexed(name="mobile_index")
 
 
  // where mobile = "186*" and (id = null or id = "123456")
  
 Query query = Query.query(Criteria.where("mobile").is("186*").orOperator(Criteria.where("idNo").is("123456"),Criteria.where("idNo").is(null)));
        
