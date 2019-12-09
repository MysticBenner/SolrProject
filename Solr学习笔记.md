#### 全文检索服务器 Solr

##### 1.Solr的介绍

###### **1.1 什么是Solr**

Solr是apache的开源项目，使用Java开发，基于Lucene的全文检索服务器

###### **1.2 与Lucene的关系**

Solr比Lucene提供了更多的查询语句，而且它可扩展、可配置，同时它对Lucene的性能进行了优化

Lucene是一个全文检索引擎工具包，它只是一个jar包，不能独立运行对外提供服务。Solr是一个全文检索服务器，它可以单独运行在servlet容器，可单独对外提供搜索和索引功能。

总的来说，Solr开发比Lucene更便捷。

###### **1.3 实现全文检索的原理**

![Solr运行图解](D:\Solr笔记\Slor学习过程截图\Solr运行图解.jpg)

索引流程：solr客户端（浏览器、java程序）可以向solr服务端发送POST请求，请求内容是包含Field等信息的一个xml文档，通过该文档，solr实现对索引的维护（增删改）

搜索流程：solr客户端（浏览器、java程序）可以向服务端发送GET请求，solr服务器返回一个xml文档

solr同样没有视图渲染的功能

##### 2.Solr安装配置

###### **2.1安装准备**

官网安装，同时要有Lucene包（例子用的是4.10.3的远古版本），下面是下载好的solr目录结构：

![](D:\Solr笔记\Slor学习过程截图\solr目录详解.jpg)

**2.2运行环境**

- jdk：1.7以上
- Solr：4.10.3

![](D:\Solr笔记\Slor学习过程截图\solr对应war包.jpg)

![](D:\Solr笔记\Slor学习过程截图\solrhome的例子目录.jpg)

- Mysql：5X

![](D:\Solr笔记\Slor学习过程截图\数据库脚本.jpg)

- Web服务器：Tomcat7

**2.3 正式安装**

- 安装tomcat
- 将以下war包拷贝到tomcat的webapps目录下

![](D:\Solr笔记\Slor学习过程截图\solrwar包.jpg)

- 在tomcat中解压缩包，解压完后将war包删除

![](D:\Solr笔记\Slor学习过程截图\解压后的solr包.jpg)

- 再往解压后的solr的lib文件夹下添加jar包

![](D:\Solr笔记\Slor学习过程截图\添加目录.jpg)

- 添加log4j.properties到classes文件夹下，没有classes文件夹则创建一个

![](D:\Solr笔记\Slor学习过程截图\log4j.jpg)

![](D:\Solr笔记\Slor学习过程截图\log4j复制目录.jpg)

- 编辑tomcat中solr中webapp下的webxml，指定solrhome的目录

![](D:\Solr笔记\Slor学习过程截图\编辑web.xml目录.jpg)

**Solrcore和Solrhome的安装**

**Solrcore和Solrhome**：Solrhome是solr服务运行的主目录，一个solrhome目录里面包含多个solrcore目录，一个solecore目录里面包含了一个solr实例运行时所需要的配置文件和数据文件。

![](D:\Solr笔记\Slor学习过程截图\solrhome的目录结构.jpg)

每一个solrcore都可以单独对外提供搜索和索引服务，多个solrcore之间没有任何关系。

![](D:\Solr笔记\Slor学习过程截图\solrcore目录结构.jpg)

- 首先安装solrhome，直接创建solrhome文件夹，将solr文件夹下的文件复制粘贴即可

![](D:\Solr笔记\Slor学习过程截图\solrhome需要拷贝的文件.jpg)

![](D:\Solr笔记\Slor学习过程截图\solrhome复制到的目录.jpg)

