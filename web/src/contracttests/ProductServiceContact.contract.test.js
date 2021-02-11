/**
 * @jest-environment node
 */

import {eachLike, like} from "@pact-foundation/pact/dsl/matchers";
import ProductService from "../services/ProductService"

describe('some test', function(){

    describe("search product with name", () => {
        it('seach exisitng product with name', async () => {
            
           const ID = 2;
           const NAME = "some product";
           const PRICE = 14.32;
           const VARIANT = "1 ltr";

            global.provider.addInteraction({
                state: 'products exist',
                uponReceiving: 'get search products with name',
                withRequest: {
                    method: 'GET',
                    path: '/product/search/name/milk'
                },
                willRespondWith: {
                    status: 200,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: eachLike({
                        "id": ID,
                        "name": NAME,
                        "price": PRICE,
                        "variant": VARIANT
                   }),
                },
            });
            
            const productService = new ProductService("http://127.0.0.1", "8082");  
            const product = await productService.getProduct("milk");
            expect(product).toStrictEqual([{
                "id": ID,
                "name": NAME,
                "price": PRICE,
                "variant": VARIANT
           }]);

        })
    })

   
})
