import requests
import sys
import csv
import html
from decimal import Decimal,ROUND_HALF_UP, getcontext
import random as rnd


def main():
    '''Save product data for the product_prices table into a csv'''  

    if len(sys.argv) < 2 or len(sys.argv) > 2:
        print("Usage : db_prod_prices.py <source_index>")
        sys.exit()

    index = sys.argv[1]

    with open("product_id.txt","a+") as idx_f:
        idx_f.seek(0)
        try:
            prod_id = int(idx_f.readline())
        except:
            prod_id = 1

        source = "https://www.ab.gr/click2shop/c/_INDEX_/loadMore?q=%3Apopularity&sort=popularity&pageSize=1000&pageNumber=0"

        r = requests.get(source.replace('_INDEX_', index))
        json_data = r.json()  # Read response as json

        with open("export.csv", 'w', newline='', encoding='utf-8') as csvfile:
            csv_writer = csv.writer(csvfile, delimiter='|')
            getcontext().prec = 3

            for product in json_data:
                base_price = Decimal(product["price"]["value"])

                for store_id in range(1, 7): # [1,6]
                    variance = Decimal(rnd.uniform(-0.5, 0.5))
                    store_price = base_price + base_price * variance

                    csv_writer.writerow([prod_id, store_price, store_id])

                prod_id += 1

        idx_f.seek(0)
        idx_f.truncate()
        idx_f.write(str(prod_id))



if __name__ == "__main__":
    main()
