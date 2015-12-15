<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="x-ua-compatible" content="ie=7">
<meta name="Author" content="铭仔网络">
<meta name="Author" content="铭仔网络设计制作">
<title>爱尔丽(唐山)医学美容 官网</title>
<meta name="keywords" content="唐山爱尔丽  唐山市爱尔丽 爱尔丽 整形 医疗美容 医美 微整 眼袋 双眼皮 隆胸 瘦身 瘦脸 V脸 ">
<meta name="Description" content="爱尔丽(唐山)医学美容官方网站">

<link href="${ctx}/static/css/css_whir.css" rel="stylesheet" type="text/css">
<script src="${ctx}/static/js/hm.js"></script>
<script src="${ctx}/static/js/jquery-1.4.2.min.js" type="text/javascript" charset="utf-8"></script>
<!--[if IE 6]>
<script src="${ctx}/static/cn/Scripts/DD_belatedPNG.js" type="text/javascript"></script>
<![endif]-->
    <script type="text/javascript" src="${ctx}/static/js/jquery.SuperSlide.js"></script>
</head>
<body>
 
<div class="Header">
    <div class="topbox">
        <a href="${ctx}/" class="logo"><img src="${ctx}/static/images/201505091742494249.jpg"></a>
        <!--a href='' class="top-a"></a-->
        <div class="mainnav">
            <ul>
            <li><span><a href="${ctx}/" id="mainnav1" class="aon">首页</a></span></li>
            <li><span><a href="http://www.airlee.com.cn/zxxx/list_15.aspx" id="mainnav2">美丽资讯</a></span>
            <div class="subnav" id="subnav2">
              <dl>
                <dd><a href="http://www.airlee.com.cn/zxxx/list_15.aspx">最新消息</a></dd>
                <dd><a href="http://www.airlee.com.cn/djrt/list_18.aspx">当季热推</a></dd>
                <dd><a href="http://www.airlee.com.cn/spgx/list_16.aspx">视频共享</a></dd>
                <dd><a href="http://www.airlee.com.cn/mxcp/list_19.aspx">明星产品</a></dd>
                <dd><a href="http://www.airlee.com.cn/xgxzs/index_68.aspx">相关小知识</a></dd>
                <dd><a href="http://www.airlee.com.cn/mttj/list_20.aspx">媒体推介</a></dd>
              </dl>
              <i><img src="${ctx}/static/images/201404301117421742.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></i>
            </div>
            </li>
            <li><span><a href="http://www.airlee.com.cn/gyael/index_13.aspx" id="mainnav6">品牌故事</a></span>
            <div class="subnav subnav-small" id="subnav6">
                <dl>
                
                  
                      <dt><a href="http://www.airlee.com.cn/gyael/index_13.aspx">关于爱尔丽</a></dt>
                    
                
              </dl>
              <i><img src="${ctx}/static/images/th_201502061445334533.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></i>
            </div>
            </li>
            <li><span><a href="http://www.airlee.com.cn/fwxm/list_21.aspx" id="mainnav3">服务项目</a></span>
            <div class="subnav" id="subnav3">
                <dl>
                
                <dt><a href="http://www.airlee.com.cn/fwxm/list_21.aspx?lcid=28" title="精致面部微雕">精致面部微雕</a></dt>
                
                <dt><a href="http://www.airlee.com.cn/fwxm/list_21.aspx?lcid=27" title="动感美体塑形">动感美体塑形</a></dt>
                
              </dl>
              <i><img src="${ctx}/static/images/th_20140430111801181.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></i>
            </div>
            </li>
            <li><span><a href="http://www.airlee.com.cn/mlqa/list_22.aspx" id="mainnav7">美丽Q&amp;A</a></span></li>
            <li><span><a href="http://www.airlee.com.cn/tdjs/index_24.aspx" id="mainnav5">明星团队</a></span>
            <div class="subnav subnav-small" id="subnav5">
                <dl>
                
                  
                      <dt><a href="http://www.airlee.com.cn/tdjs/index_24.aspx">团队介绍</a></dt>
                    
                
                  
                      <dt><a href="http://www.airlee.com.cn/yszl/list_25.aspx">医师专栏</a></dt>
                    
                
              </dl>
              <i><img src="${ctx}/static/images/th_20140430111807187.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></i>
            </div>
            </li>
            <li><span><a href="http://www.airlee.com.cn/cgal/list_37.aspx" id="mainnav4">美丽见证</a></span>
            <div class="subnav subnav-small" id="subnav4">
                <dl>
                
                  
                      <dt><a href="http://www.airlee.com.cn/cgal/list_37.aspx">成功案例</a></dt>
                    
                
                  
                      <dt><a href="http://www.airlee.com.cn/mxjz/list_36.aspx">明星见证</a></dt>
                    
                
              </dl>
              <i><img src="${ctx}/static/images/20140430160316316.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></i>
            </div>
            </li>
            </ul>
        </div>   
    </div>
