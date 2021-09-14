import React from 'react';

class Login extends React.Component {

    state = {
        loginToken: null,
        email: '',
        password: ''
    };
    
    handleChange(e) {
        this.setState({ [e.target.name] : e.target.value });
        //console.log('handle change called - ' + 
        //e.target.name + ' ' + e.target.value + 
        //'this.state' + this.state.email + ' ' + this.state.password)
     }

    handleSubmit = async event => {
        event.preventDefault();
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({email: this.state.email, password: this.state.password})
        };
        
        const response = await fetch('/login', requestOptions);
        const data = await response.text();
        this.setState({ loginToken: data });
        if(data.length > 150){
            localStorage.setItem('token', data);
            window.location.reload();
        }
        else{
            alert("try again!")
        }
    }

/*    async componentDidMount() {
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({email: this.state.email, password: this.state.password})
        };
        
        const response = await fetch('/login', requestOptions);
        const data = await response.text();
        this.setState({ loginToken: data });
        if(data.length > 150){
            localStorage.setItem('token', data);
        }
        else{
            localStorage.setItem('token', 'null');
        }
    }
*/

logOut() {
    window.localStorage.removeItem("token");
    window.location.reload();
}

    render() {
        if(!localStorage.getItem('token') || localStorage.getItem('token') === 'null'){
            return (
                <div className="card text-center m-3">
                    <h5 className="card-header">Login</h5>
                    <div className="card-body">
                    
                       <form onSubmit={this.handleSubmit}>
                            <label>
                                Email:
                                <input type='text' name='email' 
                                    value={this.state.email} 
                                    onChange={this.handleChange.bind(this)} />
                            </label>
                            <label>
                                Password:
                                <input type='password' name='password' 
                                    value={this.state.password} 
                                    onChange={this.handleChange.bind(this)} />
                            </label>
                            <input type="submit" value="Login" />
                       </form>

                    </div>
                </div>
            );
        }else{
            return (
                <div className="card text-center m-3">
                    <h5 className="card-header">loginToken</h5>
                    <div className="card-body">
                       {localStorage.getItem('token')}
                    </div>
                    <input type="submit" value="Logout" onClick={this.logOut} />
                </div>
            );
        }
    }
}
export default Login;