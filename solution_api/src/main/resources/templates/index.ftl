<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>飞哥博客</title>
    <meta name="keywords" content="关键词,关键词,关键词,关键词,5个关键词"/>
    <meta name="description" content="网站简介，不超过80个字"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<header>
    <div class="logo"><a href="/">飞哥博客</a></div>
    <div class="top-nav">
        <h2 id="mnavh"><span class="navicon"></span></h2>
        <ul id="nav">
        </ul>
    </div>
</header>
<aside class="side">
    <div class="about"><i><a href="/"><img src="/static/images/avatar.jpg"></a></i>
        <p>
            飞哥，一个80后IT屌丝！一边工作一边积累经验，分享一些个人技术心得。
        </p>
    </div>
</aside>
<main>
    <div class="main-content">
        <div class="welcome"> 主页</div>
        <div class="newsbox">
<#list modelList as model>
           <section>
               <div class="news" style='min-height: 230px;'>
                   <h2 class="newstitle"><span><a href="/front/more">更多</a></span><b>${model.tagName}</b></h2>
                   <ul>
                       <#list model.articleList as article>
                       <li><a href="/front/index3/${article.id}"><span>${article.updateTime?string('yyyy-MM-dd')} </span>${article.articleName}</a></li>
                       </#list>
                   </ul>
               </div>
           </section>
</#list>
        </div>
        <div class="blank"></div>
        <!--<div class="copyright">-->
        <!--<p>Copyright 2018 Inc. AllRights Reserved. Design by <a href="/">杨青个人博客</a> 蜀ICP备11002373号-1</p>-->
        <!--</div>-->
    </div>
