<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>飞哥博客</title>
    <meta name="keywords" content="关键词,关键词,关键词,关键词,5个关键词"/>
    <meta name="description" content="网站简介，不超过80个字"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/static/css/index.css" rel="stylesheet">
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/marked@0.3.6"></script>
    <script src="https://unpkg.com/lodash@4.16.0"></script>
    <link rel="stylesheet"  href="/static/css/style.css" />
</head>
<body>
<header>
    <div class="logo">飞哥博客</div>
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
        <div class="welcome"> 我是屌丝我怕谁</div>
        <div class="newsbox">
            <section style="width: 80%;">
                <div id="editor" style="border: #1BA1E2 0px solid; background-color: white;min-height: 600px;">
                    <!--<textarea :value="input" @input="update"></textarea>-->
                    <div v-html="compiledMarkdown"></div>
                </div>
            </section>
            <section style="width: 18%;margin-left: 10px;">
                <div id="editor2" style="border: #1BA1E2 0px solid; background-color: white;min-height: 600px;">
                    asdasd
                </div>
            </section>
        </div>
        <div class="blank"></div>
        <!--<div class="copyright">-->
        <!--<p>Copyright 2018 Inc. AllRights Reserved. Design by <a href="/">杨青个人博客</a> 蜀ICP备11002373号-1</p>-->
        <!--</div>-->
    </div>
</main>
</body>

<script>
    var object = new Vue({
        el: "#editor",
        data: {
            input: "${content}"
        },
        computed: {
            compiledMarkdown: function() {
                return marked(this.input, { sanitize: true });
            }
        },
        methods: {
            update: _.debounce(function(e) {
                this.input = e.target.value;
            }, 300)
        }
    });
</script>
</html>
