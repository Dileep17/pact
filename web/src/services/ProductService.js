import adapter from 'axios/lib/adapters/http';
const axios = require('axios');

class ProductService {

    constructor(baseUrl, port){
        this.baseUrl = baseUrl;
        this.port = port;
    }

    getProduct(q) {
        return axios.request({
            method: 'GET',
            url: '/product/search/name/'+q,
            baseURL: this.baseUrl+':'+this.port,
            headers: {
                'Accept': 'application/json; charset=utf-8',
                'Content-Type': 'application/json; charset=utf-8'
            },
        }, adapter)
        .then(res => res.data);
    };

}

export default ProductService;