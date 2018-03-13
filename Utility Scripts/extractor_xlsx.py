import requests
import openpyxl as opx

def main():
    '''Download product info from source and save it into an xlsx file'''

    source = "https://www.ab.gr/click2shop/c/_INDEX_/loadMore?q=%3Apopularity&sort=popularity&pageSize=300&pageNumber=0"
    wb = opx.Workbook()

    # For all 14 categories
    for i in range(1, 14):

        # Formats index into 3 digits (e.g 001, 002 etc)
        curr_index = "{0:0=3d}".format(i)

        r = requests.get(source.replace('_INDEX_', curr_index))
        json_data = r.json()  # Read response as json

        new_worksheet = wb.create_sheet(json_data[0]['department'].replace('/',''))
        new_worksheet['A1'] = 'Product'
        new_worksheet['B1'] = 'Price' 
        new_worksheet['C1'] = 'Description'
        new_worksheet['D1'] = 'Image'


        for i,product in enumerate(json_data):
            name = product['name']
            edited_price = product['price']['value']
            desc = product['description']

            if product.get('images'):
                image = "https://www.ab.gr" + product['images'][0]['url']
            else:
                image = ''

            new_worksheet['A' + str(i+2)] = name
            new_worksheet['B' + str(i+2)] = edited_price
            new_worksheet['C' + str(i+2)] = desc
            new_worksheet['D' + str(i+2)] = image

    wb.save(filename='products.xlsx')


if __name__ == "__main__":
    main()
