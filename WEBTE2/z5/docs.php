<!doctype html>
<html>
<head>
    <title>WEBTE2-CV5-API-DOCS</title>
    <style type="text/css">
        body {
            font-family: Trebuchet MS, sans-serif;
            font-size: 15px;
            color: white;
            background-color: black;
            margin-right: 24px;
        }
        .example {
            color: black !important;
        }

        h1	{
            font-size: 25px;
        }
        h2	{
            font-size: 20px;
        }
        h3	{
            font-size: 16px;
            font-weight: bold;
        }
        hr	{
            height: 1px;
            border: 0;
            color: #ddd;
            background-color: #ddd;
        }

        .app-desc {
            clear: both;
            margin-left: 20px;
        }
        .param-name {
            width: 100%;
        }
        .license-info {
            margin-left: 20px;
        }

        .license-url {
            margin-left: 20px;
        }

        .model {
            margin: 0 0 0px 20px;
        }

        .method {
            margin-left: 20px;
        }

        .method-notes	{
            margin: 10px 0 20px 0;
            font-size: 90%;
            color: #555;
        }

        pre {
            padding: 10px;
            margin-bottom: 2px;
        }

        .http-method {
            text-transform: uppercase;
        }

        pre.get {
            background-color: #0f6ab4;
        }

        pre.post {
            background-color: #10a54a;
        }

        pre.put {
            background-color: #c5862b;
        }

        pre.delete {
            background-color: #a41e22;
        }

        .huge	{
            color: #fff;
        }

        pre.example {
            background-color: #f3f3f3;
            padding: 10px;
            border: 1px solid #ddd;
        }

        code {
            white-space: pre;
        }

        .nickname {
            font-weight: bold;
        }

        .method-path {
            font-size: 1.5em;
            background-color: #0f6ab4;
        }

        .up {
            float:right;
        }

        .parameter {
            width: 500px;
        }

        .param {
            width: 500px;
            padding: 10px 0 0 20px;
            font-weight: bold;
        }

        .param-desc {
            width: 700px;
            padding: 0 0 0 20px;
            color: #777;
        }

        .param-type {
            font-style: italic;
        }

        .param-enum-header {
            width: 700px;
            padding: 0 0 0 60px;
            color: #777;
            font-weight: bold;
        }

        .param-enum {
            width: 700px;
            padding: 0 0 0 80px;
            color: #777;
            font-style: italic;
        }

        .field-label {
            padding: 0;
            margin: 0;
            clear: both;
        }

        .field-items	{
            padding: 0 0 15px 0;
            margin-bottom: 15px;
        }

        .return-type {
            clear: both;
            padding-bottom: 10px;
        }

        .param-header {
            font-weight: bold;
        }

        .method-tags {
            text-align: right;
        }

        .method-tag {
            background: none repeat scroll 0% 0% #24A600;
            border-radius: 3px;
            padding: 2px 10px;
            margin: 2px;
            color: #FFF;
            display: inline-block;
            text-decoration: none;
        }
        a {
            color: white !important;
        }

    </style>
</head>
<body>
<h1>WEBTE2-CV5-API-DOCS</h1>

<h3>Table of Contents </h3>
<div class="method-summary"></div>
<h4><a href="#Events">Events</a></h4>
<ul>
    <li><a href="#getEventsByYear"><code><span class="http-method">get</span> /cv5/events/{year}</code></a></li>
</ul>
<h4><a href="#Invention">Invention</a></h4>
<ul>
    <li><a href="#addInvention"><code><span class="http-method">post</span> /cv5/inventions</code></a></li>
    <li><a href="#getInventionsByCentury"><code><span class="http-method">get</span> /cv5/inventions/{century}</code></a></li>
</ul>
<h4><a href="#Inventor">Inventor</a></h4>
<ul>
    <li><a href="#addInventor"><code><span class="http-method">post</span> /cv5/inventors</code></a></li>
    <li><a href="#delInventorById"><code><span class="http-method">delete</span> /cv5/inventors/{id}</code></a></li>
    <li><a href="#getInventorById"><code><span class="http-method">get</span> /cv5/inventors/{id}</code></a></li>
    <li><a href="#getInventorBySurname"><code><span class="http-method">get</span> /cv5/inventors/surname/{surname}</code></a></li>
    <li><a href="#getInventors"><code><span class="http-method">get</span> /cv5/inventors</code></a></li>
    <li><a href="#updateInventor"><code><span class="http-method">put</span> /cv5/inventors</code></a></li>
</ul>

