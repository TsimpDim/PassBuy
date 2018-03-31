CREATE TABLE `store_names` (
	`alternative_name` varchar NOT NULL,
	`store_id` INT NOT NULL
);

CREATE TABLE `products` (
	`name` varchar NOT NULL,
	`description` TEXT NOT NULL,
	`image_link` varchar NOT NULL,
	`category` INT NOT NULL,
	`product_id` INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`product_id`)
);

CREATE TABLE `product_prices` (
	`store_id` INT NOT NULL,
	`price` FLOAT NOT NULL,
	`product_id` INT NOT NULL
);

CREATE TABLE `stores` (
	`main_name` varchar NOT NULL,
	`id` INT NOT NULL AUTO_INCREMENT UNIQUE,
	PRIMARY KEY (`id`)
);

CREATE TABLE `categories` (
	`name` varchar NOT NULL,
	`category_id` INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (`category_id`)
);

ALTER TABLE `store_names` ADD CONSTRAINT `store_names_fk0` FOREIGN KEY (`store_id`) REFERENCES `stores`(`id`);

ALTER TABLE `products` ADD CONSTRAINT `products_fk0` FOREIGN KEY (`category`) REFERENCES `categories`(`category_id`);

ALTER TABLE `product_prices` ADD CONSTRAINT `product_prices_fk0` FOREIGN KEY (`store_id`) REFERENCES `stores`(`id`);

ALTER TABLE `product_prices` ADD CONSTRAINT `product_prices_fk1` FOREIGN KEY (`product_id`) REFERENCES `products`(`product_id`);