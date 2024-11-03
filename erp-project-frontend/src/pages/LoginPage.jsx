import React, { useState } from 'react';
import { Box, Button, Container, TextField, Typography } from '@mui/material';
import { execLogin } from "../components/LoginApi";
import UserContext from "../contexts/UserContext";

function LoginPage() {
    const [account, setAccount] = useState('');
    const { setAuthStatus, userInfo, setUserInfo, setUserToken } = React.useContext(UserContext);
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');



    const login = async (event) => {
        event.preventDefault(); // 防止表單提交，避免 URL 顯示帳號密碼
        // Validate
        if (account === "" || password === "") {
            return;
        }
        console.log(account, password);
        const data = await execLogin(account, password);
        console.log(data)

        if (data.token) {
            setAuthStatus(1);
            setUserToken(data.token);
            sessionStorage.setItem("ERP_TOKEN", data.token);
        }

        if (data.userInfo) {

            sessionStorage.setItem("userInfo", JSON.stringify(data.userInfo));
            setUserInfo(data.userInfo);
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Typography component="h1" variant="h5">
                    登入
                </Typography>
                <Box component="form" onSubmit={login} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="account"
                        label="帳號"
                        name="account"
                        autoComplete="account"
                        autoFocus
                        value={account}
                        onChange={(e) => setAccount(e.target.value)}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="密碼"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    {error && (
                        <Typography color="error" variant="body2">
                            {error}
                        </Typography>
                    )}
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                        onClick={login}
                    >
                        登入
                    </Button>
                </Box>
            </Box>
        </Container>
    );
};




export default LoginPage;
