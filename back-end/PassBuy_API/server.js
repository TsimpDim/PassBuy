let express = require('express');
let app = express();
app.use(require('./routes.js')); // Set up seperate routes file

let mysql = require('mysql');
let connection = mysql.createConnection({ // DB credentials
    host : '',
    user : '',
    password : '',
    database : ''

});

let port = process.env.PORT || 8080; // Set PORT

let server = app.listen(port, function(){ // Start server
    let host = server.address().address;
    console.log("PassBuy_API listening at http:%s:%s", host, port);
});
