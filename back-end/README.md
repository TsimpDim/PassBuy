# Back-end Development

This sub-repository contains code regarding the back-end, including the database and the API.

## NodeJS

**Για την εγκατάσταση των απαραίτητων modules:**
    
    npm install

**Για την εκκίνηση του server:**
    
    node server.js

## Χρήση του API:

Κλήσεις στο API γίνονται από το url-βάση  

    http://snf-812693.vm.okeanos.grnet.gr:8080/api
Το API επιστρέφει τα δεδομένα που θα ζητηθούν σε μορφή [JSON](https://www.json.org).

### Τα endpoints είναι τα εξής:

• `/products/<int:product_id>`

Επιστρέφει όλες τις πληροφορίες για το προϊόν με `id` ίσο με `product_id`

#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/products/2`

Απάντηση :   

```
{
    "name": "ΑΝΑΨΥΚΤΙΚΟ ΚΟΥΤΙ 6 X 330 ML",
    "description": "Γιατί η ζωή έχει τη γεύση που της δίνεις! Ξεχωριστή απόλαυση και ανάλαφρη γεύση με λιγότερο από 1 θερμίδα.  H Coca-Cola light είναι ένα αναψυκτικό χωρίς ζάχαρη, που παρέχει λιγότερο από1 θερμίδα σε κάθε ποτήρι 250ml ή σε κάθε κουτάκι 330ml. Η ζάχαρη έχει αντικατασταθεί από γλυκαντικές ουσίες, όπως η ασπαρτάμη, η ακεσουλφάμη-Κ και το κυκλαμικό οξύ, οι οποίες είναι απολύτως ασφαλείς και αποδίδουν πρακτικά μηδενικές θερμίδες.",
    "image_url": "https://www.ab.gr/medias/sys_master/hd6/h08/8890363838494.jpg",
    "category": 1,
    "product_id": 2,
    "prices": [{
        "store_id": 1,
        "price": 3.14
    }, {
        "store_id": 2,
        "price": 3.42
    }, {
        "store_id": 3,
        "price": 3.9
    }, {
        "store_id": 4,
        "price": 3.69
    }]
}
```

• `/products/<str:category>`

Επιστρέφει όλα τα προϊόντα που βρίσκονται στην κατηγορία `category`. Οι κατηγορίες που επιτρέπονται είναι οι εξής:

1. beverages
2. dairy
3. delicatessen
4. frozen
5. fruits_veggies
6. health_beauty
7. home
8. meat_fish
9. misc
10. snacks


#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/products/health_beauty`

Απάντηση :   

```
{
   "category":7,
   "products":[
      {
         "name":"ΜΩΡΟΜΑΝΤΗΛΑ ΑΝΤΑΛΛΑΚΤΙΚΟ 3 Χ 72 ΤΕΜ",
         "description":"Οι μωροπετσέτες Babycare Χαμομήλι σχεδιάστηκαν για τον απαλό, καθημερινό καθαρισμό του βρεφικού δέρματος. Με εκχύλισμα χαμομηλιού, καθαρίζουν απαλά, περιποιούνται το βρεφικό δέρμα και συμβάλλουν στην προστασία από ερεθισμούς και συγκάματα.",
         "image_url":"https://www.ab.gr/medias/sys_master/hce/h02/8838327074846.jpg",
         "product_id":3851
      },
      {
         "name":"ΜΩΡΟΜΑΝΤΗΛΑ ΑΝΤΑΛΛΑΚΤΙΚΟ 80 ΤΕΜ",
         "description":"Υγρά μωρομάντηλα από την 365 απαλά και ανθεκτικά, ιδανικά για τον καθημερινό καθαρισμό καθαρισμό των μωρών. Είναι εμποτισμένα με καθαριστική λοσιόν χωρίς αλκοόλ. Είναι πλούσιο σε εκχύλισμα χαμομηλιού και προβιταμίνη Β5. Χωρίς parabens.",
         "image_url":"https://www.ab.gr/medias/sys_master/hc8/h36/8836291788830.jpg",
         "product_id":3852
      },
      {
      ...
      }
   ]
}
```


• `/categories`

Επιστρέφει όλες τις πληροφορίες για όλες τις κατηγορίες

#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/categories`

Απάντηση :   

```
[
   {
      "name":"Ροφήματα",
      "category_id":1
   },
   {
      "name":"Γαλακτοκομικά",
      "category_id":2
   },
   {
      "name":"Delicatessen",
      "category_id":3
   },
   {
      "name":"Κατεψυγμένα τρόφιμα",
      "category_id":4
   },
   {
      "name":"Φρούτα και λαχανικά",
      "category_id":5
   },
   {
      "name":"Σνακ",
      "category_id":6
   },
   {
      "name":"Προϊόντα ομορφιάς και προσωπικής υγιεινής",
      "category_id":7
   },
   {
      "name":"Προϊόντα οικιακής χρήσης",
      "category_id":8
   },
   {
      "name":"Φρέσκα κρεατικά και ψαρικά είδη",
      "category_id":9
   },
   {
      "name":"Άλλα προϊόντα",
      "category_id":10
   }
]
```

• `/categories/<int:category_id>`

Επιστρέφει όλες τις πληροφορίες για την κατηγορία με `id` ίσο με `category_id`.

#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/categories/2`

Απάντηση :   

```
[
   {
      "name":"Γαλακτοκομικά",
      "category_id":2
   }
]
```

• `/prices/<int:product_id>`

Επιστρέφει τις τιμές του προϊόντος με `id` ίσο με `product_id` σε όλα τα καταστήματα.

#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/prices/2321`

Απάντηση :   

```
{
   "product_id":2321,
   "prices":[
      {
         "store_id":1,
         "price":3.13
      },
      {
         "store_id":2,
         "price":2.8
      },
      {
         "store_id":3,
         "price":2.01
      },
      {
         "store_id":4,
         "price":3.91
      }
   ]
}
```

• `/prices/<int:product_id>/<int:store_id>`

Επιστρέφει τις τιμές του προϊόντος με `id` ίσο με `product_id` στο κατάστημα με `id` ίσο με `store_id`.


#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/prices/2321/2`

Απάντηση :   

```
[
   {
      "store_id":2,
      "price":2.8,
      "product_id":2321
   }
]
```

• `/stores`

Επιστρέφει όλες τις πληροφορίες για όλα τα καταστήματα.

#### Παράδειγμα:

Κλήση : `http://snf-812693.vm.okeanos.grnet.gr:8080/api/stores`

Απάντηση :   

```
[
   {
      "name":"ΑΒ Βασιλόπουλος",
      "id":1
   },
   {
      "name":"Μασούτης",
      "id":2
   },
   {
      "name":"LIDL",
      "id":3
   },
   {
      "name":"Discount Markt",
      "id":4
   }
]
```
