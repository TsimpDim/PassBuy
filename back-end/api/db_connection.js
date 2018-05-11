let mysql = require('mysql');
let db_conf = require('./db_conf.json'); // DB config file
let pool  = mysql.createPool(db_conf); // Create DB connection pool

// Run test DB connection
pool.getConnection((err, connection) => {
    if(err){
      // Display only an error message
      console.log("Error: Could not connect to database");
  }
    else {
      console.log("Database connection successful");
      // Close test connection
      connection.release();
    }
});

module.exports = pool;
