import urllib.request
import requests

def main():
    
    source = "https://www.ab.gr/click2shop/c/_INDEX_/loadMore?q=%3Apopularity&sort=popularity&pageSize=300&pageNumber=0"

    for i in range(1, 14):
        
        curr_index = "{0:0=3d}".format(i)
        r = requests.get(source.replace('_INDEX_', curr_index))
        json_data = r.json()

        for i,prod in enumerate(json_data):
            if prod.get('images'):
                urllib.request.urlretrieve("https://www.ab.gr" + prod['images'][0]['url'], "images/" + str(curr_index) + "_" + str(i) + ".jpg")


if __name__ == "__main__":
    main()