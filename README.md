# DatawayDemo
Dataway demo
参考博客：https://blog.csdn.net/qq_18244417/article/details/120320489


### Dataway 具体使用
参考Dataway Document


#### 获取某个时间之前的用户登录信息
查询信息
```
{
  "user_id": 2,
  "beforetime": "2020-10-15"
}
```
构建函数
```
// a new DataQL Query.
import 'net.hasor.dataql.fx.basic.DateTimeUdfSource' as time;
// select * from test_login where user_id = #{user_id} and date < #{time};
// return time.parser("2020-04-28 10:28:44", "yyyy-MM-dd hh:mm:ss")
// var beforetime = "2020-10-15";
// var user_id = 2;
var timeparser = (timestring)->{
    return time.parser(timestring, "yyyy-MM-dd");
};

var getUser = @@sql(user_id,beforetime)
<%
    select * from test_login where `user_id` = #{user_id} and `date` < #{beforetime} 
%>;

return getUser(${user_id},timeparser(${beforetime}))=> [{
    "user_id":user_id,
    "client_id":client_id,
    "time":time.format(date, "yyyy-MM-dd")
}
]
```
#### 分页查询
```
// a new Query.
// a new DataQL Query.
// page Segment Query.
hint FRAGMENT_SQL_QUERY_BY_PAGE = true
var dimSQL = @@sql(userName)<%
    select * from user where `name` like concat('%',#{userName},'%')
%>;
var queryPage = dimSQL(${userName});
run queryPage.setPageInfo({
    "pageSize"    : 5, // 页大小
    "currentPage" : ${pageNumber}  // 自定义想要获取的页面
});
//用Json结构返回结果
return {
    "pageInfo": queryPage.pageInfo(),
    "pageData":queryPage.data()
}
//返回前端处理分页的时候通常所需的分页信息，有了分页信息可以生成前端的页面序号按钮
// return queryPage.pageInfo()
```
#### 获取物品的销量
// a new Query.
var queryItemSell = @@sql(itemid)<%
select g.id,g.name,t1.total
from
(
select goods_id,sum(count) as total
from trans 
where goods_id=#{itemid}
group by goods_id
)t1
left join goods g
on t1.goods_id=g.id;
%>
return queryItemSell(${itemid}) =>
 [{
    "item_id":id,
    "item_name":name,
    "item_sell_number":total
}
]