- 在拷贝后的文件夹collection下找到solrconfig文件进行配置，主要配置三个标签：lib标签、datadir标签和requestHandle标签。如果对该文件不进行配置则使用默认配置项。

  ![](D:\Solr笔记\Slor学习过程截图\对solrconfig进行配置.jpg)

  ![](D:\Solr笔记\Slor学习过程截图\对依赖包进行扩展.jpg)

  ![依赖包复制到的目录](D:\Solr笔记\Slor学习过程截图\依赖包复制到的目录.jpg)

  - lib标签

    Solrcore需要添加一些扩展依赖包，这些依赖包需要lib标签进行操作

    ![](D:\Solr笔记\Slor学习过程截图\修改lib目录.jpg)

  - datadir标签

    每个SolrCore都有自己的索引文件目录，默认在SolrCore目录下的data下，data数据目录下包括了index索引目录和tlog日志文件目录，如果不想使用默认的目录可以通过solrConfig.xml对目录进行更改

    ![](D:\Solr笔记\Slor学习过程截图\solrcore的文件目录.jpg)

  - requestHandler标签

    requestHandler请求处理器，定义了索引和搜索的访问方式，通过/update维护索引，可以完成索引的添加、修改和删除操作。

    ![](D:\Solr笔记\Slor学习过程截图\requestHandler标签.jpg)

至此安装成功，开始正式的Solr编程之旅

##### 3.Solr使用

启动solr服务 http://localhost:8080/solr

![](D:\Solr笔记\Slor学习过程截图\solr界面介绍.jpg)

- **Dashboard**

  仪表盘，显示了该Solr实例开始启动运行的时间、版本、系统资源、jvm等信息。

- **Logging**

  Solr运行日志信息

- **Cloud**

  Cloud即SolrCloud，即Solr云（集群），当使用Solr Cloud模式运行时会显示此菜单，该部分功能在第二个项目，即电商项目会讲解。

- **Core Admin**

  Solr Core的管理界面。在这里可以添加SolrCore实例。

- **java properties**

  Solr在JVM 运行环境中的属性信息，包括类路径、文件编码、jvm内存设置等信息。

- **Tread Dump**

  显示Solr Server中当前活跃线程信息，同时也可以跟踪线程运行栈信息。

- **Core selector（重点）**

![core selector](D:\Solr笔记\Slor学习过程截图\core selector.jpg)

- **Analysis（重点）**

  通过此界面可以测试索引分析器和搜索分析器的执行情况。 注：solr中，**分析器是绑定在域的类型中的。**

![analysis](D:\Solr笔记\Slor学习过程截图\analysis.jpg)

- **dataimport**

  可以定义数据导入处理器，从关系数据库将数据导入到Solr索引库中。 默认没有配置，需要手工配置。

- **Document（重点）**

  通过/update表示更新索引，solr默认根据id（唯一约束）域来更新Document的内容，如果根据id值搜索不到id域则会执行添加操作，如果找到则更新。

  通过此菜单可以**创建索引、更新索引、删除索引**等操作，界面如下：

![document菜单](D:\Solr笔记\Slor学习过程截图\document菜单.jpg)

​	overwrite=”true” ：

 	solr在做索引的时候，如果文档已经存在，就用xml中的文档进行替换。 

​	commitWithin=”1000” ：

 	solr 在做索引的时候，每个1000（1秒）毫秒，做一次文档提交。为了方便测试也可以在Document中立即提交，< /doc>后添加< commit />。

- Query（重点）

  通过/select执行搜索索引，必须指定“q”查询条件方可搜索。

![query](D:\Solr笔记\Slor学习过程截图\query.jpg)

###### **3.1 配置多solrcore**

配置多solrcore的好处：

- 在进行solrcloud的时候，必须配置多solcore
- 每个solrcore之间是独立的，都可以单独对外提供服务。不同的业务模块可以使用不同的solrcore来提供搜索和索引服务。

添加solrcore：

- 拷贝solrhome下的collection1目录，修改为collection2

![](D:\Solr笔记\Slor学习过程截图\添加另一个solrcore.jpg)

- 修改solcore目录下的core.properties中的name名称

![](D:\Solr笔记\Slor学习过程截图\修改solrcore名称.jpg)

经过这两步，多solrcore就配置成功了

###### **3.2.Schema.xml配置文件**

