let mysql = require('mysql');
let db_creds = require('./db_creds.json'); // DB config file
let db;



function connectDatabase(){
    if(!db){
        db = mysql.createConnection(db_creds);
        
        db.connect(function(err){ // Connect to DB
            if(err) throw err;
            
            console.log("Connected to DB successfully");
        });
    }

    return db;
}

module.exports = connectDatabase();