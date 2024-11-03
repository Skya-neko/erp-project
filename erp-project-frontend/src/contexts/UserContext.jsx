import React from 'react';


export const _userInfo = {
    userId: "",
    userName: "",
    menu: [],
    token: ""
}

const UserContext = React.createContext();

export default UserContext;