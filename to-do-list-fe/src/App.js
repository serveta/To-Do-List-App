import logo from './logo.svg';
import './App.css';
import React from 'react';

import Login from './login.js';
import GetAllUser from './getAllUser';

class App extends React.Component {

  render() {
    return (
        <div>
            <h3 className="p-3 text-center">To-Do-List App</h3>
            <Login />
            <GetAllUser />
        </div>
    )
  }

  /*
  state = {
    loginToken: null,
    users: []
  };


    async componentDidMount() {
        const requestOptions = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({email: 'user@user.com', password: 'user'})
        };
        const response = await fetch('/login', requestOptions);
        const data = await response.text();
        this.setState({ loginToken: data });
        localStorage.setItem('token', data);

        const GetAllUserOptions = {
          method: 'GET',
          headers: { 'Accept': 'application/json',
              'Content-Type': 'application/json', 
              'Authorization': 'Bearer '+localStorage.getItem('token')}
        };
        const responseUsers = await fetch('/users', GetAllUserOptions);
        const body = await responseUsers.json();
        this.setState({users: body});
    }

    render() {
      const {users} = this.state;
      return (
          <div className="card text-center m-3">
              <h5 className="card-header">POST Request with Async/Await</h5>
              <div className="card-body">
                  Returned: {localStorage.getItem('token')}
              </div>

              <h2><u>Users - Lists and Items</u></h2>
                  {users.map(user =>
                      <div key={user.id}>
                      <br/>
                        <h5>- <i>User</i></h5>
                        {user.name} ({user.email})


                        {user.toDoLists.map(toDoList =>
                            <div key={toDoList.id}>
                            <br/>
                            <h5>- <i>To Do List</i></h5>
                                 {toDoList.name}

                                 <br/><br/>
                                 <h5>- <i>{toDoList.name} Items</i></h5>
                                 {toDoList.toDoItems.map(toDoItem =>
                                      <div key={toDoItem.id}>
                                           {toDoItem.name}
                                      </div>
                                 )}

                            </div>
                        )}
                      </div>
                  )}
          </div>
      );
  }
  */
}
export default App;
