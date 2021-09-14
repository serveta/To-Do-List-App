import React from "react";

class GetAllUser extends React.Component {

    state = {
      users: []
    };
  
  
    async componentDidMount() {
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
        if(localStorage.getItem('token')){
        return (
            <div className="card text-center m-3">
                <h5 className="card-header">Users - Lists and Items</h5>
            
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
        else{
            return ('Please LogIn');
        }
    }
  }
  export default GetAllUser;