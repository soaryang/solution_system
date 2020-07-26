<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>飞哥博客</title>
    <meta name="keywords" content="关键词,关键词,关键词,关键词,5个关键词"/>
    <meta name="description" content="网站简介，不超过80个字"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="http://localhost/static/css/index.css" rel="stylesheet">
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <!--
    <link href="http://localhost/static/plug/markdown/css/editormd.css" rel="stylesheet" />
    <link href="/static/plug/markdown/css/editormd.css" rel="stylesheet" />
    <script src="/static/plug/markdown/lib/marked.min.js"></script>
    <script src="/static/plug/markdown/lib/prettify.min.js"></script>
    <script src="/static/plug/markdown/lib/raphael.min.js"></script>
    <script src="/static/plug/markdown/lib/underscore.min.js"></script>
    <script src="/static/plug/markdown/lib/sequence-diagram.min.js"></script>
    <script src="/static/plug/markdown/lib/flowchart.min.js"></script>
    <script src="/static/plug/markdown/lib/jquery.flowchart.min.js"></script>
    <script src="/static/plug/markdown/editormd.js"></script>
    -->
    <link href="https://pandao.github.io/editor.md/css/editormd.min.css" rel="stylesheet"/>
    <script src="https://pandao.github.io/editor.md/lib/marked.min.js"></script>
    <script src="https://pandao.github.io/editor.md/lib/prettify.min.js"></script>
    <script src="https://pandao.github.io/editor.md/lib/raphael.min.js"></script>
    <script src="https://pandao.github.io/editor.md/lib/underscore.min.js"></script>
    <script src="https://pandao.github.io/editor.md/lib/sequence-diagram.min.js"></script>
    <script src="https://pandao.github.io/editor.md/lib/flowchart.min.js"></script>
    <script src="https://pandao.github.io/editor.md/lib/jquery.flowchart.min.js"></script>
    <script src="https://pandao.github.io/editor.md/editormd.min.js"></script>


    <script>
        $(function () {
            var data = '${content?js_string}';
            var testEditormdView = editormd.markdownToHTML("test-editormd-view", {
                markdown: data,
                htmlDecode: "style,script,iframe",  // you can filter tags decode
                tocm: true,    // Using [TOCM]
                emoji: true,
                taskList: true,
                tex: true,  // 默认不解析
                flowChart: true,  // 默认不解析
                sequenceDiagram: true,  // 默认不解析
            });
        });
    </script>
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
        <div class="welcome">34234234</div>
        <div class="newsbox">

            <section style="width: 80%;">
                <div id="test-editormd-view" style="border: 1px solid rgb(221, 221, 221); width: 90%;">
                    <textarea style="display:none;" id="markdownValue"  name="test-editormd-markdown-doc"></textarea>
                </div>
            </section>
        </div>

        <div class="blank"></div>
    </div>
</main>
</body>

<script>

</script>
</html>