</div>
<script type="text/javascript">
jQuery(".mainnav ul li").hover(function(){
	jQuery(".mainnav ul li .subnav").hide();
	jQuery(".mainnav ul li").find("a:first").removeClass("acur");
	jQuery(this).find("a:first").addClass("acur");
	jQuery(this).find(".subnav").show();
	},function(){
	jQuery(".mainnav ul li .subnav").hide();
	jQuery(".mainnav ul li").find("a:first").removeClass("acur");
	});
</script>

<!--banner图片 start-->
<div class="BannerBox">
    <div class="BannerList">
        <ul style="position: relative; width: 1366px; height: 555px;">
        
        <li style="position: absolute; width: 1366px; left: 0px; top: 0px; background: url(http://www.airlee.com.cn/uploadfiles/2015/04/20150417192004204.jpg) 50% 0% no-repeat;"></li>
        
        <li style="position: absolute; width: 1366px; left: 0px; top: 0px; display: none; background: url(http://www.airlee.com.cn/uploadfiles/2014/05/201405301121222122.jpg) 50% 0% no-repeat;"></li>
        
        </ul>
    </div>
    <div class="hd">
        <ul>
        
        <li class="on"><img src="${ctx}/static/images/201504171916521652.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><i></i></li>
        
        <li><img src="${ctx}/static/images/th_201405061722292229.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><i></i></li>
        
        </ul>
    </div>
    <div class="banbg"></div>
</div>
<script type="text/javascript">
    jQuery(".BannerBox").slide({ titCell: ".hd ul li", mainCell: ".BannerList ul", effect: "fold", autoPlay: true, mouseOverStop: false, interTime: "5000" });	
</script>
<div class="contain">
    <div class="Hpbox">
        <div class="HpList">
            <img class="img" src="${ctx}/static/images/mxjz_03.png">
        <div class="tempWrap" style="overflow:hidden; position:relative; width:980px">
        <ul style="width: 3528px; position: relative; overflow: hidden; padding: 0px; margin: 0px; left: -1176px;">
        <li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=236" class="pic"><img sr${ctx}/static/images/es/201510061333203320.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>4D脱脂针</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=209" class="pic"><img src="${ctx}/static/images/201504171614161416.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>全球独创—活氧排毒血管...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=208" class="pic"><img src="${ctx}/static/images/201504171623372337.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>启动返老还童的钥匙—自...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=207" class="pic"><img src="${ctx}/static/images/201504171623562356.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>童颜制造器——水美美</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=206" class="pic"><img src="${ctx}/static/images/20150417162805285.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>提拉塑形 完善逆龄—3D...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=203" class="pic"><img src="${ctx}/static/images/201504171631313131.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>法拉丽，至臻拉皮，速效...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=236" class="pic"><img src="${ctx}/static/images/201510061333203320.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>4D脱脂针</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=209" class="pic"><img src="${ctx}/static/images/201504171614161416.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>全球独创—活氧排毒血管...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=208" class="pic"><img src="${ctx}/static/images/201504171623372337.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>启动返老还童的钥匙—自...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=207" class="pic"><img src="${ctx}/static/images/201504171623562356.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>童颜制造器——水美美</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=206" class="pic"><img src="${ctx}/static/images/20150417162805285.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>提拉塑形 完善逆龄—3D...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=203" class="pic"><img src="${ctx}/static/images/201504171631313131.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>法拉丽，至臻拉皮，速效...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=236" class="pic"><img src="${ctx}/static/images/201510061333203320.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>4D脱脂针</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=209" class="pic"><img src="${ctx}/static/images/201504171614161416.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>全球独创—活氧排毒血管...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=208" class="pic"><img src="${ctx}/static/images/201504171623372337.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>启动返老还童的钥匙—自...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=207" class="pic"><img src="${ctx}/static/images/201504171623562356.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>童颜制造器——水美美</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=206" class="pic"><img src="${ctx}/static/images/20150417162805285.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>提拉塑形 完善逆龄—3D...</span></a></li><li style="float: left; width: 170px;"><p></p><a href="http://www.airlee.com.cn/mxcp/info_19.aspx?itemid=203" class="pic"><img src="${ctx}/static/images/201504171631313131.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>法拉丽，至臻拉皮，速效...</span></a></li></ul></div>
        </div>
        <a class="prev" href="javascript:void(0)"></a><a class="next" href="javascript:void(0)"></a>
    </div>
    <script type="text/javascript">
        jQuery(".Hpbox").slide({ mainCell: ".HpList ul", autoPage: true, effect: "leftLoop", autoPlay: true, scroll: 1, vis: 5, trigger: "click" });
    </script>
    <div class="Homebox">
        <div class="Home_left">
           <div class="Home-mjz">
                <div class="H-mjz-list">
                    <ul style="position: relative; width: 616px; height: 220px;">
                    
                        <li style="position: absolute; width: 270px; left: 0px; top: 0px; display: list-item;">
                        <div class="pic"><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=232"><img src="${ctx}/static/images/201506171441444144.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></div>
                        <h1><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=232">摆脱初老症状  大妈脸...</a></h1>
                        <div class="doc-pic">
                            <img src="${ctx}/static/images/201506171447224722.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;">
                            <p><span>改造医师：</span>刘恭治医生：主治非侵入式的雷射及拉皮项目</p>
                        </div>
                        <h2><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=232" class="a">查详案例详细</a><a href="http://www.airlee.com.cn/cgal/list_37.aspx">其它案例</a></h2>
                        </li>
                    
                        <li style="position: absolute; width: 270px; left: 0px; top: 0px; display: none;">
                        <div class="pic"><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=231"><img src="${ctx}/static/images/20150610161408148.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></div>
                        <h1><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=231">反转颜龄的关键——无痕...</a></h1>
                        <div class="doc-pic">
                            <img src="${ctx}/static/images/20150610163004304.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;">
                            <p><span>改造医师：</span>郭炳成医师
