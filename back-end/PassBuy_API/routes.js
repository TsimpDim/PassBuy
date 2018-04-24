let express = require('express');
let router = express.Router();
let db = require('./connection');

  
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

router.get('/categories/:id', function(req, res) {
    db.query("SELECT * FROM categories WHERE category_id = ?",[req.params.id],
    function(err, result, fields){
        if(err) throw err;

        res.send(result);
    });
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

module.exports = router;