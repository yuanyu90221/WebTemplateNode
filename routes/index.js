var express = require('express');
var router = express.Router();
var fs = require('fs');
var rootPath = __dirname.replace('\\routes','\\views');
/* GET home page. */
router.get('/', function(req, res, next) {
  //res.render('index', { title: 'WebTemplateNode' });
  // res.writeHead(200,{'Content-Type':'text/html;charset=utf-8'});
  // res.write();
  // res.end();
  res.sendFile('/login.html',{root:rootPath});
});

router.get('/login', function(req, res){
	res.json(200,{"json":1});
});

module.exports = router;