爱尔丽医美中华分院院长

</p>
                        </div>
                        <h2><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=231" class="a">查详案例详细</a><a href="http://www.airlee.com.cn/cgal/list_37.aspx">其它案例</a></h2>
                        </li>
                    
                        <li style="position: absolute; width: 270px; left: 0px; top: 0px; display: none;">
                        <div class="pic"><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=230"><img src="${ctx}/static/images/20150604112207227.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></div>
                        <h1><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=230">产后肥肚不离身  不要...</a></h1>
                        <div class="doc-pic">
                            <img src="${ctx}/static/images/20150604120644644.png" onerror="this.src=&#39;/res/images/nofile.jpg&#39;">
                            <p><span>改造医师：</span>伊能静指定御用医师—刘兆腾医师
爱尔丽中和分院院长
擅长疗程：法拉丽，玻尿酸注射，水微晶
</p>
                        </div>
                        <h2><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=230" class="a">查详案例详细</a><a href="http://www.airlee.com.cn/cgal/list_37.aspx">其它案例</a></h2>
                        </li>
                    
                        <li style="position: absolute; width: 270px; left: 0px; top: 0px; display: none;">
                        <div class="pic"><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=87"><img src="${ctx}/static/images/th_201502061530223022.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></div>
                        <h1><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=87">再也不是路人甲  从此...</a></h1>
                        <div class="doc-pic">
                            <img src="${ctx}/static/images/th_201502061620142014.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;">
                            <p><span>改造医师：</span>爱尔丽忠孝分院专任医师曾文杰
