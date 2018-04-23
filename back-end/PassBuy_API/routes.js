let express = require('express');
let router = express.Router();
let db = require('./connection');

  
router.get('/stores', function(req, res) {
    db.query("SELECT * FROM stores", function(err, result, fields){
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

module.exports = router;