在schema.xml文件中，主要配置了solrcore中的一些数据信息，包含Field和FieldType的定义等信息，在solr中，Field和FieldType都需要先定义后使用

![](D:\Solr笔记\Slor学习过程截图\schema.jpg)

- Field

  定义Field域 eg：

  <field name="name" type="text_general" indexed="true" stored="true"/>

  **Name**：指定域的名称 **Type**：指定域的类型 **Indexed**：是否索引 **Stored**：是否存储 **Required**：是否必须 **multiValued**：是否多值，比如商品信息中有多张图片，一个Field想要存储多个值的话，就必须把multiValued设置为true   

- dynamicField

  动态域 eg：

  <dynamicField name="*_i"  type="int"    indexed="true"  stored="true"/>

  **Name**：指定动态域的命名规则

- uniqueKey

  指定唯一键 eg：

  <uniqueKey>id</uniqueKey>

  其中的id是在Field标签中已经定义好的域名，而且该域要设置为require为true

  一个schema.xml文件中必须有且仅有一个唯一键

- copyField

  复制域 eg：

  <copyField source="cat" dest="text"/>

  source：要复制的源域的域名 dest：目标域域名

  由dest指定的目标域，必须设置multiValued为true

- fieldType

  域的类型 eg：

  ```xml
  <fieldType name="text_general" class="solr.TextField" positionIncrementGap="100">
    <analyzer type="index">
      <tokenizer class="solr.StandardTokenizerFactory"/>
      <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
      <!-- in this example, we will only use synonyms at query time
      <filter class="solr.SynonymFilterFactory" synonyms="index_synonyms.txt" ignoreCase="true" expand="false"/>
      -->
      <filter class="solr.LowerCaseFilterFactory"/>
    </analyzer>
    <analyzer type="query">
      <tokenizer class="solr.StandardTokenizerFactory"/>
      <filter class="solr.StopFilterFactory" ignoreCase="true" words="stopwords.txt" />
      <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>
      <filter class="solr.LowerCaseFilterFactory"/>
    </analyzer>
  </fieldType>
  ```
  **Name**：指定域的类型；**Class**：指定该域类型对应的solr的类型；**Analyzer**：指定分析器；**Type**：index、query，分别指定搜索和索引时的分析器；**Tokenizer**:指定分词器；**Filter**：指定过滤器

###### 3.3 中文分词器

使用ikanalyzer进行中文分词

- 将ikanalyzer的jar包拷贝到tomcat下solr中的lib目录中

![](D:\Solr笔记\Slor学习过程截图\ikanalyzer拷贝的目录.jpg)

- 将ikanalyzer的扩展词库的配置文件拷贝到tomcat下的classes目录中

![](D:\Solr笔记\Slor学习过程截图\ikanalyzer的配置文件拷贝.jpg)

- 配置中文分词的FieldType

   <fieldType name="text_ik" class="solr.TextField">
        <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer">
        </analyzer>
     </fieldType>

- 配置使用中文分词的Field

  <field name="content_ik" type="text_ik" indexed="true" stored="true"/>

- 重启tomcat即可测试中文分词器是否有用

![](D:\Solr笔记\Slor学习过程截图\在solr中验证分词器.jpg)

###### 3.4配置业务Field

- 需求

  由于要对任意案例中的products表的数据进行索引，所以需要先定义对应的Field域

- 分析配置

  ![](D:\Solr笔记\Slor学习过程截图\对product进行需求分析配置.jpg)

  需要往索引库添加的字段有： pid、name、catalog、catalog_name、price、description、picture

  **FieldType：** 经分析，由于中文分词器已经配置完FieldType，所以目前FieldType已经满足需要，无需配置。

  **Field：** **Pid：** 由于pid在products表中是唯一键，而且在solr的shema.xml中已有一个id的唯一键配置，所以不需要再重新定义pid域。

  **Name：**

  <field name="product_name" type="text_ik" indexed="true" stored="true"/>

  **Catalog、catalog_name：**

  <!-- 商品分类ID --> <field name="product_catalog" type="string" indexed="true" stored="true"/> <!-- 商品分类名称 --> <field name="product_catalog_name" type="string" indexed="true" stored="false"/> Price： <!-- 商品价格 --> <field name="product_price" type="float" indexed="true" stored="true"/> Description： <!-- 商品描述 --> <field name="product_description" type="text_ik" indexed="true" stored="false"/> Picture： <!-- 商品图片地址 --> <field name="product_picture" type="string" indexed="false" stored="true"/>

  ![](D:\Solr笔记\Slor学习过程截图\在schema中进行field配置.jpg)

