import React from 'react';
import './App.scss';

class App extends React.Component {
  constructor(){
    super();
    this.login = this.login.bind(this);
    this.state ={
      medLicence:"",
      password:""
    }

    this.changeHandler = this.changeHandler.bind(this)
  }

  changeHandler(val) {
    const {name, value} = val.target;
    this.setState({ [name]: value });
  }

  login(){
    alert(this.state.medLicence);
    alert(this.state.password);
  }

  render(){
    
    return (
      <div className="loginForm container">
        <input className="form-control form-control-lg" type="text" placeholder="Medical Licence Number" name="medLicence" onChange={this.changeHandler} />
        <input className="form-control form-control-lg" type="password" placeholder="password" name="password" onChange={this.changeHandler} />
        <button onClick={this.login} type="submit" className="btn btn-primary">Login</button>
      </div>
    );
  }
}

export default App;
