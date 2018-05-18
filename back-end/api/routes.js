let express = require('express');
let router = express.Router();
let db = require('./db_connection');
let error_handling = require('./error_handling');

router.get('/api/stores', (req, res) => {
	db.query("SELECT * FROM stores", (err, result) => {

		if (err)
			res.send(error_handling("Could not retrieve stores"));
		else
			res.send(result);

	});
});

router.get('/api/categories', (req, res) => {
	db.query("SELECT * FROM categories", (err, result) => {

		if (err)
			res.send(error_handling("Could not retrieve categories"));
		else
			res.send(result);

	});
});

router.get('/api/categories/:id', (req, res) => {
	db.query("SELECT * FROM categories WHERE category_id = ?", [req.params.id],
	(err, result) => {

		if (err || result.length == 0)
			res.send(error_handling("Category not found"));
		else
			res.send(result[0]);

	});
});

router.get('/api/products/:arg', (req, res) => {

	// Argument is a number - thus an id
	if (!isNaN(req.params.arg)) {

		// Get product info
		db.query("SELECT * FROM products WHERE product_id = ?", [req.params.arg],
		(err, result) => {

			if (err || result.length == 0)
				res.send(error_handling("Product not found"));
			else {

				let response = result[0];

				// When the previous query ends, start a new one for the prices
				db.query("SELECT store_id, price FROM product_prices WHERE product_id = ?", [req.params.arg],
					(err, result) => {

						if (err)
							response.prices = error_handling("No prices found");
						else
							response.prices = result;

						res.send(response);
					});
			}
		});

		// Argument is a string - thus a category
	} else {

		db.query("SELECT name, description, image_url, product_id, category FROM ??", [req.params.arg],
		(err, result) => {

			if (err)
				res.send(error_handling("Could not retrieve products from the given category"));
			else {

				// Since every product is guaranteed to be from the same category
				result.forEach((v) => { delete v.category; });

				res.send(result);
			}

		});
	}
});

router.get('/api/products/search/:search_str', (req, res) => {

	db.query("SELECT * FROM products WHERE name LIKE ?", ['%' + req.params.search_str + '%'],
	(err, result) => {

		if (err || result.length == 0)
			res.send(error_handling("No such product was found with the given keyword(s)"));
		else
			res.send(result);

	});
});

router.get('/api/prices/:pr_id', (req, res) => {
	db.query("SELECT * FROM product_prices WHERE product_id = ?", [req.params.pr_id],
	(err, result) => {

		if (err || result.length == 0)
			res.send(error_handling("Could not find prices for the given product"));
		else {

			// Since every price is going to be for the same product
			result.forEach((v) => { delete v.product_id; });
			res.send(result);
		}

	});
});

router.get('/api/prices/:pr_id/:str_id', (req, res) => {
	db.query("SELECT * FROM product_prices WHERE product_id = ? AND store_id = ?", [req.params.pr_id, req.params.str_id],
	(err, result) => {

		if (err || result == 0)
			res.send(error_handling("Could not retrieve prices for the given product or store"));
		else
			res.send(result[0]);

	});
});



module.exports = router;