###### **3.5 Dataimport**

该插件可以将数据库中指定的sql语句的结果导入到solr索引库中。

- 第一步：添加jar包 

  Dataimport的jar包 复制以下目录的jar包

![](D:\Solr笔记\Slor学习过程截图\添加Dataimport的jar包.jpg)

添加到以下目录

![](D:\Solr笔记\Slor学习过程截图\dataimport添加目录.jpg)

修改solrconfig.xml文件，添加lib标签

<lib dir="${solr.install.dir:../..}/contrib/dataimporthandler/lib" regex=".*\.jar" />

**MySQL数据库驱动包** 将mysql的驱动包，复制到以下目录

![](D:\Solr笔记\Slor学习过程截图\mysql的驱动包导入.jpg)

修改solrconfig.xml文件，添加lib标签

<lib dir="${solr.install.dir:../..}/contrib/db/lib" regex=".*\.jar" />

- 第二步：配置requestHandler

在solrconfig.xml中，添加一个dataimport的requestHandler

![](D:\Solr笔记\Slor学习过程截图\在solrconfig中配置dataimport的requestHandler.jpg)

- 第三步：创建data-config.xml

在solrconfig.xml同级目录下，创建data-config.xml

![](D:\Solr笔记\Slor学习过程截图\创建data-config.xml.jpg)

-  重启tomcat

![](D:\Solr笔记\Slor学习过程截图\重启tomcat查看配置是否成功.jpg)



##### **5 Solrj的使用**

###### **5.1 什么是solrj**

Solrj就是solr服务器的java客户端。

![](D:\Solr笔记\Slor学习过程截图\solrj图解.jpg)

###### **5.2 环境准备**

- Jdk

- Ide

- Tomcat

- Solrj



###### **5.3 搭建工程**

**Solrj的依赖包和核心包**

![](D:\Solr笔记\Slor学习过程截图\solrj的依赖包和核心包.jpg)

**Solr的扩展服务包**

![](D:\Solr笔记\Slor学习过程截图\solr的扩展服务包.jpg)

 ![](D:\Solr笔记\Slor学习过程截图\jar包导入成功完成图.jpg)

###### **5.4 使用solrj完成索引维护**

-  添加/修改索引

在solr中，索引库中都会存在一个唯一键，如果一个Document的id存在，则执行修改操作，如果不存在，则执行添加操作。

![](D:\Solr笔记\Slor学习过程截图\添加索引.jpg)

- 删除索引
  - 根据指定ID来删除

![](D:\Solr笔记\Slor学习过程截图\指定id进行杀出.jpg)

- 
  -  根据条件删除

![](D:\Solr笔记\Slor学习过程截图\根据条件删除.jpg)

###### **5.4.3 查询索引**

- 简单查询

![](D:\Solr笔记\Slor学习过程截图\简单查询.jpg)