爱尔丽中华分院院长郭炳成</p>
                        </div>
                        <h2><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=87" class="a">查详案例详细</a><a href="http://www.airlee.com.cn/cgal/list_37.aspx">其它案例</a></h2>
                        </li>
                    
                        <li style="position: absolute; width: 270px; left: 0px; top: 0px; display: none;">
                        <div class="pic"><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=86"><img src="${ctx}/static/images/th_201502061541454145.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></div>
                        <h1><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=86">超级欧巴桑变身气质女...</a></h1>
                        <div class="doc-pic">
                            <img src="${ctx}/static/images/th_20140506100548548.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;">
                            <p><span>改造医师：</span>改造医师：中华分院院长郭炳成、忠孝分院专任医师曾文杰</p>
                        </div>
                        <h2><a href="http://www.airlee.com.cn/cgal/info_37.aspx?itemid=86" class="a">查详案例详细</a><a href="http://www.airlee.com.cn/cgal/list_37.aspx">其它案例</a></h2>
                        </li>
                    
                    </ul>
                </div>
                <div class="hd">
                    <ul>
                    
                    <li class="on">1</li>
                    
                    <li class="">2</li>
                    
                    <li class="">3</li>
                    
                    <li class="">4</li>
                    
                    <li class="">5</li>
                    
                    </ul>
                </div>
                <script type="text/javascript">
                    jQuery(".Home-mjz").slide({ titCell: ".hd ul li", mainCell: ".H-mjz-list ul", effect: "fold", autoPlay: true, mouseOverStop: false,interTime:"3000" });	
                </script>
           </div>
           <div class="Home-star">
              <div class="H_title1"><a href="http://www.airlee.com.cn/mxcp/list_19.aspx"></a></div>
               <ul>
                
                <li><a href="http://www.airlee.com.cn/mxjz/list_36.aspx" class="pic"><img src="${ctx}/static/images/20140505180532532.png" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>小邓丽君：蔡幸娟...</span></a></li>
                
                <li><a href="http://www.airlee.com.cn/mxjz/list_36.aspx" class="pic"><img src="${ctx}/static/images/20140505180455455.png" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>美胸皇后：郭静纯...</span></a></li>
                
                <li><a href="http://www.airlee.com.cn/mxjz/list_36.aspx" class="pic"><img src="${ctx}/static/images/2014050518050858.png" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>国际巨星：崔丽心...</span></a></li>
                
                <li><a href="http://www.airlee.com.cn/mxjz/list_36.aspx" class="pic"><img src="${ctx}/static/images/20140505180442442.png" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>幸福人妻女星：季...</span></a></li>
                
               </ul>
               <div class="bigbox">
                
                    <a href="http://www.airlee.com.cn/mxjz/list_36.aspx" class="pic"><img src="${ctx}/static/images/20140505180532532.png" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>小邓丽君：蔡幸娟</span></a>
                
               </div>
           </div>
           <div class="Home-team">
              <div class="H_title2"><a href="http://www.airlee.com.cn/yszl/list_25.aspx"></a></div>
              <div class="bigimg"><img src="${ctx}/static/images/201510151659145914.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></div>
              
              <div class="toptext" style="">
                  <h1><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=241">蔡文平医师——自体脂肪...</a></h1>
                  <p>爱尔丽整形外科诊所高雄博爱分院院长</p>
                  <h2><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=241">了解他</a></h2>
              </div>
              
              <div class="toptext" style="display:none;">
                  <h1><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=240">黄仁吴医师</a></h1>
                  <p>台湾爱尔丽医院权威整形外科医师</p>
                  <h2><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=240">了解他</a></h2>
              </div>
              
              <div class="toptext" style="display:none;">
                  <h1><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=205">亞洲首席面雕抗衰大師...</a></h1>
                  <p>广州爱尔丽医美整形医院专任医师</p>
                  <h2><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=205">了解他</a></h2>
              </div>
              
              <div class="toptext" style="display:none;">
                  <h1><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=234">世界隆胸脂雕之神：罗国...</a></h1>
                  <p>新庄爱尔丽分院院长
