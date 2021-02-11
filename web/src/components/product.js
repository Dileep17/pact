import React, {Component} from 'react';
import '../App.css';
import ProductService from "../services/ProductService"

class Product extends Component {

    constructor(props) {
        super(props);
        this.state = {
            products: []
        };
    }

    componentWillMount() {
        const productService = new ProductService('http://localhost', "8081");
        productService.getProduct(this.props.data)
        .then((data) => {
            this.setState({ products: data })
        })
        .catch(console.log)
    }

  render() {
    return(
        <div className="uicomponents">
            
            <table>
                <tbody>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Variant</th>
                        <th>Price</th>
                    </tr>
                    {this.state.products.map((product) => (
                        <tr>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.variant}</td>
                            <td>{product.price}</td>
                        </tr>           
                    ))}
                </tbody>
            </table>
        </div>
      )    
  }
}

export default Product;