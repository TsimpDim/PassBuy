let express = require('express');
let app = express();
app.use(require('./routes')); // Set up seperate routes file

let port = process.env.PORT || 8080; // Set PORT

let server = app.listen(port, () => { // Start server
    let host = server.address().address;
    console.log("PassBuy_API listening at http:%s:%s", host, port);
});