<h1><a name="Events">Events</a></h1>
<div class="method"><a name="getEventsByYear"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="get"><code class="huge"><span class="http-method">get</span> /cv5/events/{year}</code></pre></div>
    <div class="method-summary">Get all events by year</div>
    <div class="method-notes"></div>

    <h3 class="field-label">Path parameters</h3>
    <div class="field-items">
        <div class="param">year (required)</div>

        <div class="param-desc"><span class="param-type">Path Parameter</span> &mdash; The year that needs to be fetched </div>
    </div>  <!-- field-items -->








    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>
[
    {
        "name": "string",
        "surname": "string",
        "death_date": "string"
    },
    {
        "name": "string",
        "surname": "string",
        "birth_date": "string"
    },
    {
        "invention_date": "string",
        "description": "string"
    }
]
</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">200</h4>
    successful operation
    <h4 class="field-label">404</h4>
    Year not provided
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<h1><a name="Invention">Invention</a></h1>
<div class="method"><a name="addInvention"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="post"><code class="huge"><span class="http-method">post</span> /cv5/inventions</code></pre></div>
    <div class="method-summary">Add new invention to inventor</div>
    <div class="method-notes"></div>


    <h3 class="field-label">Consumes</h3>
    This API call consumes the following media types via the <span class="header">Content-Type</span> request header:
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Request body</h3>
    <div class="field-items">
        <div class="param"></div>
        <pre class="example">
            <code>
{
    "inventor_id": "50",
    "date": "15.06.1931",
    "description": "string"
}</code>
        </pre>



    </div>  <!-- field-items -->




    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        object json

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example">
            <code>
{
    "id": 123,
    "inventor_id": 50,
    "date": "15.06.1931",
    "description": "string"
}</code>
        </pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">201</h4>
    successful operation
    <h4 class="field-label">400</h4>
    Date or description not provided
    <a href="#"></a>
    <h4 class="field-label">404</h4>
    Inventor not found
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<div class="method"><a name="getInventionsByCentury"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="get"><code class="huge"><span class="http-method">get</span> /cv5/inventions/{century}</code></pre></div>
    <div class="method-summary">Get all inventions with by century</div>
    <div class="method-notes"></div>

    <h3 class="field-label">Path parameters</h3>
    <div class="field-items">
        <div class="param">century (required)</div>

        <div class="param-desc"><span class="param-type">Path Parameter</span> &mdash; The century that needs to be fetched </div>
    </div>  <!-- field-items -->






    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        json array

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>[
    {
        "invention_date": "string",
        "description": "string"
    }
]</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">200</h4>
    successful operation
    <h4 class="field-label">404</h4>
    Century not provided
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<h1><a name="Inventor">Inventor</a></h1>
<div class="method"><a name="addInventor"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="post"><code class="huge"><span class="http-method">post</span> /cv5/inventors</code></pre></div>
    <div class="method-summary">Add a new inventor with his invention</div>
    <div class="method-notes"></div>


    <h3 class="field-label">Consumes</h3>
    This API call consumes the following media types via the <span class="header">Content-Type</span> request header:
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Request body</h3>
    <div class="field-items">
        <pre class="example"><code>{
    "name": "jano",
    "surname": "smutny",
    "birth_date": "15.06.1931",
    "birth_place": "Poprad",
    "inventor_description": "vynálezca padáku",
    "death_date": "12.06.1991",
    "death_place": "Poprad",
    "invention_description": "padák",
    "invention_date": "02.05.1971"
}</code></pre>


    </div>  <!-- field-items -->




    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        inventor json with array of inventions

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>{
    "name": "jano",
    "surname": "smutny",
    "birth_date": "15.06.1931",
    "birth_place": "Poprad",
    "inventor_description": "vynálezca padáku",
    "death_date": "12.06.1991",
    "death_place": "Poprad",
    "inventions": [
        "invention": "string"
    ]
}</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">201</h4>
    successful operation
    <h4 class="field-label">409</h4>
    Inventor already exists
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<div class="method"><a name="delInventorById"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="delete"><code class="huge"><span class="http-method">delete</span> /cv5/inventors/{id}</code></pre></div>
    <div class="method-summary">Delete inventor by ID</div>
    <div class="method-notes"></div>

    <h3 class="field-label">Path parameters</h3>
    <div class="field-items">
        <div class="param">id (required)</div>

        <div class="param-desc"><span class="param-type">Path Parameter</span> &mdash; The id that needs to be fetched </div>
    </div>  <!-- field-items -->







    <!--Todo: process Response Object and its headers, schema, examples -->



    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">204</h4>
    successful operation
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<div class="method"><a name="getInventorById"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="get"><code class="huge"><span class="http-method">get</span> /cv5/inventors/{id}</code></pre></div>
    <div class="method-summary">Get inventor by ID</div>
    <div class="method-notes"></div>

    <h3 class="field-label">Path parameters</h3>
    <div class="field-items">
        <div class="param">id (required)</div>

        <div class="param-desc"><span class="param-type">Path Parameter</span> &mdash; The id that needs to be fetched </div>
    </div>  <!-- field-items -->






    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        inventor json with array of inventions

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>{
    "id": 5,
    "name": "jano",
    "surname": "smutny",
    "birth_date": "15.06.1931",
    "birth_place": "Poprad",
    "inventor_description": "vynálezca padáku",
    "death_date": "12.06.1991",
    "death_place": "Poprad",
    "inventions": [
        "string"
    ]
}</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">200</h4>
    successful operation
    <h4 class="field-label">404</h4>
    Inventor not found
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<div class="method"><a name="getInventorBySurname"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="get"><code class="huge"><span class="http-method">get</span> /cv5/inventors/surname/{surname}</code></pre></div>
    <div class="method-summary">Get inventor by surname</div>
    <div class="method-notes"></div>

    <h3 class="field-label">Path parameters</h3>
    <div class="field-items">
        <div class="param">surname (required)</div>

        <div class="param-desc"><span class="param-type">Path Parameter</span> &mdash; The surname that needs to be fetched </div>
    </div>  <!-- field-items -->






    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        inventor json

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>{
    "id": 0,
    "name": "string",
    "surname": "string",
    "birth_date": "string",
    "birth_place": "string",
    "death_date": "string",
    "death_place": "string",
    "description": "string"
}</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">200</h4>
    successful operation
    <h4 class="field-label">404</h4>
    Inventor not found
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<div class="method"><a name="getInventors"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="get"><code class="huge"><span class="http-method">get</span> /cv5/inventors</code></pre></div>
    <div class="method-summary">Get all inventors</div>
    <div class="method-notes"></div>







    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        inventor json array

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>[
    {
        "id": "string",
        "name": "string",
        "surname": "string",
        "birth_date": "string",
        "birth_place": "string",
        "description": "string",
        "death_date": "string",
        "death_place": "string"
    }
]</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">200</h4>
    successful operation
    <h4 class="field-label">404</h4>
    Inventor not found
    <a href="#"></a>