- 复杂查询

  - solr的查询语法

  (1). **q** - 查询关键字，必须的，如果查询所有使用*:*。 请求的q是字符串 

  ![](D:\Solr笔记\Slor学习过程截图\q的使用.jpg)

  (2). **fq** - （filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，例如：： 请求fq是一个数组（多个值） 

  ![](D:\Solr笔记\Slor学习过程截图\fq查询.jpg)

  

  过滤查询价格从1到20的记录。 也可以在“q”查询条件中使用product_price:[1 TO 20]，如下：

  ![](D:\Solr笔记\Slor学习过程截图\q实现价格查询.jpg)

  也可以使用“*”表示无限，例如： 20以上：product_price:[20 TO *] 20以下：product_price:[* TO 20]

  (3). **sort** - 排序，格式：`sort=<field name>+<desc|asc>[,<field name>+<desc|asc>]… 。` 示例：

  ![](D:\Solr笔记\Slor学习过程截图\sort.jpg)

   按价格降序 (4). **start** - [分页](http://www.knowsky.com/tag-3.html)显示使用，开始记录下标，从0开始 (5). **rows** - 指定返回结果最多有多少条记录，配合start来实现分页。 实际开发时，知道当前页码和每页显示的个数最后求出开始下标。

  (6). **fl** - 指定返回那些字段内容，用逗号或空格分隔多个。

  ![](D:\Solr笔记\Slor学习过程截图\fl.jpg)

   显示商品图片、商品名称、商品价格

  (7). **df** - 指定一个搜索Field

  ![](D:\Solr笔记\Slor学习过程截图\dl.jpg)

   也可以在SolrCore目录 中conf/solrconfig.xml文件中指定默认搜索Field，指定后就可以直接在“q”查询条件中输入关键字。 

  ![](D:\Solr笔记\Slor学习过程截图\solcore中solrconfig中的指定搜索field.jpg)

  (8). **wt** - (writer type)指定输出格式，可以有 xml, json, [php](http://www.knowsky.com/php.asp), phps, 后面 solr 1.3增加的，要用通知我们，因为默认没有打开。

  (9). **hl** - 是否高亮 ,设置高亮Field，设置格式前缀和后缀。 

  ![](D:\Solr笔记\Slor学习过程截图\hl.jpg)

  - 代码

  @Test public void search02() throws Exception { // 创建HttpSolrServer HttpSolrServer server = new HttpSolrServer("http://localhost:8080/solr"); // 创建SolrQuery对象 SolrQuery query = new SolrQuery(); // 输入查询条件 query.setQuery("product_name:小黄人"); // query.set("q", "product_name:小黄人"); // 设置过滤条件 // 如果设置多个过滤条件的话，需要使用query.addFilterQuery(fq) query.setFilterQueries("product_price:[1 TO 10]"); // 设置排序 query.setSort("product_price", ORDER.asc); // 设置分页信息（使用默认的） query.setStart(0); query.setRows(10); // 设置显示的Field的域集合 query.setFields("id,product_name,product_catalog,product_price,product_picture"); // 设置默认域 query.set("df", "product_keywords"); // 设置高亮信息 query.setHighlight(true); query.addHighlightField("product_name"); query.setHighlightSimplePre("<em>"); query.setHighlightSimplePost("</em>"); // 执行查询并返回结果 QueryResponse response = server.query(query); // 获取匹配的所有结果 SolrDocumentList list = response.getResults(); // 匹配结果总数 long count = list.getNumFound(); System.out.println("匹配结果总数:" + count); // 获取高亮显示信息 Map<String, Map<String, List<String>>> highlighting = response.getHighlighting(); for (SolrDocument doc : list) { System.out.println(doc.get("id")); List<String> list2 = highlighting.get(doc.get("id")).get( "product_name"); if (list2 != null) System.out.println("高亮显示的商品名称：" + list2.get(0)); else { System.out.println(doc.get("product_name")); } System.out.println(doc.get("product_catalog")); System.out.println(doc.get("product_price")); System.out.println(doc.get("product_picture")); System.out.println("====================="); } }

##### **6 京东案例**

###### **6.1 需求**

使用Solr实现电商网站中商品信息搜索功能，可以根据关键字、分类、价格搜索商品信息，也可以根据价格进行排序，同时还可以分页。 界面如下： 

![](D:\Solr笔记\Slor学习过程截图\实现界面.jpg)

###### **6.2 分析**

- UI分析

![](D:\Solr笔记\Slor学习过程截图\UI分析.jpg)

- 架构分析

**应用服务器服务端：** **表现层：**使用springmvc接收前台搜索页面的查询条件等信息 **业务层：**调用dao层完成数据库持久化 如果数据库数据发生变化，调用solrj的客户端同步索引库。 **Dao层：**使用mybatis完成数据库持久化

**Solrj服务器：** 提供搜索和索引服务

**数据库服务器：** 提供数据库服务

![](D:\Solr笔记\Slor学习过程截图\架构分析.jpg)

###### **6.3 工程搭建**

- Solrj的jar包

- Solr的扩展包

- Springmvc的包

###### **6.4 代码实现**

- Pojo

![](D:\Solr笔记\Slor学习过程截图\工程搭建完成图.jpg)

- Service

**Service接口**

![](D:\Solr笔记\Slor学习过程截图\service接口.jpg)

**Service实现类**

@Service public class ProductServiceImpl implements ProductService { // 依赖

注入

HttpSolrServer @Autowired private HttpSolrServer server; @Override public ResultModel getProducts(String queryString, String catalogName, String price, String sort, Integer page) throws Exception { // 创建SolrQuery对象 SolrQuery query = new SolrQuery(); // 输入关键字 if (StringUtils.isNotEmpty(queryString)) { query.setQuery(queryString); } else { query.setQuery("*:*"); } // 输入商品分类过滤条件 if (StringUtils.isNotEmpty(catalogName)) { query.addFilterQuery("product_catalog_name:" + catalogName); } // 输入价格区间过滤条件 // price的值：0-9 10-19 if (StringUtils.isNotEmpty(price)) { String[] ss = price.split("-"); if (ss.length == 2) { query.addFilterQuery("product_price:[" + ss[0] + " TO " + ss[1] + "]"); } } // 设置排序 if ("1".equals(sort)) { query.setSort("product_price", ORDER.desc); } else { query.setSort("product_price", ORDER.asc); } // 设置分页信息 if (page == null) page = 1; query.setStart((page - 1) * 20); query.setRows(20); // 设置默认域 query.set("df", "product_keywords"); // 设置高亮信息 query.setHighlight(true); query.addHighlightField("product_name"); query.setHighlightSimplePre("<font style=\"color:red\" >"); query.setHighlightSimplePost("</font>"); QueryResponse response = server.query(query); // 查询出的结果 SolrDocumentList results = response.getResults(); // 记录总数 long count = results.getNumFound(); List<Products> products = new ArrayList<>(); Products prod; // 获取高亮信息 Map<String, Map<String, List<String>>> highlighting = response .getHighlighting(); for (SolrDocument doc : results) { prod = new Products(); // 商品ID prod.setPid(doc.get("id").toString()); List<String> list = highlighting.get(doc.get("id")).get( "product_name"); // 商品名称 if (list != null) prod.setName(list.get(0)); else { prod.setName(doc.get("product_name").toString()); } // 商品价格 prod.setPrice(Float.parseFloat(doc.get("product_price").toString())); // 商品图片地址 prod.setPicture(doc.get("product_picture").toString()); products.add(prod); } // 封装ResultModel对象 ResultModel rm = new ResultModel(); rm.setProductList(products); rm.setCurPage(page); rm.setRecordCount(count); int pageCount = (int) (count / 20); if (count % 20 > 0) pageCount++; // 设置总页数 rm.setPageCount(pageCount); return rm; } }

- Controller

  - 代码

  ![](D:\Solr笔记\Slor学习过程截图\controller.jpg)

  - jsp和静态资源

从资料中拷贝

![](D:\Solr笔记\Slor学习过程截图\jsp文件.jpg)

![](D:\Solr笔记\Slor学习过程截图\静态资源文件.jpg)

图片信息放到以下目录

![](D:\Solr笔记\Slor学习过程截图\图片存放目录.jpg)

![](D:\Solr笔记\Slor学习过程截图\在tomcat中设置存放目录.jpg)

-  Web.xml

![](D:\Solr笔记\Slor学习过程截图\web.xml设置.jpg)

- 配置springmvc.xml

![](D:\Solr笔记\Slor学习过程截图\springmvc.xml.jpg)









