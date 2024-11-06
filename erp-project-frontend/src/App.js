import logo from './logo.svg';
import './App.css';
import LoginPage from './pages/LoginPage'; // 路徑根據實際位置調整
import axios from "axios";
import UserContext, { _userInfo } from "./contexts/UserContext";
import { useEffect, useState } from "react";

function App() {
  const [authStatus, setAuthStatus] = useState(0);
  const [userInfo, setUserInfo] = useState(_userInfo);
  const [userToken, setUserToken] = useState(null);

  useEffect(() => {
    initAxios();
  }, [authStatus]);

  // Auto Login
  useEffect(() => {
    const __token = sessionStorage.getItem("GALAXY_TOKEN");
    if (!__token) {
      setAuthStatus(0);
      return;
    }
    setUserToken(__token);
    setAuthStatus(1);
    const __userInfo = sessionStorage.getItem("userInfo");
    setUserInfo(JSON.parse(__userInfo));
  }, []);

  const initAxios = async () => {
    // Add a request interceptor
    console.log("Init Axios")
    axios.interceptors.request.use(
      function (config) {
        console.log("request OK intercept: " + JSON.stringify(config))
        config.headers["ERP_API_TOKEN"] = userInfo.token;
        config.timeout = 60000;
        return config;
      },
      function (error) {
        console.log("request ERR intercept: " + JSON.stringify(error));
        return Promise.reject(error);
      }
    );


    // Add a response interceptor
    axios.interceptors.response.use(
      function (response) {
        return response;
      },
      function (error) {
        console.log("response ERR intercept: " + JSON.stringify(error));
        if (
          error.message &&
          error.message.indexOf("Network Error") !== -1
        ) {
          setAuthStatus(3);
        }
        return Promise.reject(error);
      }
    );
  };

  return (
    <div className="App">
      <UserContext.Provider
        value={{
          authStatus,
          setAuthStatus,
          userInfo,
          setUserInfo,
          userToken,
          setUserToken,
        }}
      >
        <LoginPage />
      </UserContext.Provider>
    </div>
  );
}




export default App;
