let mysql = require('mysql');
let db_creds = require('./db_creds.json'); // DB config file
let pool  = mysql.createPool(db_creds); // Create DB connection pool

// Test DB connection
pool.getConnection((err) => {
    if(err){
      // Display only an error message
      console.log("Error: Could not connect to database");
  }
    else {
      console.log("Database connection successful");
    }
});

module.exports = pool;
