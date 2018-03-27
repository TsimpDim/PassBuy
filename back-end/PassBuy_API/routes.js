let express = require('express');
let router = express.Router();

  
router.get('/', function(req, res) {
    res.send('<h2>PassBuy API default route</h2><hr>');
});

module.exports = router;