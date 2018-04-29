let express = require('express');
let router = express.Router();
let db = require('./db_connection');


router.get('/stores', function(req, res) {
    db.query("SELECT * FROM stores", function(err, result, fields){
        if(err) throw err;

        res.send(result);
    });
});

router.get('/categories', function(req, res) {
    db.query("SELECT * FROM categories", function(err, result, fields){
        if(err) throw err;

        res.send(result);
    });
});


router.get('/products/:arg', function(req, res) {

    // Arguement is number - thus an id
    if(!isNaN(req.params.arg)){

        db.query("SELECT * FROM products WHERE product_id = ?",[req.params.arg], // Get product info
        function(err, result, fields){

            if(err) throw err;

            let response = result[0];

            // When the previous query ends, start a new one for the prices
            db.query("SELECT store_id, price FROM product_prices WHERE product_id = ?",[req.params.arg], // Get price info
            function(err, result, fields){
                if(err){
                    console.log(err);
                    response.prices = {"error" : "No prices found"};
                }else{
                    response.prices = result;
                }

                res.send(response); // And when the new one finishes as well, send the response
            });
        
        });

    // Arguement is a string - thus a category
    }else{
        let response = {"category" : -1};

        db.query("SELECT name, description, image_url, product_id, category FROM ??",[req.params.arg],
        function(err, result, fields){
            if(err) throw err;

            response.category = result[0].category; // Get category from the first product
            result.forEach(function(v){ delete v.category }); // Since every product is guaranteed to be from the same category
            response.products = result;
            
            res.send(response);
        });
    }
});

router.get('/prices/:pr_id', function(req, res) {
    db.query("SELECT * FROM product_prices WHERE product_id = ?",[req.params.pr_id],
    function(err, result, fields){
        if(err) throw err;
        
        // Set up response object
        let response = {"product_id" : req.params.pr_id}; // Default value is the product_id itself
        response.prices = []; // Add prices array

        result.forEach(el => {
            response.prices.push({
                "store_id" : el.store_id,
                "price" : el.price
            });
        });

        res.send(response);
    });
});

router.get('/prices/:pr_id/:str_id', function(req, res) {
    db.query("SELECT * FROM product_prices WHERE product_id = ? AND store_id = ?",[req.params.pr_id, req.params.str_id],
    function(err, result, fields){
        if(err) throw err;

        res.send(result); 
    });
});



module.exports = router;
