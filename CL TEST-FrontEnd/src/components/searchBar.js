import React, {Component} from 'react'; //create and manage components

class SeachBar extends Component {
  // defining state in a class based componenet
  constructor(props) {
    super(props);

    this.state = {term: ''};
  }

  render() {
    return (  
      <div className="form-group">
        <input
        className= "form-control"
        placeholder= "Search"
        value={this.state.term}
        onChange = {event => this.onInputChange(event.target.value)} 
        />
        
      </div>
    );
  }

  onInputChange(term) {
    this.setState({term});
    this.props.onSearchTermChange(term);
  }
}

export default SeachBar;