南京爱尔丽分院专任医师
</p>
                  <h2><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=234">了解他</a></h2>
              </div>
              
              <ul>
              
              <li class="aon"><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=241"><img src="${ctx}/static/images/201510151659145914.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></li>
              
              <li><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=240"><img src="${ctx}/static/images/201510151716481648.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></li>
              
              <li><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=205"><img src="${ctx}/static/images/201504161758325832.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></li>
              
              <li><a href="http://www.airlee.com.cn/yszl/info_25.aspx?itemid=234"><img src="${ctx}/static/images/th_201510061245404540.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></a></li>
              
              </ul>
           </div>
        </div>
        <div class="Home_right">
            <div class="Home_news">
                <h1><img src="${ctx}/static/images/h_title4.jpg"></h1>
                <ul>
                
                <li class="litop"><a href="http://www.airlee.com.cn/zxxx/info_15.aspx?itemid=243" class="pic"><img src="${ctx}/static/images/th_201511041447284728.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"><span>万能荷尔蒙--为二孩的到来铺就“阳...</span></a></li>
                
                <li><a href="http://www.airlee.com.cn/zxxx/info_15.aspx?itemid=242">爱如潮水-性福『湿』乐园</a></li>
                
                <li><a href="http://www.airlee.com.cn/zxxx/info_15.aspx?itemid=238">爱尔丽携手金钟奖入围准影后潘慧如献爱心</a></li>
                
                <li><a href="http://www.airlee.com.cn/zxxx/info_15.aspx?itemid=237">台湾美女市议员与爱尔丽不得不说的美丽之道...</a></li>
                
                </ul>
            </div>
            <div class="Home_about">
                <h1><img src="${ctx}/static/images/h_title5.jpg"></h1>
                
                <p>	台湾爱尔丽成立于2002年，爱尔丽的美丽事业横跨了两岸三地、新加坡，全亚洲会员超过百万人，台湾岛内医学美容市场占有率超过5成以上，获得全台百大整形医院第一名，全亚洲30余家直营分院连锁，是目前全台湾规模最大、口碑最佳的医美集团。</p>
                
                <h2><a href="http://www.airlee.com.cn/gyael/index_13.aspx"><img src="${ctx}/static/images/btn1.jpg"></a></h2>
            </div>
            <div class="Home_contact">
                <h1><img src="${ctx}/static/images/h_title6.jpg"></h1>
                <dl>
                    <dd>预约电话：</dd>
                    <dd><img src="${ctx}/static/images/201502111646274627.jpg"></dd>
                    <dd>公司邮箱：</dd>
                    <dd>beauty@airlee.com.cn</dd>
                    <dt>
                    
                    <span><img src="${ctx}/static/images/20150529121404144.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></span>
                    <span><img src="${ctx}/static/images/201505091745564556.jpg" onerror="this.src=&#39;/res/images/nofile.jpg&#39;"></span>
                    
                    </dt>
                </dl>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!--banner图片 end-->
  
 
<div class="footer">
    <div class="bt_nav"><script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?c17c4db4a13f85882e451f18f7ce054e";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script><a href="http://www.airlee.com.cn/xzael/index_29.aspx">寻找爱尔丽</a><a href="http://www.airlee.com.cn/lxwmMH/index_30.aspx">联系我们</a><a target="_blank" href="http://weibo.com/p/1006065602798797/home?from=page_100606&mod=TAB#place">新浪微博秀</a>
    </div>
    <p style="text-align:center;" class="MsoNormal">	<span style="white-space:normal;"><span style="white-space:normal;"></span></span></p><p style="text-align:center;" class="MsoNormal">	广州分院：广州市天河区天河路371号（隆德大厦首层）<span style="line-height:1.5;">&nbsp;美丽热线：400-020-5688</span><span style="line-height:1.5;">&nbsp;</span></p><p style="text-align:center;" class="MsoNormal">	<span style="line-height:1.5;">福州分院：福州市鼓楼区省府路66号 &nbsp;</span><span style="line-height:1.5;">武汉分院：武汉市硚口区南泥湾大道118号江城壹号7号楼 &nbsp;</span><span style="line-height:1.5;">成都分院：成都市武侯区佳灵路20号九峰国际C座1025室</span></p><p style="text-align:center;" class="MsoNormal">	<span style="line-height:1.5;">济南分院：济南市槐荫区经七路振兴花园1-4B &nbsp;</span><span style="line-height:1.5;">合肥分院：合肥市包河区马鞍山路200号和地广场 &nbsp;</span><span style="line-height:1.5;">集团总部：中国台湾台北市忠孝东路三段305号6楼</span></p> 

    <p>Copyright © 2015 爱尔丽(国际)医学美容. All Rights Reserved.<a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备14045312号-1</a> Designed by <a href="http://www.wanhu.com.cn/" target="_blank">Wanhu </a></p>
</div>
<script type="text/javascript">
    $(".Pages a").each(function () {
        if ($(this).attr("href") == "javascript:void(0);") {
            $(this).hide();
        }
    })
</script>

 <script type="text/javascript">
	jQuery(".Home-team .toptext:first").show();
	jQuery(".Home-team ul li:first").addClass("aon");
    jQuery("#mainnav1").attr("class", "aon");
	jQuery(".Home-team ul li").hover( function(){
		jQuery(".Home-team ul li").removeClass("aon");
		jQuery(this).addClass("aon");
		jQuery(".bigimg img").attr("src",jQuery(this).find("img").attr("src"));
		jQuery(".Home-team .toptext").hide();
		jQuery(".Home-team .toptext").eq(jQuery(this).index()).show();
   });
	jQuery(".Home-star ul li").hover( function(){
		jQuery(".bigbox img").attr("src",jQuery(this).find("img").attr("src"));
		jQuery(".bigbox span").text(jQuery(this).find("span").text());
		jQuery(".bigbox a").attr("href",jQuery(this).find("a").attr("href"));
   });
</script>

</body></html>