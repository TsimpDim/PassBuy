import requests
import sys
import csv
import html

def main():
    '''Save product data from specific categories into a csv'''
    
    if len(sys.argv) < 3 or len(sys.argv) > 2:
        print("Usage : db_data.py <source_index> <db_index>")
        sys.exit()    

    index = sys.argv[1]
    db_index = sys.argv[2]

    source = "https://www.ab.gr/click2shop/c/_INDEX_/loadMore?q=%3Apopularity&sort=popularity&pageSize=300&pageNumber=0"

    r = requests.get(source.replace('_INDEX_', index))
    json_data = r.json()  # Read response as json

    with open("export.csv", 'w', newline='', encoding='utf-8') as csvfile:
        csv_writer = csv.writer(csvfile, delimiter='|')
        for product in json_data:
            name = product['name']
            desc = product['description']
            if not desc:
                desc = ""
            else:
                desc = html.unescape(desc)

            if product.get('images'):
                image = "https://www.ab.gr" + product['images'][0]['url']
            else:
                image = ''
            
            csv_writer.writerow([name, desc, image, db_index])



if __name__ == "__main__":
    main()