</main>
<!--<a href="#" class="cd-top cd-is-visible">Top</a>-->
</body>
</html>
<style>
    @charset "utf-8";
    /* css */
    * { margin: 0; padding: 0 }
    body { font: 15px "Microsoft YaHei", Arial, Helvetica, sans-serif; background: #ededed url(../images/bg.jpg) repeat-y left 0; color: #666; line-height: 1.5; }
    img { border: 0; display: block }
    ul, li { list-style: none; }
    a { text-decoration: none; color: #333; }
    a:hover { text-decoration: none; }
    .blank { height: 10px; overflow: hidden; width: 100%; margin: auto; clear: both }
    .fl { float: left }
    .fr { float: right }
    header { position: fixed; top: 0; left: 0; z-index: 10; margin: 0; width: 100%; height: 48px; overflow: hidden; background: #313842; line-height: 48px; }
    .logo { font-size: 22px; color: #FFF; margin-left: 20px; float: left }
    .logo a { color: #fff }
    .top-nav { float: left; margin-left: 60px }
    .top-nav ul { overflow: hidden }
    .top-nav ul li { display: block; float: left; padding-right: 40px }
    .top-nav ul li a { color: #FFF }
    .top-nav ul li a:hover { color: #ccc }
    .top-nav ul li.selected a, .top-nav ul li a#selected { color: #09b1b9; }
    .search { background: #FFF; overflow: hidden; width: 260px; float: right; margin: 10px 30px 0 0; border-radius: 20px; }
    .search .input_text { padding-left: 5px; float: left; outline: none; border: 0; height: 30px; line-height: 30px; }
    .search .input_submit { background: url(../images/search.png) no-repeat center right 10px; color: #FFF; float: right; width: 32px; border: none; cursor: pointer; outline: none; height: 30px; line-height: 30px; }
    aside { width: 180px; float: left; overflow: hidden; position: fixed; color: #fff; }
    .about { line-height: 26px; font-size: 14px; padding: 0 10px; margin-top: 50px; }
    .about i { display: block; margin: 20px auto; width: 100px; height: 100px; }
    .about i img { width: 100%; display: block; border-radius: 50%; margin: auto }
    .about p { }
    .weixin { width: 100%; text-align: center; }
    .weixin img { width: 80%; margin: 40px auto 10px }
    .main-content { background: #EDEDED; margin-top: 48px; margin-left: 180px; }
    .welcome { border-bottom: 1px solid #E1E1E1; background: url(../images/zhuye.png) no-repeat left 20px center #fff; line-height: 42px; padding-left: 40px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; }
    .picbox { border: 1px solid #E1E1E1; margin: 20px; background: #fff; overflow: hidden; }
    .pictitle { background: #F8F8F8; height: 40px; line-height: 40px; text-align: center; font-size: 16px; border-bottom: 1px solid #E1E1E1; }
    .pictitle a { color: #666; }
    .pictitle a:hover { color: #000 }
    /*gundong*/
    .Box_con { position: relative; margin: 20px 60px; }
    .Box_con .btnl { position: absolute; }
    .Box_con .btn { display: block; width: 65px; height: 65px; position: absolute; top: 40px; cursor: pointer; }
    .Box_con .btnl { background: url(../images/cp3.png) no-repeat center; left: -60px; }
    .Box_con .btnl:hover { background: url(../images/cp4.png) no-repeat center; }
    .Box_con .btnr { background: url(../images/cp1.png) no-repeat center; right: -60px; }
    .Box_con .btnr:hover { background: url(../images/cp2.png) no-repeat center; }
    .Box_con .conbox { position: relative; overflow: hidden; }
    .Box_con .conbox ul { position: relative; list-style: none; overflow: hidden; }
    .Box_con .conbox ul li { float: left; width: 210px; height: 180px; margin-left: 20px; overflow: hidden; }
    .Box_con .conbox ul li:first-child { margin-left: 0; }
    .Box_con .conbox ul li img { display: block; width: 100%; height: 150px; transition: all 0.5s; }
    .Box_con .conbox ul li p { height: 30px; line-height: 30px; text-overflow: ellipsis; white-space: nowrap; overflow: hidden; }
    .Box_con .conbox ul li:hover p { color: #09B1B9 }
    /*newsbox*/
    .newsbox { margin: 20px 10px }
    .newsbox section { width: 33.3%; float: left; margin-bottom: 20px }
    .newsbox .news { background: #fff; border: 1px solid #E1E1E1; margin: 0 10px }
    .newstitle { line-height: 40px; font-size: 15px; border-bottom: 1px solid #E1E1E1; background: #F8F8F8 url(../images/newslist.png) no-repeat left 14px center; padding: 0 20px 0 46px; font-weight: normal }
    .newstitle span { float: right; font-size: 14px }
    .newstitle span a { color: #666 }
    .newstitle b { display: block; border-left: 1px solid #E1E1E1; padding-left: 10px }
    .newsbox .news ul { padding: 10px }
    .newsbox .news ul li { display: inline-block; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; height: 28px; width: 96%; line-height: 28px; font-size: 14px; background: url(../images/li.png) no-repeat left center; padding-left: 15px; }
    .newsbox .news ul li span { float: right; color: #999; font-size: 12px; margin-left: 20px }
    .newsbox .news ul li a:hover { color: #09B1B9; }
    /*link*/
    .links { border: 1px solid #E1E1E1; margin: 0 20px 20px 20px; background: #fff; overflow: hidden; }
    .linktitle { background: #F8F8F8; height: 40px; line-height: 40px; text-align: center; font-size: 16px; border-bottom: 1px solid #E1E1E1; }
    .link-pic { padding: 20px; overflow: hidden }
    .link-pic li { float: left; height: 34px; margin-right: 20px; border: 1px solid #E1E1E1; margin-bottom: 10px }
    .link-pic li img { height: 34px; width: 100% }
    .link-text { padding: 0 20px 20px 20px; overflow: hidden }
    .link-text li { float: left; margin-right: 20px }
    .link-text li a:hover { color: #09B1B9; }
    .copyright { padding: 0 20px; text-align: center; margin-bottom: 20px; clear: both; }
    .copyright a { color: #666 }
    /*cd-top*/
    .cd-top { display: inline-block; height: 40px; width: 40px; position: fixed; bottom: 40px; right: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.05); overflow: hidden; text-indent: 100%; white-space: nowrap; background: rgba(0, 0, 0, 0.8) url(../images/top.png) no-repeat center; visibility: hidden; opacity: 0; -webkit-transition: all 0.3s; -moz-transition: all 0.3s; transition: all 0.3s; z-index: 9999999 }
    .cd-top.cd-is-visible { visibility: visible; opacity: 1; }
    /*blogbox*/
    .blogbox { width: 70%; float: left; }
    .bloglist { margin: 20px; overflow: hidden; border: 1px solid #E1E1E1; background: #fff }
    .bloglist li { overflow: hidden }
    .bloglist h2 { border-top: 1px solid #eee; border-bottom: 1px solid #eee; background: #FDFDFD; font-weight: 400; font-size: 18px; line-height: 40px; padding-left: 20px; position: relative }
    .bloglist h2:before { content: ""; position: absolute; bottom: 0; left: 0; height: 100%; width: 3px; background: #09b1b9 }
    .bloglist li:hover h2 a { color: #09B1B9 }
    .bloglist li:nth-child(1) h2 { border-top: 0 }
    .bloglist li i { display: block; float: right; width: 20%; margin: 20px }
    .bloglist li i img { width: 100% }
    .blogtext { margin: 20px 0 20px 20px; overflow: hidden; text-overflow: ellipsis; -webkit-box-orient: vertical; display: -webkit-box; -webkit-line-clamp: 3; }
    .bloginfo { margin-left: 20px }
    .bloginfo span { padding-left: 20px }
    .bloginfo span, .bloginfo span a { margin-right: 1.5%; color: #999; }
    .bloginfo span a:hover { color: #09B1B9 }
    .bloginfo span:nth-child(1) { background: url(../images/yh.png) no-repeat left center }
    .bloginfo span:nth-child(2) { background: url(../images/sj.png) no-repeat left center }
    .bloginfo span:nth-child(3) { background: url(../images/bq.png) no-repeat left center }
    .bloginfo span:nth-child(4) { background: url(../images/yh.png) no-repeat left center }
    /*rside*/
    .rside { width: 30%; float: right }
    .rside .news { background: #fff; margin: 20px 20px 20px 0; border: 1px solid #e2e2e2; }
    .rside .news ul { padding: 10px }
    .rside .news ul li { display: inline-block; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; height: 28px; width: 96%; line-height: 28px; font-size: 14px; background: url(../images/li.png) no-repeat left center; padding-left: 15px; }
    .rside .news ul li span { float: right; color: #999; font-size: 12px; margin-left: 20px; }
    .rside .news ul li a:hover { color: #09B1B9; }
    /* pagelist */
    .pagelist { text-align: center; color: #666; width: 100%; clear: both; margin: 20px 0; padding-top: 20px }
    .pagelist a { color: #666; margin: 0 2px 5px; border: 1px solid #d2d2d2; padding: 5px 10px; display: inline-block; background: #fff; }
    .pagelist a:hover { color: #09B1B9; }
    .pagelist > b { border: 1px solid #09b1b9; padding: 5px 10px; background: #09b1b9; color: #fff; }
    /*contentbox*/
    .contentbox { border: 1px solid #E1E1E1; margin: 20px; background: #FFF; padding: 20px }
    .contitle { font-size: 20px; line-height: 26px; margin-bottom: 20px }
    .contentbox .bloginfo { margin-left: 0 }
    .contentbox .jianjie { margin: 20px 0; background: #f7f7f7; padding: 10px; }
    .jianjie b { color: #09b1b9; margin-right: 5px; }
    .entry p {  margin: 5px 0 10px; color: #666; font-size: 14px; line-height: 180%; word-break: break-all; }
    .entry img { max-width: 100% }
    .down { background: #FFFCEF; border: 1px solid #FFBB76; border-radius: 2px; font-size: 14px; margin: 20px; padding: 15px 20px; line-height: 26px; }
    .down p:first-child { margin-bottom: 15px }
    .downlink { overflow: hidden; margin: 20px 0; }
    .downbtn { background: #1BA1E2; border-radius: 2px; color: #FFFFFF; font-size: 14px; padding: 8px 30px; line-height: 35px; }
    .share { margin: 20px; }
    .nextinfo { margin: 20px 0 }
    .nextinfo p { padding: 4px 20px; }
    .nextinfo a:hover { color: #09B1B9; text-decoration: underline }
    .viewbox { border: 1px solid #E1E1E1; margin: 20px; background: #FFF; }
    .viewbox ul { padding: 10px; overflow: hidden }
    .viewbox ul li { width: 25%; float: left; overflow: hidden }
    .viewbox ul li a { margin: 0 8px; display: block }
    .viewbox ul li i { overflow: hidden; display: block }
    .viewbox ul li img { width: 100% }
    .viewbox ul li p { line-height: 18px; font-size: 14px; color: #666; margin: 10px 0 }
    .viewbox ul li:hover p { color: #09B1B9; }
    .viewbox ul li span { color: #999; font-size: 12px; line-height: 20px; }
    .pinlun { border: 1px solid #E1E1E1; margin: 20px; background: #FFF; }
    .gbok { padding: 20px }
    /*media*/
    #mnav { }
    #mnav a { color: #FFF }
    #mnavh { display: none; width: 30px; height: 40px; float: right; text-align: center; padding: 0 5px }
    .navicon { display: block; position: relative; width: 30px; height: 5px; background-color: #FFFFFF; margin-top: 20px }
    .navicon:before, .navicon:after { content: ''; display: block; width: 30px; height: 5px; position: absolute; background: #FFFFFF; -webkit-transition-property: margin, -webkit-transform; transition-property: margin, -webkit-transform; transition-property: margin, transform; transition-property: margin, transform, -webkit-transform; -webkit-transition-duration: 300ms; transition-duration: 300ms; }
    .navicon:before { margin-top: -10px; }
    .navicon:after { margin-top: 10px; }
    .open .navicon { background: none }
    .open .navicon:before { margin-top: 0; -webkit-transform: rotate(45deg); transform: rotate(45deg); }
    .open .navicon:after { margin-top: 0; -webkit-transform: rotate(-45deg); transform: rotate(-45deg); }
    .open .navicon:before, .open .navicon:after { content: ''; display: block; width: 30px; height: 5px; position: absolute; background: #fff; }
    #mside { display: none }
    @media screen and (min-width: 960px) and (max-width: 1023px) {
        .top-nav { float: right; margin-left: 0 }
        .top-nav ul li { padding-right: 23px; }
        .newsbox .news ul li span { display: none }
        .newsbox .news { margin: 0 5px 0 0 }
        .picbox { margin: 10px }
        .blogtext { -webkit-line-clamp: 2; }
        .bloglist li { margin-bottom: 10px }
        .rside .news ul li span { display: none }
        .bloglist { margin: 10px; }
        .rside .news { margin: 10px 10px 10px 0; }
        .links { margin: 0 10px 10px 10px; }
        .contentbox { margin: 10px }
        .viewbox { margin: 10px }
        .pinlun { margin: 10px }
    }
    @media screen and (min-width: 768px) and (max-width: 959px) {
        .top-nav { position: fixed; right: 0; float: right; margin-left: 0; background: #323841; width: 200px }
        .top-nav ul li { padding-right: 0 }
        #mnavh { display: block; }
        #nav { display: none; padding-left: 10px; border-left: #09b1b9 10px solid; }
        .search { display: none }
        .rside { display: none }
        .blogbox { width: 100% }
        .contentbox, .viewbox, .pinlun, .picbox { margin: 10px }
        .newsbox { margin: 0; padding: 0 10px; }
        .bloginfo { margin-bottom: 20px }
        .newsbox section { width: 50% }
        .links { margin: 0 10px 10px 10px }
        .newsbox .news { margin: 0 }
    }
    @media only screen and (max-width: 767px) {
        body { }
        .navicon { margin-top: 18px }
        .top-nav { position: fixed; right: 0; float: right; margin-left: 0; background: #323841; }
        .top-nav ul li { padding-right: 20px }
        #mnavh { display: block; }
        #nav { display: none; padding-left: 10px; border-left: #09b1b9 10px solid; }
        header { height: 98px; position: initial; }
        .search { width: 80%; margin-bottom: 10px }
        .side { display: none }
        .rside { display: none }
        .blogbox { width: 100% }
        .contentbox, .viewbox, .pinlun, .picbox, .bloglist { margin: 10px }
        .newsbox { margin: 0; padding: 0 10px; }
        .bloginfo { margin-bottom: 20px }
        .newsbox section { width: 100% }
        .links { margin: 0 10px 10px 10px }
        .newsbox .news { margin: 0 }
        .main-content { margin-left: 0; overflow: hidden; margin-top: 0 }
        .licur span { display: block }
        #starlist, .weixin { display: none; }
        .copyright { margin-bottom: 0 }
        #starlist li a { padding-left: 30px }
        #mside { display: block; text-align: center; font-size: 16px; line-height: 42px; background: #09B1B9; color: #fff }
        .but { position: relative; display: block }
        .but:after { position: absolute; content: "X"; width: 10px; top: 0; right: 5%; height: 4px }
        .bloglist h2 { font-size: 16px }
        .contentbox { padding: 10px }
        .down { margin: 0 }
        .viewbox ul li { width: 50%; margin-bottom: 20px }
        .nextinfo p { padding: 0 5px }
    }

</style>
