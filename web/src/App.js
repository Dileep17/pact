import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import Product from './components/product';
import './App.css';

class App extends Component{

  constructor() {
    super();
    this.state = {
      q: ""
    };
    // this.search = this.search.bind(this);
  }

  search(){
    if (document.getElementById("searchText").value.length !== 0) {
      ReactDOM.render(<Product data = {document.getElementById("searchText").value} />,document.getElementById("root"))
    }
  }

  render() {
    return(
      <div className="uicomponents">
        <input type="text" id="searchText" placeholder="search products" required />
        <button onClick={this.search}> Search </button>
      </div>
      )    
  }
}

export default App;