</div> <!-- method -->
<hr/>
<div class="method"><a name="updateInventor"/>
    <div class="method-path">
        <a class="up" href="#__Methods">Up</a>
        <pre class="put"><code class="huge"><span class="http-method">put</span> /cv5/inventors</code></pre></div>
    <div class="method-summary">Update an existing inventor</div>
    <div class="method-notes"></div>


    <h3 class="field-label">Consumes</h3>
    This API call consumes the following media types via the <span class="header">Content-Type</span> request header:
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Request body</h3>
    <div class="field-items">
         <pre class="example"><code>
{
    "id": "25",
    "name": "jano",
    "surname": "",
    "birth_date": "15.06.1931",
    "birth_place": "Poprad",
    "description": "",
    "death_date": "",
    "death_place": "Poprad"
 }</code></pre>

        <div class="param-desc">Leave entry blank to preserve original data</div>

    </div>  <!-- field-items -->




    <h3 class="field-label">Return type</h3>
    <div class="return-type">
        inventor json

    </div>

    <!--Todo: process Response Object and its headers, schema, examples -->

    <h3 class="field-label">Example data</h3>
    <div class="example-data-content-type">Content-Type: application/json</div>
    <pre class="example"><code>{
    "id": 25,
    "name": "string",
    "surname": "string",
    "birth_date": "string",
    "birth_place": "string",
    "death_date": "string",
    "death_place": "string",
    "description": "string"
}</code></pre>

    <h3 class="field-label">Produces</h3>
    This API call produces the following media types according to the <span class="header">Accept</span> request header;
    the media type will be conveyed by the <span class="header">Content-Type</span> response header.
    <ul>
        <li><code>application/json</code></li>
    </ul>

    <h3 class="field-label">Responses</h3>
    <h4 class="field-label">200</h4>
    successful operation
    <h4 class="field-label">404</h4>
    Inventor not found
    <a href="#"></a>
</div> <!-- method -->
<hr/>
</body>
</html>
