let express = require('express');
let router = express.Router();
let db = require('./db_connection');
let error_handling = require('./error_handling');

router.get('/api/stores', function(req, res) {
    db.query("SELECT * FROM stores", function(err, result, fields){

        // Handle errors if any
        if(err){
          res.send(error_handling("Could not retrieve stores"));
        } else{
          // Send the results if no errors encountered
          res.send(result);
        }
    });
});

router.get('/api/categories', function(req, res) {
    db.query("SELECT * FROM categories", function(err, result, fields){

        // Handle errors if any
        if (err) {
          res.send(error_handling("Could not retrieve categories"));
        } else {
          // Send the results if no errors encountered
          res.send(result);
        }
    });
});

router.get('/api/categories/:id', function(req, res) {
    db.query("SELECT * FROM categories WHERE category_id = ?",[req.params.id],
    function(err, result, fields){

        // Handle errors if any
        if (err || result.length == 0) {
          res.send(error_handling("Category not found"));
        } else {
          // Send the results if no errors encountered
          res.send(result[0]);
        }
   });
});

router.get('/api/products/:arg', function(req, res) {

    // Argument is a number - thus an id
    if(!isNaN(req.params.arg)){

      // Get product info
      db.query("SELECT * FROM products WHERE product_id = ?",[req.params.arg],
      function(err, result, fields){

          // Handle errors if any
          if (err || result.length == 0) {
            res.send(error_handling("Product not found"));
          } else {

            let response = result[0];

            // When the previous query ends, start a new one for the prices
            // Get product price
            db.query("SELECT store_id, price FROM product_prices WHERE product_id = ?",[req.params.arg],
            function(err, result, fields){
              // Handle errors if any
                if(err) {
                  response.prices = error_handling("No prices found");
                } else {
                  response.prices = result;
                }
                // When the new query finishes as well, send the response
                res.send(response);
            });
          }
        });
        
    // Argument is a string - thus a category or a search keyword
    } else {
        let response = {"category" : -1};

        db.query("SELECT name, description, image_url, product_id, category FROM ??",[req.params.arg],
        function(err, result, fields){
          
          // Handle errors if any
          if (err) {
            res.send(error_handling("Could not retrieve products from the given category"));
          } else {

            // Get category from the first product
            response.category = result[0].category;
            // Since every product is guaranteed to be from the same category
            result.forEach(function(v){ delete v.category });
            response.products = result;

            // Send the results if no errors encountered
            res.send(response);

          }
        });
    }
});

router.get('/api/products/search/:search_str', function(req, res){

  db.query("SELECT * FROM products WHERE name LIKE ?",['%' + req.params.search_str + '%'], 
  function(err, result, fields){

    if(err || result.length == 0){
      res.send(error_handling("No such product was found with the given keyword(s)"));
    } else {
      res.send(result);
    }

  });
});

router.get('/api/prices/:pr_id', function(req, res) {
    db.query("SELECT * FROM product_prices WHERE product_id = ?",[req.params.pr_id],
    function(err, result, fields){

      // Handle errors if any
      if (err || result.length == 0) {
        res.send(error_handling("Could not find prices for the given product"));
      } else {
        // Set up response object
        // Default value is the product_id itself
        // Type cast to integer avoiding letters being added on the final JSON object
        let response = {"product_id" : parseInt(req.params.pr_id)};

        // Add 'prices' array
        response.prices = [];

        // Add product prices to 'prices' array
        result.forEach(element => {
            response.prices.push({
                "store_id" : element.store_id,
                "price" : element.price
            });
        });
        // Send the results if no errors encountered
        res.send(response);
      }
    });
});

router.get('/api/prices/:pr_id/:str_id', function(req, res) {
    db.query("SELECT * FROM product_prices WHERE product_id = ? AND store_id = ?",[req.params.pr_id, req.params.str_id],
    function(err, result, fields){

      // Handle errors if any
      if (err || result == 0) {
        res.send(error_handling("Could not retrieve prices for the given product or store"));
      } else {
        // Send the results if no errors encountered
        res.send(result[0]);
      }
    });
});



module.exports = router;
